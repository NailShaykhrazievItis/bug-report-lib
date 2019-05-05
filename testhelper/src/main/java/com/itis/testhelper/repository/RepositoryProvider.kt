package com.itis.testhelper.repository

import android.content.Context
import android.preference.PreferenceManager

class RepositoryProvider {

    companion object {

        val issueRepository: IssueRepository by lazy {
            IssueRepositoryImpl()
        }

        @Volatile
        private var preferenceRepository: PreferenceRepository? = null

        fun getPreferenceRepository(context: Context): PreferenceRepository =
                preferenceRepository ?: synchronized(this) {
                    preferenceRepository ?: initPreferenceRepository(context).also {
                        preferenceRepository = it
                    }
                }

        fun getPreferenceRepository(): PreferenceRepository? = preferenceRepository

        private fun initPreferenceRepository(context: Context) =
                PreferenceRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(context))
    }
}