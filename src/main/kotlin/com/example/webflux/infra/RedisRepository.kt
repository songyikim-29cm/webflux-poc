package com.example.webflux.infra

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RedisRepository(
    private val reactiveRedisTemplate: ReactiveStringRedisTemplate,
) {

    fun save(key: String, value: String): Mono<Boolean> {
        return reactiveRedisTemplate.opsForValue().set(key, value)
    }

    suspend fun get(key: String): String {
        return reactiveRedisTemplate.opsForValue().get(key).awaitSingle()
    }

    fun delete(key: String): Mono<Long> {
        return reactiveRedisTemplate.delete(key)
    }
}
