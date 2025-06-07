package com.gsrajeni.appscheduler.ui.screens.edit_schedule

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrajeni.appscheduler.core.service.MyAlarmManager
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.data.model.UpdateLog
import com.gsrajeni.appscheduler.data.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditScheduleViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val database: AppDatabase,
    private val alarmManager: MyAlarmManager
) : ViewModel() {
    val _isScheduleUpdated = MutableStateFlow(false)
    var isScheduleUpdated = _isScheduleUpdated.asStateFlow()
    private val _schedule = MutableStateFlow<ScheduledApp?>(null)
    val schedule = _schedule.asStateFlow()

    fun editSchedule(app: ScheduledApp?) {
        viewModelScope.launch(Dispatchers.IO) {
            app?.apply {
                database.scheduleDao().updateSchedule(app)
                database.scheduleDao().log(
                    UpdateLog(
                        description = "Edited schedule with id: ${app.id}",
                    )
                )
                alarmManager.addAlarm(appContext, app.packageName, app.id, app.dateTime.time)
                _isScheduleUpdated.value = true
            }
        }
    }

    fun getSchedule(id: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            database.scheduleDao().getSchedule(id).collectLatest {
                _schedule.value = it
            }
        }
    }
}