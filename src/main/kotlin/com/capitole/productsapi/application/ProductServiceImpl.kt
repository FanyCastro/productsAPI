package com.capitole.productsapi.application

import com.capitole.productsapi.domain.model.Product
import com.capitole.productsapi.domain.port.`in`.ProductService
import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.domain.service.DiscountCalculator
import com.capitole.productsapi.infrastructure.web.dto.ProductDetails
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductServiceImpl (
    private val repository: ProductRepository,
    private val discountCalculator: DiscountCalculator
): ProductService {
    override fun getProducts(category: String?, pageable: Pageable): Page<ProductDetails> {
        val products = if (category != null) {
            repository.findByCategory(category, pageable)
        } else {
            repository.findAll(pageable)
        }
       return products.map { toProductDetails(it) };
    }

    private fun toProductDetails(product: Product): ProductDetails {
        val (discount, discountType) = discountCalculator.calculateBestDiscount(product)
       return ProductDetails(
            sku = product.sku,
            originalPrice = product.price,
            finalPrice = product.price - (product.price * discount),
            discountPercent = discount * BigDecimal(100),
            discountType = discountType,
            description = product.description,
            category = product.category,
       )
    }
}