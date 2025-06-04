package com.gsrajeni.appscheduler.ui.screens.add_schedule

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gsrajeni.appscheduler.ui.components.DefaultAppBar
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        DefaultAppBar(
            title = "Add Schedule",
        )
    }) {
        Box(modifier = modifier.padding(it))

    }
}