package com.gsrajeni.appscheduler.data.sources

import android.content.Context

class SharedPreferenceDataSource(
    private val context: Context
) {
    companion object {
        private val IS_ACCESSIBILITY_POPUP_SHOWN = "is_accessibility_popup_shown"
    }

    val sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val isAccessibilityPopupShown = sharedPref.getBoolean(IS_ACCESSIBILITY_POPUP_SHOWN, false)

    fun updateAccessiblityPopupShown(value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(IS_ACCESSIBILITY_POPUP_SHOWN, value)
        editor.apply()
    }
}