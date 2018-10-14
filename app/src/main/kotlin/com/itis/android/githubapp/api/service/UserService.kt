package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.User
import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Nail Shaykhraziev on 08.10.2018.
 */
interface UserService {

    @POST("/authorizations")
    suspend fun authorize(@Query("auth_token") authToken: String): Deferred<User>

    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): Deferred<User>

    @GET("users/{user}/orgs")
    suspend fun getUserOrgs(@Path("user") user: String): Deferred<List<User>>
}
