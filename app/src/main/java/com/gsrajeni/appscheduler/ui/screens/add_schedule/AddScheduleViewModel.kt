package com.gsrajeni.appscheduler.ui.screens.add_schedule

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.core.service.MyAlarmManager
import com.gsrajeni.appscheduler.data.model.AppInfo
import com.gsrajeni.appscheduler.data.model.ScheduleStatus
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.data.model.UpdateLog
import com.gsrajeni.appscheduler.data.room.AppDatabase
import com.gsrajeni.appscheduler.data.sources.InstalledAppDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val alarmManager: MyAlarmManager,
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

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun createSchedule(info: AppInfo, dateTime: Date) {
        val schedule = ScheduledApp(
            name = info.name,
            packageName = info.packageName,
            dateTime = dateTime,
            status = ScheduleStatus.Scheduled
        )
        viewModelScope.launch(Dispatchers.IO) {
            val id = database.scheduleDao().insert(app = schedule)
            if(id != -1L){
                database.scheduleDao().log(UpdateLog(
                    description = appContext.getString(
                        R.string.added_new_schedule_with_name,
                        schedule.name
                    ),
                ))
            }
            alarmManager.addAlarm(appContext, info.packageName, id, dateTime.time)
            _isScheduleCreated.value = true
        }
    }
}