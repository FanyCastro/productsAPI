package com.capitole.productsapi.unit.application

import com.capitole.productsapi.application.ProductServiceImpl
import com.capitole.productsapi.domain.model.DiscountResultModel
import com.capitole.productsapi.domain.model.ProductModel
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

class ProductModelServiceImplTest {
    private val mockRepository = mockk<ProductRepository>()
    private val mockCalculator = mockk<DiscountCalculator>()

    private val service = ProductServiceImpl(mockRepository, mockCalculator)

    @Test
    fun `getProducts should apply discounts to all products`() {
        // Given
        val productModel1 = ProductModel("SKU1", BigDecimal("100"), "Test1", "Electronics")
        val productModel2 = ProductModel("SKU2", BigDecimal("50"), "Test2", "Clothing")
        val page = PageImpl(listOf(productModel1, productModel2))

        every { mockRepository.findAll(any<Pageable>()) } returns page
        every { mockCalculator.calculateBestDiscount(productModel1) } returns
                DiscountResultModel(BigDecimal("0.15"), "Category Discount")
        every { mockCalculator.calculateBestDiscount(productModel2) } returns
                DiscountResultModel(BigDecimal.ZERO, null)

        // When
        val result = service.invoke(null, Pageable.unpaged())

        // Then
        assertEquals(2, result.content.size)

        // Verify discounts applied
        assertEquals(BigDecimal(15), result.content[0].discountPercent)
        assertEquals(BigDecimal.ZERO, result.content[1].discountPercent)

        // Verify repository called
        verify { mockRepository.findAll(any<Pageable>()) }
    }

    @Test
    fun `getProducts should filter by category when provided`() {
        // Given
        val productModel = ProductModel("SKU1", BigDecimal("100"), "Test", "Electronics")
        val page = PageImpl(listOf(productModel))

        every { mockRepository.findByCategory("Electronics", any()) } returns page
        every { mockCalculator.calculateBestDiscount(productModel) } returns
                DiscountResultModel(BigDecimal("0.15"), null)

        // When
        val result = service.invoke("Electronics", Pageable.unpaged())

        // Then
        assertEquals(1, result.content.size)
        verify { mockRepository.findByCategory("Electronics", any()) }
        verify(exactly = 0) { mockRepository.findAll(any()) }
    }
}