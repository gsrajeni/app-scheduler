package com.gsrajeni.appscheduler.ui.screens.dashboard.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.data.constants.Constants
import com.gsrajeni.appscheduler.data.model.ScheduleStatus
import com.gsrajeni.appscheduler.data.model.ScheduledApp
import com.gsrajeni.appscheduler.ui.components.AppIconFromPackage
import java.text.SimpleDateFormat

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun ScheduledAppCard(
    app: ScheduledApp, onEditClick: () -> Unit, onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AppIconFromPackage(packageName = app.packageName, modifier = Modifier.size(48.dp))

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.name, fontWeight = FontWeight.Bold)
                Text(
                    text = stringResource(
                        R.string.scheduled_at, getFormattedScheduleTime(app)
                    )
                )
                Text(
                    text = stringResource(R.string.status, app.status), color = when (app.status) {
                        ScheduleStatus.Scheduled -> Color.Blue
                        ScheduleStatus.Executed -> Color.Green
                    }
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (app.status == ScheduleStatus.Scheduled) {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                }
            }
        }
    }
}

fun getFormattedScheduleTime(app: ScheduledApp): String {
    return SimpleDateFormat(Constants.dd_mm_yyyy_hh_mm_a).format(app.dateTime)
}