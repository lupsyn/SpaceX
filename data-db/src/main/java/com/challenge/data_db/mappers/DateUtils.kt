package com.challenge.data_db.mappers

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

interface DateUtils {
    fun format(dateValue: String): DateTime
    fun dateTimeToString(dateTime: DateTime): String
    fun getCurrentTimestamp(): Long
}

class DateUtilsImpl() : DateUtils {
    override fun format(dateValue: String): DateTime {
        val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

        return dateTimeFormatter.parseDateTime(dateValue.replace("Z", "+0100"))
    }

    override fun dateTimeToString(dateTime: DateTime): String =
        dateTime.toString().replace("+0100", "Z")

    override fun getCurrentTimestamp(): Long = System.currentTimeMillis()
}
