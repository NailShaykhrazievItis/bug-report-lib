package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{login}")
    fun getUserAsync(@Path("login") login: String): Deferred<User>

    @GET("user")
    fun getUserByTokenAsync(): Deferred<User>

    @GET("users/{user}/orgs")
    fun getUserOrgsAsync(@Path("user") user: String): Deferred<List<User>>
}
