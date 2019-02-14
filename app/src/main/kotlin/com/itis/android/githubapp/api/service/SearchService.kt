package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.search.SearchRepoResponse
import com.itis.android.githubapp.model.search.SearchUserResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/repositories")
    fun searchReposAsync(@Query("q") query: String): Deferred<SearchRepoResponse>

    @GET("search/users")
    fun searchUsersAsync(@Query("q") query: String): Deferred<SearchUserResponse>
}
