package edu.msoe.textauto.DataBase

import androidx.room.TypeConverter
import java.lang.StringBuilder
import java.util.Date

class ConditionalTypeConverter {
    val SEPARATOR = "*(AZ@!"
    @TypeConverter
    fun fromData( data: List<String>): String {
        val output = StringBuilder()
        data.forEach{ s -> output.append(s+SEPARATOR) }
        return output.toString()
    }

    @TypeConverter
    fun toData(data: String): List<String> {
        return data.split(SEPARATOR)
    }
}