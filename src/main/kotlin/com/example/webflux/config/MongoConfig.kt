package com.example.webflux.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

@Configuration
class MongoConfig {

    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        val converters = mutableListOf<Converter<*, *>>()
        converters.add(ZonedDateTimeWriteConverter())
        converters.add(ZonedDateTimeReadConverter())
        return MongoCustomConversions(converters)
    }
}

class ZonedDateTimeReadConverter : Converter<Date, ZonedDateTime> {
    override fun convert(date: Date): ZonedDateTime {
        return date.toInstant().atZone(ZoneOffset.UTC)
    }
}

class ZonedDateTimeWriteConverter : Converter<ZonedDateTime, Date> {
    override fun convert(source: ZonedDateTime): Date {
        return Date.from(source.toInstant())
    }
}
