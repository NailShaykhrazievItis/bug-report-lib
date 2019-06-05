package com.itis.testhelper.api.service

import com.itis.testhelper.model.Repository
import com.itis.testhelper.model.User
import com.itis.testhelper.model.request.AuthBody
import com.itis.testhelper.model.request.IssueBody
import com.itis.testhelper.model.response.Authorization
import com.itis.testhelper.model.response.IssueResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface GithubService {

    @POST("authorizations")
    fun authorizeAsync(@Header("Authorization") authorization: String,
                       @Body params: AuthBody): Deferred<Authorization>

    @GET("user")
    fun getUserByTokenAsync(): Deferred<User>

    @GET("user/repos")
    fun getUserReposAsync(@Query("page") page: Int? = null,
                          @Query("type") type: String? = null,
                          @Query("sort") sort: String? = null,
                          @Query("direction") direction: String? = null): Deferred<List<Repository>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("repos/{owner}/{repo}/issuese")
    fun createIssueAsync(@Path("owner") user: String,
                         @Path("repo") repo: String,
                         @Body issue: IssueBody): Deferred<IssueResponse>
}
