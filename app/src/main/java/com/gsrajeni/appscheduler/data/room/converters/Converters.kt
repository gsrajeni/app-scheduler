package com.gsrajeni.appscheduler.data.room.converters

import androidx.room.TypeConverter
import com.gsrajeni.appscheduler.data.model.ScheduleStatus

class Converters {
    @TypeConverter
    fun fromScheduleStatus(status: ScheduleStatus): String {
        return status.name
    }

    @TypeConverter
    fun toScheduleStatus(value: String): ScheduleStatus {
        return ScheduleStatus.valueOf(value)
    }
}
