package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.Repository
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoService {

    @GET("user/repos")
    fun getUserReposAsync(@Query("page") page: Int? = null,
                          @Query("type") type: String? = null,
                          @Query("sort") sort: String? = null,
                          @Query("direction") direction: String? = null): Deferred<List<Repository>>

    @GET("users/{user}/repos")
    fun getUserPublicReposAsync(@Path("user") user: String,
                                @Query("page") page: Int? = null,
                                @Query("type") type: String? = null,
                                @Query("sort") sort: String? = null,
                                @Query("direction") direction: String? = null): Deferred<List<Repository>>

    @GET("repos/{owner}/{name}")
    fun getRepositoryAsync(@Path("owner") owner: String,
                           @Path("name") name: String): Deferred<Repository>
}
