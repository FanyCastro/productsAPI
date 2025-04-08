package com.capitole.productsapi.infrastructure.persistence.adapters

import com.capitole.productsapi.domain.model.Product
import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.infrastructure.persistence.repository.ProductJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class JpaProductRepositoryAdapter(
    private val jpaRepository: ProductJpaRepository
) : ProductRepository {

    override fun findAll(pageable: Pageable): Page<Product> {
        return jpaRepository.findAll(pageable).map { it.toDomain() }
    }

    override fun findByCategory(category: String, pageable: Pageable): Page<Product> {
        return jpaRepository.findByCategory(category, pageable).map { it.toDomain() }
    }
}