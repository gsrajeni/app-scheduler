package com.gsrajeni.appscheduler.ui.screens.add_schedule

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrajeni.appscheduler.data.model.AppInfo
import com.gsrajeni.appscheduler.data.model.ScheduleStatus
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.data.room.AppDatabase
import com.gsrajeni.appscheduler.data.sources.InstalledAppDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val database: AppDatabase,
    private val installedAppDataSource: InstalledAppDataSource
) : ViewModel() {
    private val _installedAppList = MutableStateFlow(listOf<AppInfo>())
    val installedAppList = _installedAppList.asStateFlow()
    val _isScheduleCreated = MutableStateFlow(false)
    val isScheduleCreated = _isScheduleCreated.asStateFlow()
    init {
        loadApp()
    }

    private fun loadApp() {
        _installedAppList.value = installedAppDataSource.getInstalledApps(appContext)
    }

    fun createSchedule(info: AppInfo, date: Long, hour: Int, minute: Int) {
        val schedule = ScheduledApp(
            name = info.name,
            packageName = info.packageName,
            date = date,
            hour = hour,
            minute = minute,
            status = ScheduleStatus.Scheduled
        )
        viewModelScope.launch(Dispatchers.IO) {
            database.scheduleDao().insertAll(schedule)
            _isScheduleCreated.value = true
        }
    }
}