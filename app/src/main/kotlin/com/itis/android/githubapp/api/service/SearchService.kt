package com.itis.android.githubapp.api.service

import io.reactivex.Completable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nail Shaykhraziev on 08.10.2018.
 */
interface SearchService {
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): Completable

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String, @Query("page") page: Int): Completable
}
