package com.capitole.productsapi.unit.infrastructure.persistance

import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.infrastructure.persistence.adapters.JpaProductRepositoryAdapter
import com.capitole.productsapi.infrastructure.persistence.entities.ProductEntity
import com.capitole.productsapi.infrastructure.persistence.repository.ProductJpaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaProductRepositoryAdapter::class)
class JpaProductRepositoryAdapterTest {

    @Autowired lateinit var adapter: ProductRepository
    @Autowired lateinit var jpaRepository: ProductJpaRepository
    @Autowired lateinit var entityManager: TestEntityManager

    @Test
    fun `findAll should return paged products`() {
        // Given
        val entity1 = ProductEntity("SKU1", BigDecimal("100"), "Product 1", "Electronics")
        val entity2 = ProductEntity("SKU2", BigDecimal("200"), "Product 2", "Home & Kitchen")
        entityManager.persist(entity1)
        entityManager.persist(entity2)
        entityManager.flush()

        // When
        val result = adapter.findAll(PageRequest.of(0, 10))

        // Then
        assertEquals(2, result.totalElements)
        assertEquals("SKU1", result.content[0].sku)
        assertEquals("Product 1", result.content[0].description)
    }

    @Test
    fun `findByCategory should filter products`() {
        // Given
        val electronics1 = ProductEntity("SKU1", BigDecimal("100"), "TV", "Electronics")
        val electronics2 = ProductEntity("SKU2", BigDecimal("200"), "Radio", "Electronics")
        val home = ProductEntity("SKU3", BigDecimal("50"), "Knife", "Home & Kitchen")
        listOf(electronics1, electronics2, home).forEach { entityManager.persist(it) }
        entityManager.flush()

        // When
        val result = adapter.findByCategory("Electronics", PageRequest.of(0, 10))

        // Then
        assertEquals(2, result.totalElements)
        assertTrue(result.content.all { it.category == "Electronics" })
    }

    @Test
    fun `findAll should return empty page when no products`() {
        // When
        val result = adapter.findAll(PageRequest.of(0, 10))

        // Then
        assertEquals(0, result.totalElements)
    }

    @Test
    fun `findByCategory should return empty page when no matches`() {
        // Given
        entityManager.persist(ProductEntity("SKU1", BigDecimal("100"), "TV", "Electronics"))
        entityManager.flush()

        // When
        val result = adapter.findByCategory("NonExisting", PageRequest.of(0, 10))

        // Then
        assertEquals(0, result.totalElements)
    }

    @Test
    fun `should map all product fields correctly`() {
        // Given
        val entity = ProductEntity(
            sku = "SKU_TEST",
            price = BigDecimal("99.99"),
            description = "Test Description",
            category = "Test Category"
        )
        entityManager.persist(entity)
        entityManager.flush()

        // When
        val result = adapter.findAll(PageRequest.of(0, 1)).content[0]

        // Then
        assertEquals(entity.sku, result.sku)
        assertEquals(entity.price, result.price)
        assertEquals(entity.description, result.description)
        assertEquals(entity.category, result.category)
    }
}