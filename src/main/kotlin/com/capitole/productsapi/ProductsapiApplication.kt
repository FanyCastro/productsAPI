package com.capitole.productsapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main application class for the Products API.
 * This application provides a REST API for managing products and their discounts.
 *
 * @author Capitole
 */
@SpringBootApplication
class ProductsapiApplication


fun main(args: Array<String>) {
    runApplication<ProductsapiApplication>(*args)
}
