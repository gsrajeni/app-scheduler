package com.gsrajeni.appscheduler.core.service

import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.data.constants.Constants
import com.gsrajeni.appscheduler.data.model.ScheduleStatus
import com.gsrajeni.appscheduler.data.model.UpdateLog
import com.gsrajeni.appscheduler.data.room.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppLaunchService : Service() {
    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var coroutineScope: CoroutineScope
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val packageName = intent?.getStringExtra(Constants.packageName)
        val appId: Long? = intent?.getLongExtra(Constants.appId, -1L)
        val notification = NotificationCompat.Builder(this, Constants.appLaunchChannel)
            .setContentTitle(getString(R.string.app_launch_scheduler)).setContentText(
                getString(
                    R.string.launching_app, packageName
                )
            ).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                appId?.toInt() ?: 0,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(
                appId?.toInt() ?: 0, notification
            )
        }

        packageName?.let {
            appId?.apply {
                coroutineScope.launch(Dispatchers.IO) {
                    val app = database.scheduleDao().getSchedule(appId)
                    app.collectLatest {
                        if (it != null) {
                            it.status = ScheduleStatus.Executed
                            database.scheduleDao().log(
                                UpdateLog(
                                    description = getString(
                                        R.string.executed_schedule_with_name, it.name
                                    ),
                                )
                            )
                            database.scheduleDao().updateSchedule(it)
                            this.coroutineContext.job.cancel()
                        }

                    }
                }
            }
            if (AccessibilityServiceBridge.instance != null) {
                //If accessibility service is available, then try to run the app with that service
                AccessibilityServiceBridge.instance?.launchApp(packageName)
            } else {
                if (isAppInForeground(this)) {
                    //App is in foreground, launch the application.
                    launchApp(it)
                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                    // For Android 10 and below, try to launch the app directly
                    // as background activity starts are generally allowed.
                    launchApp(it)
                } else {
                    //Accessibility mode is not enabled and app is not in foreground, so show the notification, so that user can tap and launch the app
                    showLaunchNotification(this, packageName)
                }
            }
            stopSelf()
        }
        return START_NOT_STICKY
    }

    private fun launchApp(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(launchIntent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

private fun showLaunchNotification(context: Context, packageName: String) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (launchIntent != null) {
        val pendingIntent = PendingIntent.getActivity(
            context.applicationContext,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.appLaunchChannel)
            .setContentTitle(context.getString(R.string.app_launch_ready)).setContentText(
                context.getString(
                    R.string.tap_to_open, packageName
                )
            ).setSmallIcon(R.drawable.ic_launcher_foreground).setContentIntent(pendingIntent)
            .setAutoCancel(true).build()

        val notificationId = packageName.hashCode()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}

private fun isAppInForeground(context: Context): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses = activityManager.runningAppProcesses ?: return false
    val packageName = context.packageName
    return appProcesses.any {
        it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && it.processName == packageName
    }
}