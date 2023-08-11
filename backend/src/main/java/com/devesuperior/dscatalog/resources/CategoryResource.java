package com.devesuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * End point, refrente a todas as categorias, ou seja, uma rota Vamos alteraar o
	 * tipo List para Page, a fim de paginar o retorno das listas ...
	 */
	@GetMapping
	// public ResponseEntity<List<CategoryDTO>> findAll() {
	public ResponseEntity<Page<CategoryDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction

	) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<CategoryDTO> list = service.findAllPaged(pageRequest);

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

	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
