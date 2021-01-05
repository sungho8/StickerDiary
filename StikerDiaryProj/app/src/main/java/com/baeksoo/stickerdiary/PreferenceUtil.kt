package com.baeksoo.stickerdiary

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun getThemeId() : Int{
        var key = getString("Theme","Theme0")
        var id : Int = R.style.Theme0
        when(key){
            "Theme0" -> id = R.style.Theme0
            "Theme1" -> id = R.style.Theme1
            "Theme2" -> id = R.style.Theme2
            "Theme3" -> id = R.style.Theme3
            "Theme4" -> id = R.style.Theme4
            "Theme5" -> id = R.style.Theme5
            "Theme6" -> id = R.style.Theme6
            "Theme7" -> id = R.style.Theme7
        }
        return id
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}