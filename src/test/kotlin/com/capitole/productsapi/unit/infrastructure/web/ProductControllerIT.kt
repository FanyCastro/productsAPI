package com.capitole.productsapi.unit.infrastructure.web

import com.capitole.productsapi.application.ProductServiceImpl
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {
    @Autowired lateinit var mvc: MockMvc
    @Autowired lateinit var productService: ProductServiceImpl

    @Test
    fun `GET products - should return all products list with discounts`() {
        mvc.get("/api/v1/products") {
        }.andExpect { status { isOk() }
            jsonPath("$.content", hasSize<Int>(10))}
    }

    @Test
    fun `GET products - should return products by category list with discounts`() {
        mvc.get("/api/v1/products?category=Electronics") {
        }.andExpect { status { isOk() }
            jsonPath("$.content", hasSize<Int>(5))}
    }

    @Test
    fun `GET products - should return all products by category list with discounts using pages`() {
        mvc.get("/api/v1/products?page=1&size=2") {
        }.andExpect { status { isOk() }
            jsonPath("$.content", hasSize<Int>(2))
            jsonPath("$.page", equalTo(1))
            jsonPath("$.size", equalTo(2))
            jsonPath("$.totalElements", equalTo(10))
            jsonPath("$.totalPages", equalTo(5))
        }
    }

    @Test
    fun `GET products - should return all products by category list with discounts sorted by description`() {
        mvc.get("/api/v1/products?sortBy=description&sortDirection=asc") {
        }.andExpect { status { isOk() }
            jsonPath("$.content", hasSize<Int>(10))
            jsonPath("$.content[0].description", equalTo("4K Ultra HD Smart TV, 55 inches"))
            jsonPath("$.content[9].description", equalTo("Yoga Mat with Non-Slip Surface"))
        }
    }

    @Test
    fun `GET products - should throw an exception when description is not valid`() {
        mvc.get("/api/v1/products?sortBy=field&sortDirection=asc") {
        }.andExpect { status { is4xxClientError() }
            jsonPath("$.status", equalTo(400))
            jsonPath("$.message", equalTo("Validation failed"))
        }
    }

    @Test
    fun `GET products - should return 400 when page is negative`() {
        mvc.get("/api/v1/products?sortBy=description&sortDirection=asc&page=-1") {
        }.andExpect { status { isBadRequest() }
            jsonPath("$.message", equalTo("Validation failed"))
            jsonPath("$.errors[0]", containsString("Page must be 0 or greater"))
        }
    }

    @Test
    fun `GET products - should return 400 when category exceeds max length`() {
        val longCategory = "a".repeat(51)
        mvc.get("/api/v1/products?category=$longCategory") {
        }.andExpect { status { isBadRequest() }
            jsonPath("$.message", equalTo("Validation failed"))
            jsonPath("$.errors[0]", containsString("Category cannot exceed 50 characters"))
        }
    }

    @Test
    fun `GET products - should throw an exception when method is not allowed`() {
        mvc.put("/api/v1/products") {
        }.andExpect { status { is4xxClientError() } }
    }
}