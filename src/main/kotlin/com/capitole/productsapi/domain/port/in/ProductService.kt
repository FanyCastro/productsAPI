package com.capitole.productsapi.domain.port.`in`

import com.capitole.productsapi.infrastructure.web.dto.ProductDetails
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductService {
    fun getProducts(category: String?, pageable: Pageable): Page<ProductDetails>
}