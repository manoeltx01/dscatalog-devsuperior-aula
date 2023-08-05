package com.devesuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devesuperior.dscatalog.dto.CategoryDTO;
import com.devesuperior.dscatalog.entities.Category;
import com.devesuperior.dscatalog.repositories.CategoryRepository;
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
	public List<CategoryDTO> findAll() {
		// Da erro tem q ser reescrita return repository.findAll();

		List<Category> list = repository.findAll();

		/*
		 * Usar funcao lambida, em vez da opcao abaixo Trabalha com lista depois stream
		 * e volta para lista de novo
		 */

		// List<CategoryDTO> listDto = list.stream().map( x -> new
		// CategoryDTO(x)).collect(Collectors.toList());

		/*
		 * Carregar Category Entity no DTO foi substituido pelo stream acima
		 */
		// List<CategoryDTO> listDto = new ArrayList<>();
		// for (Category cat : list) {
		// listDto.add(new CategoryDTO(cat));
		// }

		// return listDto;

		/*
		 * Usando o return direto
		 */

		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

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
}
