package com.gsrajeni.appscheduler.ui.screens.add_schedule

import android.text.format.DateFormat
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gsrajeni.appscheduler.data.model.AppInfo
import com.gsrajeni.appscheduler.ui.components.DefaultAppBar
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController
import com.gsrajeni.appscheduler.ui.screens.add_schedule.components.app_picker.AppPicker
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<AddScheduleViewModel>()
    var selectedApp: AppInfo? by remember { mutableStateOf(null) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= Calendar.getInstance().timeInMillis
            }
        })
    var showDatePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
    )
    val context = LocalContext.current
    val isScheduleCreated by viewModel.isScheduleCreated.collectAsStateWithLifecycle()
    fun isEverythingValid(): Boolean {
        if (selectedApp == null) return false
        if (datePickerState.selectedDateMillis == null) return false
        if (timePickerState.hour == 0 && timePickerState.minute == 0) return false
        return true
    }
    val navController = LocalNavHostController.current
    LaunchedEffect(isScheduleCreated) {
        if (isScheduleCreated){
            navController?.popBackStack()
        }
    }
    Scaffold(topBar = {
        DefaultAppBar(
            title = "Add Schedule",
        )
    }) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppPicker(onAppSelected = {
                selectedApp = it
            })
            if (selectedApp != null) {
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElevatedButton(onClick = { showDatePicker = true }) {
                        Text("Select Date")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = datePickerState.selectedDateMillis?.let { millis ->
                            "Selected Date: ${
                                DateFormat.format(
                                    "dd/MM/yyyy",
                                    millis
                                )
                            }"
                        } ?: "No date selected"
                    )
                }
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState
                        )
                    }
                }
                if (selectedApp != null)
                    TimePicker(
                        modifier = Modifier.padding(top = 24.dp),
                        state = timePickerState
                    )
                if (selectedApp != null)
                    ElevatedButton(onClick = {
                        if (isEverythingValid()) {
                            val calendar = Calendar.getInstance()
                            datePickerState.selectedDateMillis?.let {
                                calendar.timeInMillis = it
                            }
                            calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            calendar.set(Calendar.MINUTE, timePickerState.minute)
                            viewModel.createSchedule(
                                selectedApp!!,
                                Date(calendar.timeInMillis)
                            )
                        }
                        else{
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Add Schedule")
                    }
            }

        }
    }
}