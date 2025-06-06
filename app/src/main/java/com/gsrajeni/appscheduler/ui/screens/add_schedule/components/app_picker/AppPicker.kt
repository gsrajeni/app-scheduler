package com.gsrajeni.appscheduler.ui.screens.add_schedule.components.app_picker

import InstalledAppsBottomSheet
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gsrajeni.appscheduler.data.model.AppInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPicker(
    modifier: Modifier = Modifier, onAppSelected: (AppInfo) -> Unit,
) {
    var pickedApp: AppInfo? by remember { mutableStateOf(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) } // Control visibility


    Box(modifier = modifier.padding(16.dp)) {
        val strokeWidth = 2.dp
        val primaryColor = MaterialTheme.colorScheme.primary
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(
                    if (pickedApp != null) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .drawBehind {
                    val strokeColor = if (pickedApp != null) primaryColor else Color.Gray
                    val pathEffect = if (pickedApp != null) null else PathEffect.dashPathEffect(
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
                .clickable {
                    showBottomSheet = true
                }
                .padding(16.dp), // Inner padding
            contentAlignment = Alignment.Center) {
            if (pickedApp == null) Text("Select an application")
            else Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                pickedApp?.icon?.apply {
                    Image(
                        painter = rememberAsyncImagePainter(model = this), // Use Coil's painter
                        contentDescription = "${pickedApp?.name ?: "App"} icon", // More descriptive
                        modifier = Modifier
                            .size(48.dp) // Example size, adjust as needed
                            .padding(end = 12.dp) // Adjust padding as needed
                    )
                }
                Text(
                    pickedApp?.name ?: "Name not found", style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
    if (showBottomSheet) {
        InstalledAppsBottomSheet(sheetState = sheetState, onDismissRequest = {
            showBottomSheet = false
        }, onAppClick = { appInfo ->
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                pickedApp = appInfo
                onAppSelected(appInfo)
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
        })
    }
}