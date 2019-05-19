package com.itis.testhelper.repository

import android.content.Context
import android.preference.PreferenceManager

class RepositoryProvider {

    companion object {

        val issueRepository: IssueRepository by lazy {
            IssueRepositoryImpl()
        }

        @Volatile
        private var stepsRepository: StepsRepository? = null
        @Volatile
        private var userRepository: UserRepository? = null

        fun getStepsRepository(context: Context): StepsRepository =
                stepsRepository ?: synchronized(this) {
                    stepsRepository ?: initStepsRepository(context).also {
                        stepsRepository = it
                    }
                }

        fun getUserRepository(context: Context): UserRepository =
                userRepository ?: synchronized(this) {
                    userRepository ?: initUserRepository(context).also {
                        userRepository = it
                    }
                }

        fun getUserRepository(): UserRepository? = userRepository

        private fun initStepsRepository(context: Context) =
                StepsRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(context))

        private fun initUserRepository(context: Context) =
                UserRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(context))
    }
}