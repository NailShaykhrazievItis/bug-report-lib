package com.itis.android.githubapp

import com.google.gson.JsonObject
import com.itis.android.githubapp.api.service.AuthService
import com.itis.android.githubapp.model.Authorization
import com.itis.android.githubapp.repository.AuthRepository
import com.itis.android.githubapp.repository.PreferenceRepository
import com.itis.android.githubapp.repository.impl.AuthRepositoryImpl
import com.itis.android.githubapp.ui.auth.LoginViewModel
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import kotlin.test.assertEquals

class LoginTest {

    private val mockAuthRepository: AuthRepository = mockk()
    private val mockPrefRepository: PreferenceRepository = mockk()

    private val loginViewModel = LoginViewModel(mockAuthRepository, mockPrefRepository)

    private val mockAuthService: AuthService = mockk()
    private val authRepository = spyk(AuthRepositoryImpl(mockAuthService))

//    @Test
//    fun whenLoginExpectedSuccess() = runBlocking(Dispatchers.Main) {
//        val expectedLogin = "Login"
//        val expectedPass = "Pass"
//        val expectedAuth = Authorization("token")
//
//        coEvery {
//            mockAuthRepository.getAuthAsync(expectedLogin, expectedPass).await()
//        } returns expectedAuth
//
//        runBlocking(Dispatchers.Main) {
//            loginViewModel.auth(expectedLogin, expectedPass)
//
////            coVerify { loginViewModel.auth(expectedLogin, expectedPass) }
////            coVerify { mockPrefRepository.saveAuthToken("token") }
//        }
//    }

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
