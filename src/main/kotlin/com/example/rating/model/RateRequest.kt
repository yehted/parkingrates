package com.example.rating.model

import java.time.ZonedDateTime

data class RateRequest(
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime
)