package com.example.system.ingredientsDB

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class DateConverters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? {
        return date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDate(timestamp: Long?): LocalDate? {
        return timestamp?.let {
            Date(it).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }
}