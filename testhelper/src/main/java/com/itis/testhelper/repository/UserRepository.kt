package com.itis.testhelper.repository

import com.itis.testhelper.model.Repository
import com.itis.testhelper.model.User
import com.itis.testhelper.model.response.Authorization

interface UserRepository {

    suspend fun getAuthAsync(login: String, password: String): Authorization

    suspend fun getUserByTokenAsync(): User

    suspend fun getUser(): User?

    suspend fun getRepositoriesAsync(): List<Repository>

    fun saveAuthToken(token: String): Boolean

    fun getAuthToken(): String

    fun removeToken(): Boolean

    fun saveUser(user: User)

    fun removeUser()

    fun getCurrentRepoName(): String

    fun saveRepoName(name: String)

    fun removeRepoName()
}
