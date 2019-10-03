package com.example.rating.model

import java.time.OffsetDateTime
import java.time.ZonedDateTime

data class RateRequest(
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime
)