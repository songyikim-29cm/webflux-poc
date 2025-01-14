package com.example.webflux.infra

import io.netty.channel.ChannelOption
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.netty.http.client.HttpClient
import reactor.util.retry.Retry
import java.time.Duration


@Configuration
class PromotionHttpClientConfig(
    @Value("\${client.promotion-api.base-url}") private val baseUrl: String,
    @Value("\${client.promotion-api.connect-timeout-mills:1000}") private val connectTimeoutMills: Int,
    @Value("\${client.promotion-api.read-timeout-mills:500}") private val readTimeoutMills: Int,
) {

    @Bean
    fun promotionWebClient(): WebClient = WebClient
        .builder()
        .baseUrl(baseUrl)
        .clientConnector(
            ReactorClientHttpConnector(
                HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMills)
                    .responseTimeout(Duration.ofMillis(readTimeoutMills.toLong()))
            )
        )
        .filter { request, next ->
            next.exchange(request)
                .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(1))) // 1회 재시도, 1초 간격
        }
        .build()

    @Bean
    fun promotionClient(promotionWebClient: WebClient): PromotionHttpClient = HttpServiceProxyFactory
        .builder()
        .exchangeAdapter(WebClientAdapter.create(promotionWebClient))
        .build()
        .createClient(PromotionHttpClient::class.java)
}