package com.capitole.productsapi.infrastructure.persistence.repository

import com.capitole.productsapi.infrastructure.persistence.entities.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductEntity, String> {
    fun findByCategory(category: String, pageable: Pageable): Page<ProductEntity>
}