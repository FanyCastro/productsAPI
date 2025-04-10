package com.capitole.productsapi.application

import com.capitole.productsapi.domain.model.ProductModel
import com.capitole.productsapi.domain.port.`in`.ProductService
import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.domain.service.DiscountCalculator
import com.capitole.productsapi.infrastructure.web.dto.ProductDetailsDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

class ProductServiceImpl(
    private val repository: ProductRepository,
    private val discountCalculator: DiscountCalculator
) : ProductService {
    override operator fun invoke(category: String?, pageable: Pageable): Page<ProductDetailsDto> {
        val products = if (category != null) {
            repository.findByCategory(category, pageable)
        } else {
            repository.findAll(pageable)
        }
        return products.map { toProductDetails(it) }
    }

    private fun toProductDetails(productModel: ProductModel): ProductDetailsDto {
        val (discount, discountType) = discountCalculator.calculateBestDiscount(productModel)
        return ProductDetailsDto(
            sku = productModel.sku,
            originalPrice = productModel.price,
            finalPrice = productModel.price - (productModel.price * discount),
            discountPercent = discount * BigDecimal(100),
            discountType = discountType,
            description = productModel.description,
            category = productModel.category,
        )
    }
}