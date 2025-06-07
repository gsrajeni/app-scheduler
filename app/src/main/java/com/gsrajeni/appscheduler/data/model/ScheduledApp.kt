package com.gsrajeni.appscheduler.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_schedules")
data class ScheduledApp(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,// this is a placeholder for the primary key
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "package_name") val packageName: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "hour") val hour: Int,
    @ColumnInfo(name = "minute") val minute: Int,
    @ColumnInfo(name = "status") var status: ScheduleStatus,
    @ColumnInfo(name = "createdAt") var createdAt: Long = System.currentTimeMillis()
)

enum class ScheduleStatus {
    Scheduled, Executed
}
