package com.capitole.productsapi.infrastructure.dto

data class ProductResponse(
    val content: List<ProductDetails>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean
) {
    companion object {
        fun empty() = ProductResponse(emptyList(), 0, 0,0L, 0, true)
    }
}