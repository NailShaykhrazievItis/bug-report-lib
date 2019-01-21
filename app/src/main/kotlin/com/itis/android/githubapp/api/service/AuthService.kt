package com.itis.android.githubapp.api.service

import com.google.gson.JsonObject
import com.itis.android.githubapp.model.Authorization
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("authorizations")
    fun authorize(@Header("Authorization") authorization: String,
                  @Body params: JsonObject): Single<Authorization>

    @POST("authorizations")
    fun getAuthorize(@Header("Authorization") authorization: String,
                     @Body params: JsonObject): Deferred<Authorization>
}
