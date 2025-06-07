package com.gsrajeni.appscheduler.ui.screens.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.core.service.MyAlarmManager
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.data.model.UpdateLog
import com.gsrajeni.appscheduler.data.room.AppDatabase
import com.gsrajeni.appscheduler.data.sources.SharedPreferenceDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val database: AppDatabase,
    private val alarmManager: MyAlarmManager,
    @ApplicationContext val context: Context,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : ViewModel() {
    private val _scheduledApps = MutableStateFlow<List<ScheduledApp>>(emptyList())
    val scheduledItems: Flow<List<ScheduledApp>> = database.scheduleDao().getAll()
    val _isAccessiblityShown =
        MutableStateFlow<Boolean>(sharedPreferenceDataSource.isAccessibilityPopupShown)
    val isAccessiblityShown = _isAccessiblityShown.asStateFlow()

    init {
        loadMockData()
    }

    fun loadMockData() {
        viewModelScope.launch(Dispatchers.IO) {
            database.scheduleDao().getAll().collectLatest {
                withContext(Dispatchers.Main) {
                    _scheduledApps.value = it
                }

            }
        }
    }

    fun deleteSchedule(app: ScheduledApp?) {
        app?.apply {
            viewModelScope.launch(Dispatchers.IO) {
                database.scheduleDao().delete(app)
                database.scheduleDao().log(
                    UpdateLog(
                        description = context.getString(
                            R.string.deleted_schedule_with_name, app.name
                        ),
                    )
                )
                alarmManager.deleteAlarm(context, app.packageName, app.id)
            }
        }
    }

    fun updateAccessiblityShownFlag(bool: Boolean) {
        sharedPreferenceDataSource.updateAccessiblityPopupShown(bool)
        _isAccessiblityShown.value = bool
    }
}
