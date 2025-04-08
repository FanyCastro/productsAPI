package com.capitole.productsapi.infrastructure.web

import com.capitole.productsapi.application.ProductServiceImpl
import com.capitole.productsapi.infrastructure.web.dto.ProductResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController (
    private val productService: ProductServiceImpl
) {
    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) category: String?,
        @RequestParam(defaultValue = "sku") sortBy: String,
        @RequestParam(defaultValue = "asc") sortDirection: String,
    ): ResponseEntity<ProductResponse> {
        val direction = Sort.Direction.fromString(sortDirection)
        val pageable = PageRequest.of(page, size, Sort.by(direction, sortBy))

        val products = productService.getProducts(category, pageable)
        return ResponseEntity.ok(ProductResponse.from(products))
    }
}