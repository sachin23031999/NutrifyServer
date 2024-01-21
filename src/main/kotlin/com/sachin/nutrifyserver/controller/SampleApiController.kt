package com.sachin.nutrifyserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/sample")
class SampleApiController {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "Hello from sample app"
    }
}