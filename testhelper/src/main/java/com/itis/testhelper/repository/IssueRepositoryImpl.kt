package com.itis.testhelper.repository

import com.itis.testhelper.api.ApiFactory
import com.itis.testhelper.model.request.IssueBody
import com.itis.testhelper.model.response.IssueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IssueRepositoryImpl : IssueRepository {

    override suspend fun createIssue(user: String,
                                     repo: String,
                                     title: String,
                                     body: String): IssueResponse = withContext(Dispatchers.IO) {
        val issueBody = IssueBody(title, body)
        ApiFactory.issueService.createIssueAsync(user, repo, issueBody).await()
    }
}