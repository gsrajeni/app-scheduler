package com.gsrajeni.appscheduler.ui.screens.splash

import AppRoute
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController
import com.gsrajeni.appscheduler.ui.theme.Purple40
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val navController = LocalNavHostController.current
    LaunchedEffect(Unit) {
        scope.launch {
            delay(500)
            navController?.navigate(AppRoute.DashboardRoute) {
                popUpTo(0)
            }
        }
    }
    Scaffold {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.app_scheduler), color = Purple40, fontSize = 48.sp)
                CircularProgressIndicator()
            }
        }

    }
}