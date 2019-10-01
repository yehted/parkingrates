package com.example.rating.model

import java.time.DayOfWeek
import javax.persistence.*

@Entity
data class DayOfWeek(
//    @ManyToOne
//    @JoinColumn(name = "parking_rate_id", nullable = false)
//    val parkingRate: ParkingRate? = null,
    val day: DayOfWeek,
    @Id @GeneratedValue
    var id: Long = 0
)