package com.gsrajeni.appscheduler.ui.screens.schedule_logs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gsrajeni.appscheduler.ui.components.DefaultAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleLogsScreen(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        DefaultAppBar(title = "Schedule Logs")
    }) {
        Box(modifier = modifier.padding(it))
    }
}