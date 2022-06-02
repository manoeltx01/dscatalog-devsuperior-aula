package com.devesuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devesuperior.dscatalog.entities.Category;
import com.devesuperior.dscatalog.repositories.CategoryRepository;

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
	 * Garantir a integridade da transacao JPA, ou seja, com o banco de dados
	 * Ou faz tudo ou nao Faz nada ...
	 * passar o parametro, readOnly=true para evitar lock no banco
	 */
	@Transactional(readOnly = true)
	public List<Category> findAll() {
		return repository.findAll();

	}

}
