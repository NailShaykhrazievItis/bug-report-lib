package com.itis.testhelper.repository

import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson
import com.itis.testhelper.api.ApiFactory
import com.itis.testhelper.model.Repository
import com.itis.testhelper.model.User
import com.itis.testhelper.model.request.AuthBody
import com.itis.testhelper.model.response.Authorization
import com.itis.testhelper.utils.STRING_EMPTY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val sharedPreferences: SharedPreferences) : UserRepository {

    override suspend fun getAuthAsync(login: String, password: String, note: String): Authorization =
            withContext(Dispatchers.IO) {
                val authorizationString = createAuthorizationString(login, password)
                ApiFactory.githubService.authorizeAsync(authorizationString, AuthBody(note)).await()
            }

    override suspend fun getUserByTokenAsync(): User = withContext(Dispatchers.IO) {
        val user = ApiFactory.githubService.getUserByTokenAsync().await()
        saveUser(user)
        user
    }

    override suspend fun getUser(): User? = withContext(Dispatchers.IO) {
        sharedPreferences.getString(KEY_USER, STRING_EMPTY)?.let {
            if (it.isNotEmpty()) {
                Gson().fromJson(it, User::class.java)
            } else {
                null
            }
        }
    }

    override suspend fun getRepositoriesAsync(): List<Repository> = withContext(Dispatchers.IO) {
        ApiFactory.githubService.getUserReposAsync().await()
    }

    override fun saveAuthToken(token: String) =
            sharedPreferences.edit().putString(KEY_TOKEN, token).commit()

    override fun getAuthToken(): String = sharedPreferences.getString(KEY_TOKEN, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun removeToken() = saveAuthToken(STRING_EMPTY)

    override fun saveUser(user: User) {
        val json = Gson().toJson(user)
        sharedPreferences.edit().putString(KEY_USER, json).apply()
    }

    override fun removeUser() = sharedPreferences.edit().putString(KEY_USER, STRING_EMPTY).apply()

    override fun getCurrentRepoName(): String = sharedPreferences.getString(KEY_REPO, STRING_EMPTY)
            ?: STRING_EMPTY

    override fun saveRepoName(name: String) = sharedPreferences.edit().putString(KEY_REPO, name).apply()

    override fun removeRepoName() = saveRepoName(STRING_EMPTY)

    private fun createAuthorizationString(login: String, password: String): String {
        val combinedStr = String.format("%s:%s", login, password)
        val authorizationString = BASIC_AUTHORIZATION + Base64.encodeToString(combinedStr.toByteArray(), Base64.DEFAULT)
        return authorizationString.trim()
    }

    companion object {
        private const val BASIC_AUTHORIZATION = "Basic "
        private const val CLIENT_ID_PROPERTY = "client_id"
        private const val CLIENT_SECRET_PROPERTY = "client_secret"

        private const val KEY_TOKEN = "key_report_user_token"
        private const val KEY_REPO = "key_report_repo"
        private const val KEY_USER = "key_report_user"
    }
}
