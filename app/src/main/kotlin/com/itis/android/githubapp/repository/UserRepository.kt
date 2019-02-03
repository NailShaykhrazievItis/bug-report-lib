package com.itis.android.githubapp.repository

import com.itis.android.githubapp.api.service.UserService
import com.itis.android.githubapp.model.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userService: UserService) {

    suspend fun getUserByTokenAsync(): Deferred<User> = withContext(Dispatchers.IO) {
        userService.getUserByTokenAsync()
    }
}
