package com.capitole.productsapi.infrastructure.config

import com.capitole.productsapi.application.ProductServiceImpl
import com.capitole.productsapi.domain.port.`in`.ProductService
import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.domain.service.DiscountCalculator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun productService(
        productRepository: ProductRepository,
        discountCalculator: DiscountCalculator): ProductService {
        return ProductServiceImpl(
            repository = productRepository,
            discountCalculator = discountCalculator
        )
    }
}