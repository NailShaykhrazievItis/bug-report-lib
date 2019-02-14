package com.itis.android.githubapp.repository

import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User

interface SearchRepository {

    suspend fun searchUsers(query: String): List<User>

    suspend fun searchReposAsync(query: String): List<Repository>
}
