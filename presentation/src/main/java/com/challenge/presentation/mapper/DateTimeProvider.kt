package com.challenge.presentation.mapper

import org.joda.time.DateTime

class DateTimeProvider() {
    fun today(): DateTime = DateTime.now()
}