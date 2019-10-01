package com.example.rating.model

import java.time.LocalTime
import java.time.ZoneId
import javax.persistence.*

@Entity
data class ParkingRate(
    @OneToMany(mappedBy = "parkingRate", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var daysOfWeek: List<DayOfWeek>? = null,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val timeZone: ZoneId,
    val price: Int,
    @Id @GeneratedValue
    var id: Long = 0
)