package com.itis.android.githubapp.repository.impl

import com.itis.android.githubapp.api.service.RepoService
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.repository.RepoRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoRepositoryImpl(private val repoService: RepoService) : RepoRepository {

    override suspend fun getUserReposAsync(): Deferred<List<Repository>> = withContext(Dispatchers.IO) {
        repoService.getUserReposAsync()
    }
}
