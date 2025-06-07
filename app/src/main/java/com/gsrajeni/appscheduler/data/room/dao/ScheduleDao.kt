package com.gsrajeni.appscheduler.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.data.model.UpdateLog
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM app_schedules ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ScheduledApp>>

    @Insert
    fun insert(app: ScheduledApp): Long

    @Delete
    fun delete(user: ScheduledApp)


    @Query("SELECT * FROM app_schedules WHERE id = :id")
    fun getSchedule(id: Long?): Flow<ScheduledApp?>

    @Update
    fun updateSchedule(app: ScheduledApp)

    @Insert
    fun log(log: UpdateLog)

    @Query("SELECT * FROM update_log ORDER BY createdAt DESC")
    fun getAllLogs(): Flow<List<UpdateLog>>
}