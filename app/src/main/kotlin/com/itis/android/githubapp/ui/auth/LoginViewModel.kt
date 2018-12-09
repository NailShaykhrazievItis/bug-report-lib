package com.itis.android.githubapp.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itis.android.githubapp.api.service.AuthService
import com.itis.android.githubapp.model.common.Outcome
import com.itis.android.githubapp.repository.AuthRepository
import com.itis.android.githubapp.repository.PreferenceRepository
import com.itis.android.githubapp.repository.UserRepository
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel(
        private val authService: AuthRepository,
        private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val result = MutableLiveData<Outcome<String>>()

    fun auth(login: String, password: String): MutableLiveData<Outcome<String>> {
        authService.auth(login, password)
                .map {
                    preferenceRepository.saveAuthToken(it.token)
                    it
                }
                .doOnSubscribe { result.value = Outcome.loading(true) }
                .doAfterTerminate { result.value = Outcome.loading(false) }
                .subscribeBy(onSuccess = {
                    result.value = Outcome.success(it.token)
                }, onError = {
                    result.value = Outcome.failure(it)
                })
        return result
    }
}
