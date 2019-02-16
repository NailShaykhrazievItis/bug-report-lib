package com.itis.android.githubapp

import com.google.gson.JsonObject
import com.itis.android.githubapp.api.service.AuthService
import com.itis.android.githubapp.model.Authorization
import com.itis.android.githubapp.repository.impl.AuthRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import kotlin.test.assertEquals

class LoginTest {

    private val mockAuthService: AuthService = mockk()
    private val authRepository = spyk(AuthRepositoryImpl(mockAuthService))

    @Test
    @DisplayName("Но это был я...")
    fun whenLoginExpectedSuccess() {
        val expectedLogin = "Login"
        val expectedPass = "Pass"
        val expectedAuthorizationString = "Auth"
        val expectedAuth = Authorization("token")

        coEvery {
            mockAuthService.authorizeAsync(expectedAuthorizationString, any()).await()
        } returns expectedAuth
        every {
            authRepository.createAuthorizationString(expectedLogin, expectedPass)
        } returns expectedAuthorizationString
        every {
            authRepository.createAuthorizationParam()
        } returns JsonObject()

        runBlocking {
            val result = authRepository.getAuthAsync(expectedLogin, expectedPass)

            coVerify { authRepository.getAuthAsync(expectedLogin, expectedPass) }

            assertEquals(expectedAuth, result.await())
        }
    }
}
