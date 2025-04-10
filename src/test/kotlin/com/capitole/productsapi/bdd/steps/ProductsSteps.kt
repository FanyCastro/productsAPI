package com.capitole.productsapi.bdd.steps

import com.jayway.jsonpath.JsonPath
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsSteps : BaseStepDefinitions() {
    @Given("the product catalog contains products")
    fun `the product catalog contains products`() {
        // We keep the initial load - using data.sql that is loaded when server starts
        // We could load data manually before starting the test
    }

    @When("I request the product list")
    fun `request product list`() {
        executeGet("/api/v1/products")
    }

    @When("I search for products in category {string}")
    fun `I search for products in category`(category: String) {
        executeGet("/api/v1/products?category=${category}")
    }

    @When("I request the product list with {string} {string}")
    fun `I request the product list with`(param: String, value: String) {
        executeGet("/api/v1/products?${param}=${value}")
    }

    @Then("the response status should be {int}")
    fun `verify response status`(status: Int) {
        assertEquals(HttpStatus.valueOf(status), latestResponse.statusCode)
    }

    @Then("the response should contain {int} products")
    fun `verify product count`(count: Int) {
        assertEquals(HttpStatus.OK, latestResponse.statusCode)
        val json = latestResponse.body
        val productCount = JsonPath.read<Int>(json, "$.content.length()")
        assertTrue(productCount > 0, "La respuesta no contiene productos")
        assertTrue(productCount == count, "La respuesta no contiene el número de productos esperado")
    }

    @Then("all products should have category {string}")
    fun `all products should have category`(category: String) {
        assertEquals(HttpStatus.OK, latestResponse.statusCode)

        val json = latestResponse.body
        val productCount = JsonPath.read<Int>(json, "$.content.length()")
        assertTrue(productCount > 0, "La respuesta no contiene productos")

        val categories: List<String> = JsonPath.read(json, "$.content[*].category")
        assertFalse(categories.isEmpty(), "No se encontraron categorías en los productos")
        val wrongCategoryProducts = categories
            .mapIndexed { index, actual -> index to actual }
            .filter { (_, actual) -> actual != category }

        assertTrue(wrongCategoryProducts.isEmpty()) {
            """Se encontraron productos con categoría incorrecta en las posiciones: 
           ${wrongCategoryProducts.joinToString { "índice ${it.first} (${it.second})" }}"""
        }
    }
}

abstract class BaseStepDefinitions {
    @Autowired
    protected lateinit var restTemplate: TestRestTemplate
    protected lateinit var latestResponse: ResponseEntity<String>

    protected fun executeGet(url: String) {
        latestResponse = restTemplate.getForEntity(url, String::class.java)
    }

    protected fun executePost(url: String, body: Any) {
        // Implementación similar para POST
    }
}