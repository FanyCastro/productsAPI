package com.capitole.productsapi.infrastructure.web.dto

import org.springframework.data.domain.Page

data class ProductResponse(
    val content: List<ProductDetails>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean
) {
    companion object {
        fun from(page: Page<ProductDetails>): ProductResponse {
            return ProductResponse(
                content = page.content,
                page = page.number,
                size = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                last = page.isLast
            )
        }
    }
}