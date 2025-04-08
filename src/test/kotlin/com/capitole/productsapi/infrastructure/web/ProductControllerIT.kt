package com.capitole.productsapi.infrastructure.web

import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {
    @Autowired
    lateinit var mvc: MockMvc;

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

}