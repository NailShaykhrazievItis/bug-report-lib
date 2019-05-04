package com.itis.testhelper.repository

import android.content.SharedPreferences
import com.itis.testhelper.utils.STRING_EMPTY

class PreferenceRepositoryImpl(private val sharedPreferences: SharedPreferences) : PreferenceRepository {

    override fun saveAuthToken(token: String) =
            sharedPreferences.edit().putString(KEY_TOKEN, token).commit()

    override fun getAuthToken(): String = sharedPreferences.getString(KEY_TOKEN, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun removeToken() = saveAuthToken(STRING_EMPTY)

    companion object {
        const val KEY_TOKEN = "key token"
    }
}
