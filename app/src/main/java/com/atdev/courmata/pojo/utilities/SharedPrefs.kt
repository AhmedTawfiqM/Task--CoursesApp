package com.atdev.courmata.pojo.utilities

import android.content.Context

object SharedPrefs {

    val PREF_FILE_NAME by lazy { "SharedPref" }
    val ISUSER_FirstTIME by lazy { "ISUSER_FirstTIME" }
    //

    fun setDataBoolean(context: Context, typeData: String, value: Boolean) {
        val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(typeData, value).apply()
    }

    fun isBooleanData(context: Context, typeData: String): Boolean {
        val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(typeData, false)
    }
}