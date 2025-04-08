package com.capitole.productsapi.infrastructure.persistence.entities

import com.capitole.productsapi.domain.model.Product
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    val sku: String,

    @Column(nullable = false)
    val price: BigDecimal,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val category: String
) {
    fun toDomain() = Product(
        sku = sku,
        price = price,
        description = description,
        category = category
    )
}

