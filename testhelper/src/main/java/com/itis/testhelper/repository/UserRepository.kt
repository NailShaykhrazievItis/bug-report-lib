package com.itis.testhelper.repository

import com.itis.testhelper.model.Authorization
import com.itis.testhelper.model.User

interface UserRepository {

    suspend fun getAuthAsync(login: String, password: String): Authorization

    suspend fun getUserByTokenAsync(): User

    suspend fun getUser(): User?

    fun saveAuthToken(token: String): Boolean

    fun getAuthToken(): String

    fun removeToken(): Boolean

    fun saveUserName(name: String)

    fun getUserName(): String

    fun saveUser(user: User)

    fun removeUser()

    fun getCurrentRepoName(): String

    fun saveRepoName(name: String)
}
