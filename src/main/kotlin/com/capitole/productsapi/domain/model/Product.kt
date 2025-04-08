package com.capitole.productsapi.domain.model

import java.math.BigDecimal

data class Product(
    val sku: String,
    val price: BigDecimal,
    val description: String,
    val category: String
) {
    fun hasSpecialDiscount() = sku.endsWith("5")
}
