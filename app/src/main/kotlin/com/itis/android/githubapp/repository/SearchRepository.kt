package com.itis.android.githubapp.repository

import com.itis.android.githubapp.model.Repository
import io.reactivex.Single

interface SearchRepository {

    fun searchRepos(query: String): Single<List<Repository>>

    suspend fun searchReposAsync(query: String): List<Repository>
}