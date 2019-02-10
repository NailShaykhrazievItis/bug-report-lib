package com.itis.android.githubapp.repository

interface PreferenceRepository {

    fun saveAuthToken(token: String): Boolean

    fun getAuthToken(): String

    fun removeToken(): Boolean
}
