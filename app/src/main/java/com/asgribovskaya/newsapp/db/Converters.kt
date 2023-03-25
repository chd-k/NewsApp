package com.asgribovskaya.newsapp.db

import androidx.room.TypeConverter
import com.asgribovskaya.newsapp.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String =
        source.id + "+" + source.name

    @TypeConverter
    fun toSource(string: String) : Source =
        Source(string.split("+")[0], string.split("+")[1])
}