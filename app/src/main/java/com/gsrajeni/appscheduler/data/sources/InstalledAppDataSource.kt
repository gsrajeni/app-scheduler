package com.gsrajeni.appscheduler.data.sources
import android.content.Context
import android.content.Intent
import com.gsrajeni.appscheduler.data.model.AppInfo

class InstalledAppDataSource {
    fun getInstalledApps(context: Context?): List<AppInfo> {
        val packageManager = context?.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        packageManager?.apply {
            val resolveInfos = packageManager.queryIntentActivities(intent, 0)
            val apps = resolveInfos.map {
                val appName = it.loadLabel(packageManager).toString()
                val packageName = it.activityInfo.packageName
                val icon = it.loadIcon(packageManager)
                AppInfo(appName, packageName, icon)
            }
            return apps.sortedBy { it.name.lowercase() }
        }
        return listOf()
    }

}