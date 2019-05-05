package com.itis.testhelper.api.service

import com.itis.testhelper.model.request.IssueBody
import com.itis.testhelper.model.response.IssueResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface IssueService {

    @POST("repos/{owner}/{repo}/issues")
    fun createIssueAsync(@Path("owner") user: String,
                         @Path("repo") repo: String,
                         @Body issue: IssueBody): Deferred<IssueResponse>
}
