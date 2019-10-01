package com.example.rating.model

import java.time.LocalTime
import java.time.ZoneId
import javax.persistence.*

@Entity
data class ParkingRate(
    @OneToMany(cascade = [CascadeType.ALL])
    val daysOfWeek: List<DayOfWeek>,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val timeZone: ZoneId,
    val price: Int,
    @Id @GeneratedValue
    var id: Long = 0
)