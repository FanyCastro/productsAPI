package com.capitole.productsapi.domain.service

import com.capitole.productsapi.domain.model.Product
import com.capitole.productsapi.domain.service.DiscountStrategy
import java.math.BigDecimal

class DiscountCalculator(
    private val strategies: List<DiscountStrategy>
) {
    fun calculateBestDiscount(product: Product): Pair<BigDecimal, String?> {
        return strategies
            .map { strategy ->
                strategy.calculateDiscount(product) to strategy.getDiscountType()
            }
            .filter { (discount, _) -> discount > BigDecimal.ZERO }
            .maxByOrNull { (discount, _) -> discount }
            ?: (BigDecimal.ZERO to null)  // default, no discounts apply
    }
}