package com.itis.android.githubapp.repository

import com.itis.android.githubapp.api.service.RepoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoRepository(private val repoService: RepoService) {

    suspend fun getUserReposAsync() = withContext(Dispatchers.IO) {
        repoService.getUserReposAsync()
    }
}
