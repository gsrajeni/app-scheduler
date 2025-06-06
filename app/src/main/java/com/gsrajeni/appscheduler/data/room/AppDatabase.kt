package com.gsrajeni.appscheduler.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.data.room.converters.Converters
import com.gsrajeni.appscheduler.data.room.dao.ScheduleDao

@Database(entities = [ScheduledApp::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}