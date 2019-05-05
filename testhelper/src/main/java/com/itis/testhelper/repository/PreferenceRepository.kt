package com.itis.testhelper.repository

import com.itis.testhelper.model.Step

interface PreferenceRepository {

    fun saveAuthToken(token: String): Boolean

    fun getAuthToken(): String

    fun removeToken(): Boolean

    fun saveUserName(name: String)

    fun getUserName(): String

    fun getCurrentRepoName(): String

    fun saveRepoName(name: String)

    fun addStep(step: Step)

    fun getSteps(): List<Step>

    fun clearSteps()
}