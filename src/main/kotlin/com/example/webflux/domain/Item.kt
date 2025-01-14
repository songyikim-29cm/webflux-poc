package com.example.webflux.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document(collection = "items")
data class Item(
    @Id
    val id: String?,
    val name: String,
    val category: String,
    val price: Int,
    val createdAt: ZonedDateTime,
)