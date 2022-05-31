package com.devesuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devesuperior.dscatalog.entities.Category;
import com.devesuperior.dscatalog.services.CategoryService;

/*
 * Annotation from rest
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	/*
	 * End point rest e usar a camade de serviços para acessar o banco de dados
	 */
	
	// Inserir uma injeção de dependência
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = service.findAll();
		
		// List<Category> list = new ArrayList<>();
		/*
		 * Informações mocadas, ou seja, nao vem do banco de dados
		 */
		// list.add(new Category(1L, "Books"));
		// list.add(new Category(2L, "Eletronics"));
		
		 return ResponseEntity.ok().body(list);
		

	}

}
