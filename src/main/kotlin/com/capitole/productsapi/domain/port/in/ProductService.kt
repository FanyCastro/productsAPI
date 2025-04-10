package com.capitole.productsapi.domain.port.`in`

import com.capitole.productsapi.infrastructure.web.dto.ProductDetails
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

typealias ProductService = (category: String?, pageable: Pageable) -> Page<ProductDetails>