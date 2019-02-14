package com.itis.android.githubapp.repository.impl

import com.itis.android.githubapp.api.service.SearchService
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepositoryImpl(private val searchApi: SearchService) : SearchRepository {

    override suspend fun searchReposAsync(query: String): List<Repository> = withContext(Dispatchers.IO) {
        searchApi.searchReposAsync(query).await().items
    }

    override suspend fun searchUsers(query: String): List<User> = withContext(Dispatchers.IO) {
        searchApi.searchUsersAsync(query).await().items
    }
}
