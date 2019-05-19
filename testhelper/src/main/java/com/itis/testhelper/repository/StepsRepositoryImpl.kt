package com.itis.testhelper.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itis.testhelper.model.Step
import com.itis.testhelper.utils.STRING_EMPTY

class StepsRepositoryImpl(private val sharedPreferences: SharedPreferences) : StepsRepository {

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

    companion object {
        private const val KEY_STEPS = "key_steps"
    }
}
