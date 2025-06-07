package com.gsrajeni.appscheduler.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    confirmTitle: String,
    cancelTitle: String?,
    onDismiss: (() -> Unit)? = null,
    onConfirm: () -> Unit,
    onCancel: (() -> Unit)?
) {
    AlertDialog(modifier = modifier, onDismissRequest = {
        onDismiss?.invoke() ?: onCancel?.invoke()
    }, title = { Text(title) }, text = { Text(message) }, confirmButton = {
        TextButton(onClick = {
            onConfirm.invoke()
        }) {
            Text(confirmTitle)
        }
    }, dismissButton = {
        if (onCancel != null) {
            TextButton(onClick = {
                onCancel.invoke()
            }) {
                cancelTitle?.let {
                    Text(it)
                }
            }
        }
    })
}