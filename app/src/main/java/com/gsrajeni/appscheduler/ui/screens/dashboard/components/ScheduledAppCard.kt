package com.gsrajeni.appscheduler.ui.screens.dashboard.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gsrajeni.appscheduler.data.model.ScheduleStatus
import com.gsrajeni.appscheduler.data.model.ScheduledApp

@Composable
fun ScheduledAppCard(
    app: ScheduledApp,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val bitmap = remember { drawableToBitmap(app.icon) }
            Icon(
                painter = BitmapPainter(bitmap.asImageBitmap()),
                contentDescription = app.name,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.name, fontWeight = FontWeight.Bold)
                Text(text = "Scheduled at: ${app.time}")
                Text(
                    text = "Status: ${app.status}",
                    color = when (app.status) {
                        ScheduleStatus.Scheduled -> Color.Blue
                        ScheduleStatus.Executed -> Color.Green
                        ScheduleStatus.Failed -> Color.Red
                    }
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }
                if (app.status == ScheduleStatus.Scheduled) {
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}
fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 1
    val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 1

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}