package com.capitole.productsapi.unit.infrastructure.web

import com.capitole.productsapi.application.ProductServiceImpl
import com.capitole.productsapi.infrastructure.web.ProductController
import com.capitole.productsapi.infrastructure.web.dto.ProductDetails
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

@WebMvcTest(ProductController::class)
class ProductControllerTest {
    @Autowired private lateinit var mockMvc: MockMvc
    @MockitoBean
    private lateinit var productService: ProductServiceImpl

    @Test
    fun `should return products with default pagination`() {
        // Given
        val mockProducts = listOf(
            ProductDetails(
                sku = "SKU1",
                originalPrice = BigDecimal("100"),
                finalPrice = BigDecimal("85"),
                discountPercent = BigDecimal("15"),
                discountType = "Category Discount",
                description = "Product 1",
                category = "Electronics"
            )
        )
        val mockPage = PageImpl(mockProducts)
        whenever(productService.getProducts(isNull(), any())).thenReturn(mockPage)

        // When/Then
        mockMvc.perform(get("/api/v1/products")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].sku").value("SKU1"))
            .andExpect(jsonPath("$.content[0].finalPrice").value(85))
            .andExpect(jsonPath("$.content[0].discountPercent").value(15))

        verify(productService).getProducts(
            null,
            PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "sku")))
    }

    @Test
    fun `should filter by category when provided`() {
        // Given
        val mockPage = PageImpl(emptyList<ProductDetails>())
        whenever(productService.getProducts(eq("Electronics"), any())).thenReturn(mockPage)

        // When/Then
        mockMvc.perform(get("/api/v1/products")
            .param("category", "Electronics")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)

        verify(productService).getProducts(
            "Electronics",
            PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "sku")))
    }

    @Test
    fun `should use custom pagination parameters`() {
        // Given
        val mockPage = PageImpl(emptyList<ProductDetails>())
        whenever(productService.getProducts(isNull(), any())).thenReturn(mockPage)

        // When/Then
        mockMvc.perform(get("/api/v1/products")
            .param("sortBy", "price")
            .param("sortDirection", "desc")
            .param("page", "2")
            .param("size", "20")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)

        verify(productService).getProducts(
            null,
            PageRequest.of(2, 20, Sort.by(Sort.Direction.DESC, "price")))
    }

    @Test
    fun `should return 400 when invalid sortBy`() {
        mockMvc.perform(get("/api/v1/products")
            .param("sortBy", "invalid")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors[0]").value(containsString("SortBy must be one of: sku, price, description, category")))
    }

    @Test
    fun `should return internal error server when products throws an exception`() {
        // Given
        whenever(productService.getProducts(isNull(), any())).thenThrow(RuntimeException::class.java)

        // When/Then
        mockMvc.perform(get("/api/v1/products")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError)
            .andExpect(jsonPath("$.errors[0]").value(containsString("SERVER_ERROR")))

        verify(productService).getProducts(
            null,
            PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "sku")))
    }

    @Test
    fun `should return 400 when invalid sortDirection`() {
        mockMvc.perform(get("/api/v1/products")
            .param("sortDirection", "invalid")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors[0]").value(containsString("SortDirection must be 'asc' or 'desc'")))
    }

    @Test
    fun `should return 400 when page is negative`() {
        mockMvc.perform(get("/api/v1/products")
            .param("page", "-1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors[0]").value(containsString("Page must be 0 or greater")))
    }

    @Test
    fun `should return 400 when size is zero`() {
        mockMvc.perform(get("/api/v1/products")
            .param("size", "0")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors[0]").value(containsString("Size must be 1 or greater")))
    }

    @Test
    fun `should return 400 when size exceeds max`() {
        mockMvc.perform(get("/api/v1/products")
            .param("size", "101")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors[0]").value(containsString("Size cannot exceed 100")))
    }

    @Test
    fun `should return 400 when category exceeds max length`() {
        val longCategory = "a".repeat(51)
        mockMvc.perform(get("/api/v1/products")
            .param("category", longCategory)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors[0]").value(containsString("Category cannot exceed 50 characters")))
    }

    @Test
    fun `should return validation errors for multiple invalid parameters`() {
        val longCategory = "a".repeat(51)

        mockMvc.perform(get("/api/v1/products")
            .param("sortBy", "invalidField")
            .param("sortDirection", "invalidDirection")
            .param("page", "-5")
            .param("size", "0")
            .param("category", longCategory)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors").isArray)
            .andExpect(jsonPath("$.errors.length()").value(5))
            .andExpect(jsonPath("$.message").value("Validation failed"))
    }

}