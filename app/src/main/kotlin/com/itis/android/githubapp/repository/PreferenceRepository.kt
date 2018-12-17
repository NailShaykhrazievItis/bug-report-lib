package com.itis.android.githubapp.repository

import android.content.SharedPreferences
import com.itis.android.githubapp.utils.constants.STRING_EMPTY

class PreferenceRepository(private val sharedPreferences: SharedPreferences) {

    fun saveAuthToken(token: String) =
            sharedPreferences.edit().putString(KEY_TOKEN, token).commit()

    fun getAuthToken(): String = sharedPreferences.getString(KEY_TOKEN, STRING_EMPTY)
            ?: STRING_EMPTY

    companion object {
        const val KEY_TOKEN = "key token"
    }
}
