package com.nvvi9.giphyme.db;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public Date toDate(Long value) {
        return new Date(value);
    }

    @TypeConverter
    public Long fromDate(Date date) {
        return date.getTime();
    }
}
