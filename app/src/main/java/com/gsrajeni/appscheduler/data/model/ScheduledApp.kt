package com.gsrajeni.appscheduler.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gsrajeni.appscheduler.data.constants.Constants
import java.util.Date

@Entity(tableName = Constants.appSchedules)
data class ScheduledApp(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,// this is a placeholder for the primary key
    @ColumnInfo(name = Constants.name) val name: String,
    @ColumnInfo(name = Constants.packageName) val packageName: String,
    @ColumnInfo(name = Constants.dateTime) val dateTime: Date,
    @ColumnInfo(name = Constants.status) var status: ScheduleStatus,
    @ColumnInfo(name = Constants.createdAt) var createdAt: Long = System.currentTimeMillis()
)

enum class ScheduleStatus {
    Scheduled, Executed
}
