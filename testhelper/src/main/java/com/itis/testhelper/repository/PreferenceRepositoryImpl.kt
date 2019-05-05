package com.itis.testhelper.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itis.testhelper.model.Step
import com.itis.testhelper.utils.STRING_EMPTY

class PreferenceRepositoryImpl(private val sharedPreferences: SharedPreferences) : PreferenceRepository {

    override fun saveAuthToken(token: String) =
            sharedPreferences.edit().putString(KEY_TOKEN, token).commit()

    override fun getAuthToken(): String = sharedPreferences.getString(KEY_TOKEN, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun removeToken() = saveAuthToken(STRING_EMPTY)

    override fun addStep(step: Step) {
        val steps = getSteps() + step
        val stepsJson = Gson().toJson(steps)
        sharedPreferences.edit().putString(KEY_STEPS, stepsJson).apply()
    }

    override fun getSteps(): List<Step> =
            sharedPreferences.getString(KEY_STEPS, STRING_EMPTY)?.let {
                if (it.isNotEmpty()) {
                    Gson().fromJson(it, object : TypeToken<List<Step>>() {}.type)
                } else {
                    ArrayList<Step>(0)
                }
            } ?: ArrayList(0)

    override fun clearSteps() = sharedPreferences.edit().putString(KEY_STEPS, STRING_EMPTY).apply()

    override fun saveUserName(name: String) = sharedPreferences.edit().putString(KEY_USER, name).apply()

    override fun getUserName(): String = sharedPreferences.getString(KEY_USER, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun getCurrentRepoName(): String = sharedPreferences.getString(KEY_REPO, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun saveRepoName(name: String) = sharedPreferences.edit().putString(KEY_REPO, name).apply()

    companion object {
        private const val KEY_TOKEN = "key_token"
        private const val KEY_STEPS = "key_steps"
        private const val KEY_USER = "key_user"
        private const val KEY_REPO = "key_repo"
    }
}
