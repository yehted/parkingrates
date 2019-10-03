package com.example.rating.model

import java.time.OffsetDateTime

data class RateRequest(
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime
)