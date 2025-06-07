package com.gsrajeni.appscheduler.data.room.converters

import androidx.room.TypeConverter
import com.gsrajeni.appscheduler.data.model.ScheduleStatus
import java.util.Date

class Converters {
    @TypeConverter
    fun fromScheduleStatus(status: ScheduleStatus): String {
        return status.name
    }

    @TypeConverter
    fun toScheduleStatus(value: String): ScheduleStatus {
        return ScheduleStatus.valueOf(value)
    }

    @TypeConverter
    fun fromDate(time: Date): Long {
        return time.time
    }

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }
}
