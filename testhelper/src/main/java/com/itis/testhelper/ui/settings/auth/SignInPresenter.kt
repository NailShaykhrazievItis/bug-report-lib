package com.itis.testhelper.ui.settings.auth

import com.itis.testhelper.repository.AuthRepository
import com.itis.testhelper.repository.PreferenceRepository
import com.itis.testhelper.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInPresenter(
        private var view: SignInView,
        private val preferenceRepository: PreferenceRepository,
        private val authRepository: AuthRepository
) : BasePresenter(view) {

    fun onLoginClick(login: String, password: String) {
        launch {
            invokeSuspend {
                val result = authRepository.getAuthAsync(login, password)
                launch(Dispatchers.IO) {
                    preferenceRepository.saveAuthToken(result.token)
                }
                view.successSignIn(result.token)
            }
        }
    }
}