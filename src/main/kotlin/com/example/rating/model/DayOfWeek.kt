package com.example.rating.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.DayOfWeek
import javax.persistence.*

@Entity
data class DayOfWeek(
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_rate_id")
    var parkingRate: ParkingRate? = null,
    val day: DayOfWeek,
    @Id @GeneratedValue
    var id: Long = 0
)