package com.example.webflux.infra

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import java.time.LocalDate

interface PromotionHttpClient {

    @GetExchange("/internal/v4/coupons/sales-campaign")
    suspend fun coupons(
        @RequestParam(required = false) couponIdList: List<Long>? = null,
        @RequestParam(required = false) couponName: String? = null,
        @RequestParam(required = false) couponAdminName: String? = null,
        @RequestParam(required = false) couponIssueStartAt: LocalDate? = null,
        @RequestParam(required = false) couponIssueEndAt: LocalDate? = null,
        @RequestParam(required = false) createdDateStartAt: LocalDate? = null,
        @RequestParam(required = false) createdDateEndAt: LocalDate? = null,
        @RequestParam(required = false) page: Int = 1,
        @RequestParam(required = false) size: Int = 50,
    ): PromotionResponse<CouponListDto>

    data class PromotionResponse<T>(
        val result: String,
        val data: T,
        val message: String?,
        val errorCode: String?,
    )

    data class CouponListDto(
        val resultList: List<CouponDto>,
    )

    data class CouponDto(
        val couponId: Long,
        val couponName: String,
    )
}