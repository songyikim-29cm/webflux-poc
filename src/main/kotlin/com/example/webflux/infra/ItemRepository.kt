package com.example.webflux.infra

import com.example.webflux.domain.Item
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ItemRepository : CoroutineCrudRepository<Item, String> {
    fun findByName(name: String): Flow<Item>
}