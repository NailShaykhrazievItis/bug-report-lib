package com.itis.android.githubapp.api.service

import com.itis.android.githubapp.model.search.SearchRepoResponse
import com.itis.android.githubapp.model.search.SearchUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): Single<SearchRepoResponse>

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Single<SearchUserResponse>
}
