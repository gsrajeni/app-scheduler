package com.gsrajeni.appscheduler.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "update_log")
data class UpdateLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,// this is a placeholder for the primary key
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "createdAt") var createdAt: Long = System.currentTimeMillis()
)