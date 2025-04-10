package com.capitole.productsapi.domain.model

import java.math.BigDecimal

data class DiscountResultModel (
    val discount: BigDecimal,
    val type: String?
)