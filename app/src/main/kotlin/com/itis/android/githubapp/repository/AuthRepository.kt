package com.itis.android.githubapp.repository

import com.itis.android.githubapp.model.Authorization
import kotlinx.coroutines.Deferred

interface AuthRepository {

    suspend fun getAuthAsync(login: String, password: String): Deferred<Authorization>
}
