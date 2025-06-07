package com.gsrajeni.appscheduler.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gsrajeni.appscheduler.data.constants.Constants

@Entity(tableName = Constants.updateLog)
data class UpdateLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,// this is a placeholder for the primary key
    @ColumnInfo(name = Constants.description) val description: String,
    @ColumnInfo(name = Constants.createdAt) var createdAt: Long = System.currentTimeMillis()
)