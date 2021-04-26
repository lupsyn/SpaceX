package com.challenge.data_api.mapper

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

interface DateFormatter {
    fun format(dateValue: String): DateTime
}

class DateFormatterImpl() : DateFormatter {
    override fun format(dateValue: String): DateTime {
        val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

        return dateTimeFormatter.parseDateTime(dateValue.replace("Z", "+0100"))
    }
}
