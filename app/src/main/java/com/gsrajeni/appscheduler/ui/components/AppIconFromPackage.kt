package com.gsrajeni.appscheduler.ui.components

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AppIconFromPackage(packageName: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val packageManager = context.packageManager

    val imageBitmap by remember(packageName) {
        mutableStateOf(getAppIconAsImageBitmap(packageManager, packageName))
    }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            modifier = modifier
        )
    } ?: run {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = ""
        )
    }

}
fun getAppIconAsImageBitmap(pm: PackageManager, packageName: String): ImageBitmap? {
    return try {
        val drawable = pm.getApplicationIcon(packageName)
        val bitmap = drawableToBitmap(drawable)
        bitmap.asImageBitmap()
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 1
    val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 1

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}