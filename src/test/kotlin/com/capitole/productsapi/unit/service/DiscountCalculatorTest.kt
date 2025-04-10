package com.capitole.productsapi.unit.service

import com.capitole.productsapi.domain.model.Product
import com.capitole.productsapi.domain.service.CategoryDiscountStrategy
import com.capitole.productsapi.domain.service.DiscountCalculator
import com.capitole.productsapi.domain.service.SpecialSkuDiscountStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DiscountCalculatorTest {
    private val strategies = listOf(
        SpecialSkuDiscountStrategy(),
        CategoryDiscountStrategy()
    )
    private val calculator = DiscountCalculator(strategies)

    @Test
    fun `should apply special SKU discount first`() {
        val product = Product("SKU0005", BigDecimal("100"), "Test", "Electronics")
        val (discount, type) = calculator.calculateBestDiscount(product)

        assertEquals(BigDecimal("0.30"), discount)
        assertEquals("Special SKU Discount", type)
    }

    @Test
    fun `should apply category discount when no special SKU`() {
        val product = Product("SKU0001", BigDecimal("100"), "Test", "Home & Kitchen")
        val (discount, type) = calculator.calculateBestDiscount(product)

        assertEquals(BigDecimal("0.25"), discount)
        assertEquals("Category Discount", type)
    }

    @Test
    fun `should return zero when no discounts apply`() {
        val product = Product("SKU0004", BigDecimal("100"), "Test", "Clothing")
        val (discount, type) = calculator.calculateBestDiscount(product)

        assertEquals(BigDecimal.ZERO, discount)
        assertNull(type)
    }
}