package com.capitole.productsapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductsapiApplication

fun main(args: Array<String>) {
	runApplication<ProductsapiApplication>(*args)
}
