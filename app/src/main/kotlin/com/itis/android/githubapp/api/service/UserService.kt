package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.User
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Single<User>

    @GET("user")
    fun getUserByToken(): Single<User>

    @GET("users/{user}/orgs")
    fun getUserOrgs(@Path("user") user: String): Single<List<User>>
}
