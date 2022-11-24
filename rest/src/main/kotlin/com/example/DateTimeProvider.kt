package com.example

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateTimeProvider {
    fun now():LocalDateTime {
        val currentMoment  = Clock.System.now()
        return currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
    }
}