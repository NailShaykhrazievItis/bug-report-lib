package com.itis.testhelper.repository

import com.itis.testhelper.model.Authorization

interface AuthRepository {

    suspend fun getAuthAsync(login: String, password: String): Authorization
}
