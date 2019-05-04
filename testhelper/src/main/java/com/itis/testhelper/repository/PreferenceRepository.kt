package com.itis.testhelper.repository

interface PreferenceRepository {

    fun saveAuthToken(token: String): Boolean

    fun getAuthToken(): String

    fun removeToken(): Boolean
}