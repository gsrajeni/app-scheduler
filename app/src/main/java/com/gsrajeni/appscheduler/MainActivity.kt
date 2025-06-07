package com.gsrajeni.appscheduler

import AppNavigationGraph
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.gsrajeni.appscheduler.data.constants.Constants
import com.gsrajeni.appscheduler.ui.components.DefaultConfirmationDialog
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController
import com.gsrajeni.appscheduler.ui.theme.AppSchedulerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val showNotificationPermissionDialog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        getPermissionForNotification()
        enableEdgeToEdge()
        setContent {
            AppSchedulerTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(
                    LocalNavHostController provides navController,
                ) {
                    AppNavigationGraph()
                    if (showNotificationPermissionDialog.value) {
                        DefaultConfirmationDialog(
                            title = stringResource(R.string.notification_permission_required),
                            message = stringResource(R.string.this_app_requires_notification_permission_to_function_properly_please_enable_it_in_settings),
                            confirmTitle = stringResource(R.string.open_settings),
                            cancelTitle = null,
                            onConfirm = {
                                showNotificationPermissionDialog.value = false
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            },
                            onCancel = null,
                            onDismiss = {})

                    }
                }
            }
        }
    }


    private fun getPermissionForNotification() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                showNotificationPermissionDialog.value = true
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Nothing to do, permission is granted
            } else {
                // Request for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            Constants.appLaunchChannel,
            Constants.appLaunchService, NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}