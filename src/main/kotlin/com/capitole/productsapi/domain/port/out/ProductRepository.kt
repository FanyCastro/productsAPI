package com.capitole.productsapi.domain.port.out

import com.capitole.productsapi.domain.model.ProductModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepository {
    fun findAll(pageable: Pageable): Page<ProductModel>
    fun findByCategory(category: String, pageable: Pageable): Page<ProductModel>
}