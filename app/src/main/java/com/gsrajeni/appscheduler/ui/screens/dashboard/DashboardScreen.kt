package com.gsrajeni.appscheduler.ui.screens.dashboard

import AppRoute
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.ui.components.DefaultAppBar
import com.gsrajeni.appscheduler.ui.components.DefaultConfirmationDialog
import com.gsrajeni.appscheduler.ui.components.EmptyContent
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController
import com.gsrajeni.appscheduler.ui.screens.dashboard.components.ScheduledAppCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier, viewModel: DashboardViewModel = hiltViewModel()
) {
    val scheduledAppList =
        viewModel.scheduledItems.collectAsStateWithLifecycle(initialValue = listOf())
    val navController = LocalNavHostController.current
    var scheduleToDelete: ScheduledApp? by remember { mutableStateOf(null) }
    val isAccessiblityShown by viewModel.isAccessiblityShown.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Scaffold(topBar = {
        DefaultAppBar(
            title = stringResource(R.string.app_scheduler), actions = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = {
                        navController?.navigate(AppRoute.ScheduleLogRoute)
                    })
                ){
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(R.string.logs)
                    )
                    Text(
                        stringResource(R.string.logs),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }, showBack = false
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController?.navigate(AppRoute.AddScheduleRoute)
        }, shape = RoundedCornerShape(percent = 50)) {
            Image(
                imageVector = Icons.Filled.Add,
                contentDescription = context.getString(R.string.add_schedule)
            )
        }
    }) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            if (scheduledAppList.value.isEmpty()) EmptyContent(modifier = Modifier.wrapContentSize())
            else LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 64.dp)
            ) {

                items(scheduledAppList.value.size) { index ->
                    ScheduledAppCard(scheduledAppList.value[index], onEditClick = {
                        navController?.navigate(AppRoute.EditScheduleRoute(scheduledAppList.value[index].id))
                    }, onDeleteClick = {
                        scheduleToDelete = scheduledAppList.value[index]
                    })
                }
            }
        }
        if (scheduleToDelete != null) {
            DefaultConfirmationDialog(
                title = stringResource(R.string.delete_schedule),
                message = stringResource(R.string.are_you_sure_to_delete_the_schedule_it_is_irreversible),
                confirmTitle = stringResource(R.string.delete),
                cancelTitle = stringResource(R.string.cancel),
                onDismiss = {
                    scheduleToDelete = null
                },
                onConfirm = {
                    viewModel.deleteSchedule(scheduleToDelete)
                    scheduleToDelete = null
                },
                onCancel = {
                    scheduleToDelete = null
                })
        }

        if (!isAccessiblityShown) {
            DefaultConfirmationDialog(
                title = stringResource(R.string.permission_required),
                message = stringResource(R.string.please_enable_accessibility_access_for_this_app_to_allow_background_app_launching_otherwise_your_app_will_not_be_able_to_open_other_app_when_it_will_be_in_background),
                confirmTitle = stringResource(R.string.open_settings),
                cancelTitle = stringResource(R.string.cancel),
                onDismiss = {
                    viewModel.updateAccessiblityShownFlag(true)
                },
                onConfirm = {
                    viewModel.updateAccessiblityShownFlag(true)
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    context.startActivity(intent)
                    viewModel.updateAccessiblityShownFlag(true)
                },
                onCancel = {
                    viewModel.updateAccessiblityShownFlag(true)
                })
        }

    }
}