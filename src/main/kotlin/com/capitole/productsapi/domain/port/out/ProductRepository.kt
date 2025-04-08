package com.capitole.productsapi.domain.port.out

import com.capitole.productsapi.domain.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepository {
    fun findAll(pageable: Pageable): Page<Product>
    fun findByCategory(category: String, pageable: Pageable): Page<Product>
}