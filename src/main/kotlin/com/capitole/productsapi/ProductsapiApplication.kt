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

/**
 * Main entry point of the application.
 * Starts the Spring Boot application with the provided arguments.
 *
 * @param args Command line arguments
 */
fun main(args: Array<String>) = runApplication<ProductsapiApplication>(*args)
