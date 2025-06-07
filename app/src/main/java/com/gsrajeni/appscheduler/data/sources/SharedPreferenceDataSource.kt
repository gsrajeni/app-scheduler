package com.gsrajeni.appscheduler.data.sources

import android.content.Context
import android.content.SharedPreferences
import com.gsrajeni.appscheduler.data.constants.Constants
import androidx.core.content.edit

class SharedPreferenceDataSource(
    context: Context
) {
    companion object {
        private const val IS_ACCESSIBILITY_POPUP_SHOWN = Constants.IS_ACCESSIBILITY_POPUP_SHOWN
    }

    val sharedPref: SharedPreferences = context.getSharedPreferences(Constants.myPrefs, Context.MODE_PRIVATE)
    val isAccessibilityPopupShown = sharedPref.getBoolean(IS_ACCESSIBILITY_POPUP_SHOWN, false)

    fun updateAccessibilityPopupShown(value: Boolean) {
        sharedPref.edit {
            putBoolean(IS_ACCESSIBILITY_POPUP_SHOWN, value)
        }
    }
}