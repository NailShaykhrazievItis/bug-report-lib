package com.itis.android.githubapp.repository.impl

import com.itis.android.githubapp.api.service.UserService
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.repository.UserRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun getUserByTokenAsync(): Deferred<User> = withContext(Dispatchers.IO) {
        userService.getUserByTokenAsync()
    }
}
