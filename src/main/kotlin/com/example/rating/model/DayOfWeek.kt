package com.example.rating.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.DayOfWeek
import javax.persistence.*

@Entity
data class DayOfWeek(
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_rate_id")
    var parkingRate: ParkingRate? = null,
    @Enumerated(EnumType.STRING)
    val day: DayOfWeek,
    @Id @GeneratedValue
    var id: Long = 0
)