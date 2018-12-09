package com.itis.android.githubapp.api.service

import com.google.gson.JsonObject
import com.itis.android.githubapp.model.Authorization
import com.itis.android.githubapp.model.User
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("authorizations")
    fun authorize(@Header("Authorization") authorization: String,
                  @Body params: JsonObject): Single<Authorization>

    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): Deferred<User>

    @GET("user")
    suspend fun getUserByToken(): Deferred<User>

    @GET("users/{user}/orgs")
    suspend fun getUserOrgs(@Path("user") user: String): Deferred<List<User>>
}
