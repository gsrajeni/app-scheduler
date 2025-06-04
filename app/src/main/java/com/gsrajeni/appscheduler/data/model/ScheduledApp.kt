package com.gsrajeni.appscheduler.data.model

import android.graphics.drawable.Drawable

data class ScheduledApp(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    val time: String,
    val status: ScheduleStatus
)

enum class ScheduleStatus {
    Scheduled, Executed, Failed
}
