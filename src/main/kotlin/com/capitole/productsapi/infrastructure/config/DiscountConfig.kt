package com.capitole.productsapi.infrastructure.config

import com.capitole.productsapi.domain.service.DiscountCalculator
import com.capitole.productsapi.domain.service.CategoryDiscountStrategy
import com.capitole.productsapi.domain.service.DiscountStrategy
import com.capitole.productsapi.domain.service.SpecialSkuDiscountStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiscountConfig {
    @Bean
    fun discountStrategies() = listOf(
            SpecialSkuDiscountStrategy(),
            CategoryDiscountStrategy()
    )

    @Bean
    fun discountCalculator(strategies: List<DiscountStrategy>) =
            DiscountCalculator(strategies)
}