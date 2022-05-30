package com.devesuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import com.devesuperior.dscatalog.entities.Category;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Annotation from rest
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	/*
	 * End point rest
	 */
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Eletronics"));
		return ResponseEntity.ok().body(list);

	}

}
