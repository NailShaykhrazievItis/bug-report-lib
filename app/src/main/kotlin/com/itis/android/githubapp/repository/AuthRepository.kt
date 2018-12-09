package com.itis.android.githubapp.repository

import android.util.Base64
import com.google.gson.JsonObject
import com.itis.android.githubapp.BuildConfig
import com.itis.android.githubapp.api.service.AuthService
import com.itis.android.githubapp.model.Authorization
import com.itis.android.githubapp.utils.extensions.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single

class AuthRepository(private val authService: AuthService) {

    fun auth(login: String, password: String): Single<Authorization> {
        val authorizationString = createAuthorizationString(login, password)
        return authService.authorize(authorizationString, createAuthorizationParam())
                .subscribeSingleOnIoObserveOnUi()
    }

    private fun createAuthorizationString(login: String, password: String): String {
        val combinedStr = String.format("%s:%s", login, password)
        val authorizationString = BASIC_AUTHORIZATION + Base64.encodeToString(combinedStr.toByteArray(), Base64.DEFAULT)
        return authorizationString.trim()
    }

    private fun createAuthorizationParam(): JsonObject {
        val param = JsonObject()
        param.addProperty(CLIENT_ID_PROPERTY, BuildConfig.CLIENT_ID)
        param.addProperty(CLIENT_SECRET_PROPERTY, BuildConfig.CLIENT_SECRET)
        return param
    }

    companion object {
        private const val BASIC_AUTHORIZATION = "Basic "
        private const val CLIENT_ID_PROPERTY = "client_id"
        private const val CLIENT_SECRET_PROPERTY = "client_secret"
    }
}
