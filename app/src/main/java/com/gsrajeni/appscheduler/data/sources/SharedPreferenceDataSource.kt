package com.gsrajeni.appscheduler.data.sources

import android.content.Context
import com.gsrajeni.appscheduler.data.constants.Constants

class SharedPreferenceDataSource(
    private val context: Context
) {
    companion object {
        private val IS_ACCESSIBILITY_POPUP_SHOWN = Constants.IS_ACCESSIBILITY_POPUP_SHOWN
    }

    val sharedPref = context.getSharedPreferences(Constants.myPrefs, Context.MODE_PRIVATE)
    val isAccessibilityPopupShown = sharedPref.getBoolean(IS_ACCESSIBILITY_POPUP_SHOWN, false)

    fun updateAccessiblityPopupShown(value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(IS_ACCESSIBILITY_POPUP_SHOWN, value)
        editor.apply()
    }
}