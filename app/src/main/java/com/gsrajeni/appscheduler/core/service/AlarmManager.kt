package com.gsrajeni.appscheduler.core.service
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class MyAlarmManager {
    fun addAlarm(context: Context, packageName: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AppLaunchReceiver::class.java).apply {
            putExtra("packageName", packageName)
        }
        val requestCode = packageName.hashCode()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val triggerTimeMillis =  System.currentTimeMillis() + 20000
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            pendingIntent
        )
    }
}