package com.capitole.productsapi.infrastructure.web

import com.capitole.productsapi.infrastructure.dto.ProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController {
    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) category: String?,
        @RequestParam(defaultValue = "sku") sortBy: String,
        @RequestParam(defaultValue = "asc") sortDirection: String,
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(ProductResponse.empty())
    }
}