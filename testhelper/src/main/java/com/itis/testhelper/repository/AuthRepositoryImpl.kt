package com.itis.testhelper.repository

import android.util.Base64
import androidx.annotation.VisibleForTesting
import com.google.gson.JsonObject
import com.itis.testhelper.api.ApiFactory
import com.itis.testhelper.model.Authorization
import com.itis.testhelper.utils.CLIENT_ID
import com.itis.testhelper.utils.CLIENT_SECRET
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    override suspend fun getAuthAsync(login: String, password: String): Authorization =
            withContext(Dispatchers.IO) {
                val authorizationString = createAuthorizationString(login, password)
                ApiFactory.authService.authorizeAsync(authorizationString, createAuthorizationParam()).await()
            }

    @VisibleForTesting
    fun createAuthorizationString(login: String, password: String): String {
        val combinedStr = String.format("%s:%s", login, password)
        val authorizationString = BASIC_AUTHORIZATION + Base64.encodeToString(combinedStr.toByteArray(), Base64.DEFAULT)
        return authorizationString.trim()
    }

    @VisibleForTesting
    fun createAuthorizationParam(): JsonObject {
        val param = JsonObject()
        param.addProperty(CLIENT_ID_PROPERTY, CLIENT_ID)
        param.addProperty(CLIENT_SECRET_PROPERTY, CLIENT_SECRET)
        return param
    }

    companion object {
        private const val BASIC_AUTHORIZATION = "Basic "
        private const val CLIENT_ID_PROPERTY = "client_id"
        private const val CLIENT_SECRET_PROPERTY = "client_secret"
    }
}
