package com.capitole.productsapi.domain.service

import com.capitole.productsapi.domain.model.ProductModel
import java.math.BigDecimal

interface DiscountStrategy {
    fun calculateDiscount(productModel: ProductModel): BigDecimal
    fun getDiscountType(): String?
}

class SpecialSkuDiscountStrategy : DiscountStrategy {
    override fun calculateDiscount(productModel: ProductModel): BigDecimal {
        return if (productModel.hasSpecialDiscount()) BigDecimal("0.30")
        else BigDecimal.ZERO
    }

    override fun getDiscountType() = "Special SKU Discount"
}

class CategoryDiscountStrategy : DiscountStrategy {
    private val categoryDiscounts = mapOf(
        "Electronics" to BigDecimal("0.15"),
        "Home & Kitchen" to BigDecimal("0.25")
    )

    override fun calculateDiscount(productModel: ProductModel): BigDecimal {
        return categoryDiscounts[productModel.category] ?: BigDecimal.ZERO
    }

    override fun getDiscountType() = "Category Discount"
}
