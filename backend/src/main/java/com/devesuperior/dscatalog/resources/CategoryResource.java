package com.devesuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devesuperior.dscatalog.dto.CategoryDTO;
import com.devesuperior.dscatalog.services.CategoryService;


/*
 * Annotation from rest
 */
@RestController
/*
 * End point, rota basica
 */
@RequestMapping(value = "/categories")
public class CategoryResource {

	/*
	 * End point rest e usar a camade de serviços para acessar o banco de dados
	 */

	// Inserir uma injeção de dependência

	@Autowired
	private CategoryService service;

	/*
	 * End point, refrente a todas as categorias, ou seja, uma rota
	 */
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> list = service.findAll();

		// List<Category> list = new ArrayList<>();
		/*
		 * Informações mocadas, ou seja, nao vem do banco de dados
		 */
		// list.add(new Category(1L, "Books"));
		// list.add(new Category(2L, "Eletronics"));

		return ResponseEntity.ok().body(list);

	}

	/*
	 * End point, referente a um categoria em específico, ou seja, outra rota
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}
}
