package com.gsrajeni.appscheduler.ui.screens.dashboard

import AppRoute
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gsrajeni.appscheduler.ui.components.DefaultAppBar
import com.gsrajeni.appscheduler.ui.components.DefaultConfirmationDialog
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController
import com.gsrajeni.appscheduler.ui.screens.dashboard.components.ScheduledAppCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier, viewModel: DashboardViewModel = hiltViewModel()
) {
    val scheduledAppList = viewModel.scheduledApps.collectAsStateWithLifecycle()
    val navController = LocalNavHostController.current
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        DefaultAppBar(
            title = "App Scheduler", actions = {
                Text(
                    "Logs", modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = {
                            navController?.navigate(AppRoute.ScheduleLogRoute)
                        })
                )
            }, showBack = false
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController?.navigate(AppRoute.AddScheduleRoute)
        }, shape = RoundedCornerShape(percent = 50)) {
            Image(imageVector = Icons.Filled.Add, contentDescription = "")
        }
    }) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(scheduledAppList.value.size) {
                    ScheduledAppCard(
                        scheduledAppList.value[it],
                        onEditClick = {
                            showEditDialog = true
                        }, onDeleteClick = {
                            showDeleteDialog = true
                        })
                }
            }
        }
        if (showDeleteDialog) {
            val context = LocalContext.current
            DefaultConfirmationDialog(
                title = "Delete Schedule?",
                message = "Are you sure to delete the schedule? It is irreversible.",
                confirmTitle = "Delete",
                cancelTitle = "Cancel",
                onDismiss = {
                    showDeleteDialog = false
                },
                onConfirm = {
                    showDeleteDialog = false
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                },
                onCancel = {
                    showDeleteDialog = false
                }
            )
        }
    }
}