package com.devesuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devesuperior.dscatalog.dto.CategoryDTO;
import com.devesuperior.dscatalog.entities.Category;
import com.devesuperior.dscatalog.repositories.CategoryRepository;
import com.devesuperior.dscatalog.services.exceptions.DatabaseException;
import com.devesuperior.dscatalog.services.exceptions.ResourceEntityNotFoundException;

/*
 * Esta anotação vai registrar a classe, abaixo, como um componente que vai participar 
 * da injeçao de dependencia automatizada pelo Spring.
 * Trata-se de INJEÇÃO de DEPENDENCIA AUTOMATIZADA (Todos os frameworks possuem esta
 * facilidade.
 */
@Service
public class CategoryService {

	/*
	 * Vai ter uma dependencia
	 */

	@Autowired
	private CategoryRepository repository;

	/*
	 * Garantir a integridade da transacao JPA, ou seja, com o banco de dados Ou faz
	 * tudo ou nao Faz nada ... passar o parametro, readOnly=true para evitar lock
	 * no banco
	 */
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));

	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceEntityNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		// o metodo, getOne, guarda provisoriamente o ID no objeto, evitando de ir ao
		// banco e
		// só vai ocorrer quando for para fazer o update

		try {

			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			// Agora, vamos salva la no banco de dados
			entity = repository.save(entity);
			return new CategoryDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceEntityNotFoundException("Id not found " + id);
		}
	}

	/*
	 * Sem @TRANSACTIONAL, pois preciso capturar algo no banco e para tal, elimino
	 * esta anotacao
	 */
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceEntityNotFoundException("Id no found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}

		;
	}
}
