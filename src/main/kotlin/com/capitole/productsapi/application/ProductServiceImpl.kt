package com.capitole.productsapi.application

import com.capitole.productsapi.domain.model.ProductModel
import com.capitole.productsapi.domain.port.`in`.ProductService
import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.domain.service.DiscountCalculator
import com.capitole.productsapi.infrastructure.web.dto.ProductDetailsDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class ProductServiceImpl(
    private val repository: ProductRepository,
    private val discountCalculator: DiscountCalculator
) : ProductService {

    override operator fun invoke(category: String?, pageable: Pageable): Page<ProductDetailsDto> =
        repository.findProductsByCategory(category, pageable)
            .map { it.toProductDetails() }

    private fun ProductModel.toProductDetails(): ProductDetailsDto {
        val (discount, discountType) = discountCalculator.calculateBestDiscount(this)
        val finalPrice = calculateFinalPrice(price, discount)
        
        return ProductDetailsDto(
            sku = sku,
            originalPrice = price.stripTrailingZeros(),
            finalPrice = finalPrice.stripTrailingZeros(),
            discountPercent = discount.multiply(BigDecimal(100)).stripTrailingZeros(),
            discountType = discountType,
            description = description,
            category = category
        )
    }

    private fun calculateFinalPrice(originalPrice: BigDecimal, discount: BigDecimal): BigDecimal =
        originalPrice.multiply(BigDecimal.ONE.subtract(discount))
            .setScale(2, RoundingMode.HALF_UP)
}


private fun ProductRepository.findProductsByCategory(category: String?, pageable: Pageable): Page<ProductModel> =
    when (category) {
        null -> findAll(pageable)
        else -> findByCategory(category, pageable)
}