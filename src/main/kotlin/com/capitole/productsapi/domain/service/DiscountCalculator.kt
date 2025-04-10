package com.capitole.productsapi.domain.service

import com.capitole.productsapi.domain.model.DiscountResultModel
import com.capitole.productsapi.domain.model.ProductModel
import java.math.BigDecimal

class DiscountCalculator(
    private val strategies: List<DiscountStrategy>
) {

    fun calculateBestDiscount(productModel: ProductModel): DiscountResultModel {
        return strategies
            .map { strategy ->
                DiscountResultModel(
                    discount = strategy.calculateDiscount(productModel),
                    type = strategy.getDiscountType()
                )
            }
            .filter { it.discount > BigDecimal.ZERO }
            .maxByOrNull { it.discount }
            ?: DiscountResultModel(BigDecimal.ZERO, null)  // default, no discounts apply
    }
}
