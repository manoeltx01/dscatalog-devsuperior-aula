package com.devesuperior.dscatalog.dto;

import java.io.Serializable;

import com.devesuperior.dscatalog.entities.Category;

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	public CategoryDTO() {

	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/*
	 * Vai popular o DTO, a partir da category
	 */

	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
