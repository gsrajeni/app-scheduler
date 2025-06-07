package com.gsrajeni.appscheduler.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gsrajeni.appscheduler.R

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.empty_list),
            contentDescription = stringResource(R.string.empty_list)
        )
        Text(stringResource(R.string.there_is_no_schedule_available))
    }
}