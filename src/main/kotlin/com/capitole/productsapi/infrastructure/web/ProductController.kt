package com.capitole.productsapi.infrastructure.web

import com.capitole.productsapi.application.ProductServiceImpl
import com.capitole.productsapi.infrastructure.web.dto.ProductResponse
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
@Validated
class ProductController (
    private val productService: ProductServiceImpl
) {
    @GetMapping
    fun getProducts(
        @RequestParam(required = false)
        @Size(max = 50, message = "Category cannot exceed 50 characters")
        category: String?,

        @RequestParam(defaultValue = "sku")
        @Pattern(regexp = "sku|price|description|category",
            message = "SortBy must be one of: sku, price, description, category")
        sortBy: String,

        @RequestParam(defaultValue = "asc")
        @Pattern(regexp = "asc|desc",
            message = "SortDirection must be 'asc' or 'desc'")
        sortDirection: String,

        @RequestParam(defaultValue = "0")
        @Min(value = 0, message = "Page must be 0 or greater")
        page: Int,

        @RequestParam(defaultValue = "10")
        @Min(value = 1, message = "Size must be 1 or greater")
        @Max(value = 100, message = "Size cannot exceed 100")
        size: Int

    ): ResponseEntity<ProductResponse> {
        val direction = Sort.Direction.fromString(sortDirection)
        val pageable = PageRequest.of(page, size, Sort.by(direction, sortBy))

        val products = productService.getProducts(category, pageable)
        return ResponseEntity.ok(ProductResponse.from(products))
    }
}