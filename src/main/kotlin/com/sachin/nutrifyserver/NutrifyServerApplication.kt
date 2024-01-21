package com.sachin.nutrifyserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NutrifyServerApplication

fun main(args: Array<String>) {
	runApplication<NutrifyServerApplication>(*args)
}
