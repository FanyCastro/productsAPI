package com.capitole.productsapi.infrastructure.web.dto

import java.math.BigDecimal

data class ProductDetails(
    val sku: String,
    val originalPrice: BigDecimal,
    val finalPrice: BigDecimal,
    val discountPercent: BigDecimal,
    val discountType: String,
    val description: String,
    val category: String,
) {}
