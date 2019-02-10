package com.itis.android.githubapp.repository

import com.itis.android.githubapp.model.User
import kotlinx.coroutines.Deferred

interface UserRepository {

    suspend fun getUserByTokenAsync(): Deferred<User>
}
