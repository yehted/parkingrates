package com.example.rating.model

import java.time.OffsetTime
import javax.persistence.*

@Entity
data class ParkingRate(
    @OneToMany(mappedBy = "parkingRate", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var weekDays: List<WeekDay>? = null,
    val startTime: OffsetTime,
    val endTime: OffsetTime,
    val price: Int,
    @Id @GeneratedValue
    var id: Long = 0
) {
    fun isWithinRateWindow(start: OffsetTime, end: OffsetTime): Boolean {
        return (startTime.isBefore(start) || startTime.isEqual(start)) &&
                (endTime.isAfter(end) || endTime.isEqual(end))
    }

    fun isOverlappingTimeWith(otherRate: ParkingRate): Boolean {
        return (otherRate.startTime.isAfterOrEqual(startTime) && otherRate.startTime.isBeforeOrEqual(endTime)) ||
                (otherRate.endTime.isAfterOrEqual(startTime) && otherRate.endTime.isBeforeOrEqual(endTime))
    }

    fun isValid(): Boolean {
        return startTime.isBefore(endTime)
    }
}

fun OffsetTime.isAfterOrEqual(other: OffsetTime): Boolean = this.isAfter(other) || this.isEqual(other)
fun OffsetTime.isBeforeOrEqual(other: OffsetTime): Boolean = this.isBefore(other) || this.isEqual(other)
