package com.devesuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devesuperior.dscatalog.entities.Category;

/*
 * Camada de acesso a dados do back end
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
