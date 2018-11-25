package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.Repository
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoService {

    @GET("user/repos")
    fun getUserRepos(@Query("page") page: Int,
                     @Query("type") type: String,
                     @Query("sort") sort: String,
                     @Query("direction") direction: String): Single<ArrayList<Repository>>

    @GET("user/repos")
    fun getUserReposCall(@Query("page") page: Int,
                     @Query("type") type: String,
                     @Query("sort") sort: String,
                     @Query("direction") direction: String): Call<List<Repository>>

    @GET("users/{user}/repos")
    fun getUserPublicRepos(@Path("user") user: String,
                           @Query("page") page: Int? = null,
                           @Query("type") type: String? = null,
                           @Query("sort") sort: String? = null,
                           @Query("direction") direction: String? = null): Single<ArrayList<Repository>>

    @GET("repos/{owner}/{name}")
    fun getRepository(@Path("owner") owner: String,
                      @Path("name") name: String): Single<Repository>
}
