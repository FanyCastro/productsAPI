package com.capitole.productsapi.application

import com.capitole.productsapi.domain.model.Product
import com.capitole.productsapi.domain.port.`in`.ProductService
import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.infrastructure.web.dto.ProductDetails
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductServiceImpl (
    private val repository: ProductRepository,
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
        // TODO - apply logic to calculate discounts
       return ProductDetails(
            sku = product.sku,
            originalPrice = product.price,
            finalPrice = BigDecimal(23),
            discountPercent = BigDecimal(23.5),
            discountType = "discount type to be calculated",
            description = product.description,
            category = product.category,
       )
    }
}