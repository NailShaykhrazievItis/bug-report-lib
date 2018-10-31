package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @POST("/authorizations")
    suspend fun authorize(@Query("auth_token") authToken: String): Deferred<User>

    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): Deferred<User>

    @GET("users/{user}/orgs")
    suspend fun getUserOrgs(@Path("user") user: String): Deferred<List<User>>
}
