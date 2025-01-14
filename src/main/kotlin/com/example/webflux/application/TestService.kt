package com.example.webflux.application

import com.example.webflux.infra.ItemRepository
import com.example.webflux.infra.PromotionReader
import com.example.webflux.infra.RedisRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.asFlux
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TestService(
    private val itemRepository: ItemRepository,
    private val promotionReader: PromotionReader,
    private val redisRepository: RedisRepository,
) {

    suspend fun test(): String {
        // web client
        val result = promotionReader.getCouponList().asFlow().toList()
        log.info("promotion http client 호출 성공 " + result)

        // mongodb
        log.info("mongodb 호출 성공 " + itemRepository.findByName("Sample Item").asFlux().asFlow().toList())

        // redis
        redisRepository.save("key", "value").awaitFirst()
        log.info("redis 호출 성공 " + redisRepository.get("key"))

        return "success"
    }

    companion object {
        private val log = LoggerFactory.getLogger(TestService::class.java)
    }
}