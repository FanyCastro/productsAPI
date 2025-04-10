package com.capitole.productsapi.domain.service

import com.capitole.productsapi.domain.model.ProductModel
import java.math.BigDecimal

class DiscountCalculator(
    private val strategies: List<DiscountStrategy>
) {
    fun calculateBestDiscount(productModel: ProductModel): Pair<BigDecimal, String?> {
        return strategies
            .map { strategy ->
                strategy.calculateDiscount(productModel) to strategy.getDiscountType()
            }
            .filter { (discount, _) -> discount > BigDecimal.ZERO }
            .maxByOrNull { (discount, _) -> discount }
            ?: (BigDecimal.ZERO to null)  // default, no discounts apply
    }
}