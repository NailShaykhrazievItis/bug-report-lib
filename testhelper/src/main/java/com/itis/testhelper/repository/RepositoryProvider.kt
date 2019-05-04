package com.itis.testhelper.repository

import android.content.Context
import android.preference.PreferenceManager

class RepositoryProvider {

    companion object {

        @Volatile
        private var preferenceRepository: PreferenceRepository? = null

        fun getPreferenceRepository(context: Context): PreferenceRepository =
                preferenceRepository ?: synchronized(this) {
                    preferenceRepository ?: initPreferenceRepository(context).also {
                        preferenceRepository = it
                    }
                }

        private fun initPreferenceRepository(context: Context) =
                PreferenceRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(context))
    }
}