package com.gsrajeni.appscheduler.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gsrajeni.appscheduler.R
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable (RowScope.() -> Unit) = {},
    showBack: Boolean = true
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        actions = actions,
        navigationIcon = {
            if (showBack) {
                val navController = LocalNavHostController.current
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = modifier
                        .clickable {
                            navController?.popBackStack()
                        }
                        .padding(8.dp))
            }

        }
    )
}