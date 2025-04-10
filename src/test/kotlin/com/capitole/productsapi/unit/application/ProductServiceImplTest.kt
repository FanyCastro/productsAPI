package com.capitole.productsapi.unit.application

import com.capitole.productsapi.application.ProductServiceImpl
import com.capitole.productsapi.domain.model.Product
import com.capitole.productsapi.domain.port.out.ProductRepository
import com.capitole.productsapi.domain.service.DiscountCalculator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

class ProductServiceImplTest {
    private val mockRepository = mockk<ProductRepository>()
    private val mockCalculator = mockk<DiscountCalculator>()

    private val service = ProductServiceImpl(mockRepository, mockCalculator)

    @Test
    fun `getProducts should apply discounts to all products`() {
        // Given
        val product1 = Product("SKU1", BigDecimal("100"), "Test1", "Electronics")
        val product2 = Product("SKU2", BigDecimal("50"), "Test2", "Clothing")
        val page = PageImpl(listOf(product1, product2))

        every { mockRepository.findAll(any<Pageable>()) } returns page
        every { mockCalculator.calculateBestDiscount(product1) } returns
                Pair(BigDecimal("0.15"), "Category Discount")
        every { mockCalculator.calculateBestDiscount(product2) } returns
                Pair(BigDecimal.ZERO, null)

        // When
        val result = service.getProducts(null, Pageable.unpaged())

        // Then
        assertEquals(2, result.content.size)

        // Verify discounts applied
        assertEquals(BigDecimal("15.00"), result.content[0].discountPercent)
        assertEquals(BigDecimal.ZERO, result.content[1].discountPercent)

        // Verify repository called
        verify { mockRepository.findAll(any<Pageable>()) }
    }

    @Test
    fun `getProducts should filter by category when provided`() {
        // Given
        val product = Product("SKU1", BigDecimal("100"), "Test", "Electronics")
        val page = PageImpl(listOf(product))

        every { mockRepository.findByCategory("Electronics", any()) } returns page
        every { mockCalculator.calculateBestDiscount(product) } returns
                Pair(BigDecimal("0.15"), null)

        // When
        val result = service.getProducts("Electronics", Pageable.unpaged())

        // Then
        assertEquals(1, result.content.size)
        verify { mockRepository.findByCategory("Electronics", any()) }
        verify(exactly = 0) { mockRepository.findAll(any()) }
    }
}