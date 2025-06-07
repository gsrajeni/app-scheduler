package com.gsrajeni.appscheduler.ui.screens.edit_schedule

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.data.constants.Constants
import com.gsrajeni.appscheduler.ui.components.AppIconFromPackage
import com.gsrajeni.appscheduler.ui.components.DefaultAppBar
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScheduleScreen(modifier: Modifier = Modifier, id: Long?) {
    val viewModel = hiltViewModel<EditScheduleViewModel>()
    val mySchedule = viewModel.schedule.collectAsStateWithLifecycle()
    var updatedSchedule by remember { mutableStateOf(mySchedule) }
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
    val isScheduleUpdated by viewModel.isScheduleUpdated.collectAsStateWithLifecycle()
    val navController = LocalNavHostController.current
    LaunchedEffect(isScheduleUpdated) {
        if (isScheduleUpdated) {
            navController?.popBackStack()
        }
    }
    LaunchedEffect(id) {
        viewModel.getSchedule(id)
    }
    Scaffold(topBar = {
        DefaultAppBar(
            title = stringResource(R.string.add_schedule),
        )
    }) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val strokeWidth = 2.dp
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
                    .drawBehind {
                        val strokeColor = Color.Gray
                        val pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(
                                10f, 10f
                            ), 0f
                        )
                        val cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())

                        drawRoundRect(
                            color = strokeColor,
                            topLeft = Offset(strokeWidth.toPx() / 2, strokeWidth.toPx() / 2),
                            size = Size(
                                size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx()
                            ),
                            cornerRadius = cornerRadius,
                            style = Stroke(width = strokeWidth.toPx(), pathEffect = pathEffect)
                        )
                    }
                    .padding(16.dp), // Inner padding
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    updatedSchedule.value?.packageName?.apply {
                        AppIconFromPackage(this, modifier = Modifier
                            .padding(end = 24.dp)
                            .size(48.dp))
                    }
                    Text(
                        updatedSchedule.value?.name ?: stringResource(R.string.app_not_found),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ElevatedButton(onClick = { showDatePicker = true }) {
                    Text(stringResource(R.string.select_date))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = datePickerState.selectedDateMillis?.let { millis ->
                    "Selected Date: ${
                        DateFormat.format(
                            Constants.dd_mm_yyyy, millis
                        )
                    }"
                } ?: stringResource(R.string.no_date_selected))
            }
            if (showDatePicker) {
                DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(stringResource(R.string.ok))
                    }
                }, dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }

            TimePicker(
                modifier = Modifier.padding(top = 24.dp), state = timePickerState
            )
            ElevatedButton(onClick = {
                val calendar = Calendar.getInstance()
                datePickerState.selectedDateMillis?.let {
                    calendar.timeInMillis = it
                }
                calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                calendar.set(Calendar.MINUTE, timePickerState.minute)

                val newSchedule = mySchedule.value?.copy(
                    dateTime = Date(calendar.timeInMillis)
                )
                viewModel.editSchedule(newSchedule)
            }) {
                Text(stringResource(R.string.update_schedule))
            }

        }
    }
}