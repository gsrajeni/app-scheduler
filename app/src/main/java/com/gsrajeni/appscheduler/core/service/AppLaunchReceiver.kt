package com.gsrajeni.appscheduler.core.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.gsrajeni.appscheduler.data.constants.Constants

class AppLaunchReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.getStringExtra(Constants.packageName) ?: return
        val appId = intent.getLongExtra(Constants.appId, -1L)
        val serviceIntent = Intent(context, AppLaunchService::class.java).apply {
            putExtra(Constants.packageName, packageName)
            putExtra(Constants.appId, appId)
        }
        ContextCompat.startForegroundService(context, serviceIntent)
    }
}
