package com.capitole.productsapi.domain.service

import com.capitole.productsapi.domain.model.Product
import java.math.BigDecimal

interface DiscountStrategy {
    fun calculateDiscount(product: Product): BigDecimal
    fun getDiscountType(): String?
}

class SpecialSkuDiscountStrategy : DiscountStrategy {
    override fun calculateDiscount(product: Product): BigDecimal {
        return if (product.hasSpecialDiscount()) BigDecimal("0.30")
        else BigDecimal.ZERO
    }

    override fun getDiscountType() = "Special SKU Discount"
}

class CategoryDiscountStrategy : DiscountStrategy {
    private val categoryDiscounts = mapOf(
        "Electronics" to BigDecimal("0.15"),
        "Home & Kitchen" to BigDecimal("0.25")
    )

    override fun calculateDiscount(product: Product): BigDecimal {
        return categoryDiscounts[product.category] ?: BigDecimal.ZERO
    }

    override fun getDiscountType() = "Category Discount"
}
