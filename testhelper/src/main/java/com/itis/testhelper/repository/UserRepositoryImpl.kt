package com.itis.testhelper.repository

import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.itis.testhelper.api.ApiFactory
import com.itis.testhelper.model.Authorization
import com.itis.testhelper.model.User
import com.itis.testhelper.utils.CLIENT_ID
import com.itis.testhelper.utils.CLIENT_SECRET
import com.itis.testhelper.utils.STRING_EMPTY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val sharedPreferences: SharedPreferences) : UserRepository {

    override suspend fun getAuthAsync(login: String, password: String): Authorization =
            withContext(Dispatchers.IO) {
                val authorizationString = createAuthorizationString(login, password)
                ApiFactory.githubService.authorizeAsync(authorizationString, createAuthorizationParam()).await()
            }

    override suspend fun getUserByTokenAsync(): User = withContext(Dispatchers.IO) {
        ApiFactory.githubService.getUserByTokenAsync().await()
    }

    override suspend fun getUser(): User? = withContext(Dispatchers.IO) {
        sharedPreferences.getString(KEY_USER, STRING_EMPTY)?.let {
            if (it.isNotEmpty()) {
                Gson().fromJson(it, object : TypeToken<User>() {}.type)
            } else {
                null
            }
        }
    }

    override fun saveAuthToken(token: String) =
            sharedPreferences.edit().putString(KEY_TOKEN, token).commit()

    override fun getAuthToken(): String = sharedPreferences.getString(KEY_TOKEN, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun removeToken() = saveAuthToken(STRING_EMPTY)

    override fun saveUserName(name: String) = sharedPreferences.edit().putString(KEY_USER_NAME, name).apply()

    override fun getUserName(): String = sharedPreferences.getString(KEY_USER_NAME, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun saveUser(user: User) {
        val json = Gson().toJson(user)
        sharedPreferences.edit().putString(KEY_USER, json).apply()
    }

    override fun removeUser() = sharedPreferences.edit().putString(KEY_USER, STRING_EMPTY).apply()

    override fun getCurrentRepoName(): String = sharedPreferences.getString(KEY_REPO, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun saveRepoName(name: String) = sharedPreferences.edit().putString(KEY_REPO, name).apply()

    private fun createAuthorizationString(login: String, password: String): String {
        val combinedStr = String.format("%s:%s", login, password)
        val authorizationString = BASIC_AUTHORIZATION + Base64.encodeToString(combinedStr.toByteArray(), Base64.DEFAULT)
        return authorizationString.trim()
    }

    private fun createAuthorizationParam(): JsonObject {
        val param = JsonObject()
        param.addProperty(CLIENT_ID_PROPERTY, CLIENT_ID)
        param.addProperty(CLIENT_SECRET_PROPERTY, CLIENT_SECRET)
        return param
    }

    companion object {
        private const val BASIC_AUTHORIZATION = "Basic "
        private const val CLIENT_ID_PROPERTY = "client_id"
        private const val CLIENT_SECRET_PROPERTY = "client_secret"

        private const val KEY_TOKEN = "key_report_user_token"
        private const val KEY_REPO = "key_report_repo"
        private const val KEY_USER = "key_report_user"
        private const val KEY_USER_NAME = "key_report_user_name"
    }
}
