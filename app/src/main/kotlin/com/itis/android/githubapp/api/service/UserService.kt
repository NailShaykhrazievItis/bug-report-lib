package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.User
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): Deferred<User>

    @GET("user")
    suspend fun getUserByToken(): Single<User>

    @GET("users/{user}/orgs")
    suspend fun getUserOrgs(@Path("user") user: String): Deferred<List<User>>
}
