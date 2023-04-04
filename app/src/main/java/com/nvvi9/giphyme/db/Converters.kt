package com.nvvi9.giphyme.db

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @TypeConverter
    fun fromDate(date: Date): Long = date.time
}