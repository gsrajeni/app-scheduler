package com.gsrajeni.appscheduler.ui.screens.schedule_logs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gsrajeni.appscheduler.ui.components.DefaultAppBar
import com.gsrajeni.appscheduler.ui.components.EmptyContent
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleLogsScreen(modifier: Modifier = Modifier) {
    val viewmodel: ScheduleLogViewModel = hiltViewModel()
    val logList = viewmodel.scheduleLogs.collectAsStateWithLifecycle(listOf())
    Scaffold(topBar = {
        DefaultAppBar(title = "Schedule Logs")
    }) {
        Box(modifier = modifier.padding(it), contentAlignment = Alignment.Center) {
            if (logList.value.isEmpty()) {
                EmptyContent()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    items(logList.value.size) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text(
                                    "Time: ${
                                        SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa").format(
                                            Date(
                                                logList.value[it].createdAt
                                            )
                                        )
                                    }"
                                )
                                Text(
                                    logList.value[it].description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}