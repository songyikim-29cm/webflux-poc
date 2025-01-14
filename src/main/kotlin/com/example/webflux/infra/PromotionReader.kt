package com.example.webflux.infra

import com.example.webflux.domain.Item
import com.example.webflux.infra.PromotionHttpClient.CouponDto
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class PromotionReader(
    private val promotionHttpClient: PromotionHttpClient,
) {

    private val log = LoggerFactory.getLogger(PromotionReader::class.java)

    @CircuitBreaker(name = "coupon-list", fallbackMethod = "fallback")
    fun getCouponList(): Flux<CouponDto> {
        return mono {
            promotionHttpClient.coupons()
        }.flatMapMany { response ->
            Flux.fromIterable(response.data.resultList)
        }
    }

    fun fallback(throwable: Throwable): Flux<Item> {
        log.error("circuitbreaker open!")
        return Flux.empty()
    }
}

