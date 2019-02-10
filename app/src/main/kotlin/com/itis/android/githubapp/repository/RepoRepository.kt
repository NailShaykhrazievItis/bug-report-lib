package com.itis.android.githubapp.repository

import com.itis.android.githubapp.model.Repository
import kotlinx.coroutines.Deferred

interface RepoRepository {

    suspend fun getUserReposAsync(): Deferred<List<Repository>>
}
