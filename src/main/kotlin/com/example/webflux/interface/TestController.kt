package com.example.webflux.`interface`

import com.example.webflux.application.TestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lifecycle")
class TestController(
    private val testService: TestService,
) {

    @GetMapping("/health")
    suspend fun health(): String {
        return testService.test()
    }
}