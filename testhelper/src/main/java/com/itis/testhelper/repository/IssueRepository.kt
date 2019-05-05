package com.itis.testhelper.repository

import com.itis.testhelper.model.response.IssueResponse

interface IssueRepository {

    suspend fun createIssue(user: String, repo: String, title: String, body: String): IssueResponse
}