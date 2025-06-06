package com.gsrajeni.appscheduler.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM app_schedules")
    fun getAll(): Flow<List<ScheduledApp>>

    @Insert
    fun insertAll(vararg users: ScheduledApp)

    @Delete
    fun delete(user: ScheduledApp)
}