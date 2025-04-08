package com.capitole.productsapi.infrastructure.web

import org.hamcrest.Matchers.hasSize
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
    fun `GET products - should return products list with discounts`() {
        mvc.get("/api/v1/products") {
        }.andExpect { status { isOk() }
            jsonPath("$.content", hasSize<Int>(0))}
    }
}