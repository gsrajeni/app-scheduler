package com.gsrajeni.appscheduler.ui.screens.schedule_logs

import androidx.lifecycle.ViewModel
import com.gsrajeni.appscheduler.data.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleLogViewModel @Inject constructor(
    database: AppDatabase
): ViewModel() {
    val scheduleLogs = database.scheduleDao().getAllLogs()
}