package com.itis.testhelper.api.service

import com.google.gson.JsonObject
import com.itis.testhelper.model.Repository
import com.itis.testhelper.model.User
import com.itis.testhelper.model.request.IssueBody
import com.itis.testhelper.model.response.Authorization
import com.itis.testhelper.model.response.IssueResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface GithubService {

    @POST("authorizations")
    fun authorizeAsync(@Header("Authorization") authorization: String,
                       @Body params: JsonObject): Deferred<Authorization>

    @GET("user")
    fun getUserByTokenAsync(): Deferred<User>

    @GET("user/repos")
    fun getUserReposAsync(@Query("page") page: Int? = null,
                          @Query("type") type: String? = null,
                          @Query("sort") sort: String? = null,
                          @Query("direction") direction: String? = null): Deferred<List<Repository>>

    @POST("repos/{owner}/{repo}/issues")
    fun createIssueAsync(@Path("owner") user: String,
                         @Path("repo") repo: String,
                         @Body issue: IssueBody): Deferred<IssueResponse>
}
