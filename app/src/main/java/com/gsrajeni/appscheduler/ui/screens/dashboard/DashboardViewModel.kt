package com.gsrajeni.appscheduler.ui.screens.dashboard

import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModel
import com.gsrajeni.appscheduler.data.model.ScheduleStatus
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.data.sources.InstalledAppDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val appDataSource = InstalledAppDataSource()
    private val _scheduledApps = MutableStateFlow<List<ScheduledApp>>(emptyList())
    val scheduledApps: StateFlow<List<ScheduledApp>> = _scheduledApps.asStateFlow()

    init {
        // Replace this with data from repository/database
        loadMockData()
    }

    private fun loadMockData() {
        // Replace with real icons from installed apps in production
        val dummyIcon = ColorDrawable()

        _scheduledApps.value = listOf(
            ScheduledApp("WhatsApp", "com.whatsapp", dummyIcon, "10:00 AM", ScheduleStatus.Scheduled),
            ScheduledApp("Gmail", "com.google.android.gm", dummyIcon, "02:30 PM", ScheduleStatus.Executed)
        )
    }

    fun addSchedule(app: ScheduledApp) {
        _scheduledApps.value = _scheduledApps.value + app
    }

    fun cancelSchedule(app: ScheduledApp) {
        _scheduledApps.value = _scheduledApps.value.map {
            if (it.packageName == app.packageName) it.copy(status = ScheduleStatus.Failed) else it
        }
    }

    fun editSchedule(updatedApp: ScheduledApp) {
        _scheduledApps.value = _scheduledApps.value.map {
            if (it.packageName == updatedApp.packageName) updatedApp else it
        }
    }
}
