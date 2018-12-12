package com.itis.android.githubapp.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itis.android.githubapp.model.common.Outcome
import com.itis.android.githubapp.repository.AuthRepository
import com.itis.android.githubapp.repository.PreferenceRepository
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel(
        private val authRepository: AuthRepository,
        private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _result by lazy { MutableLiveData<Outcome<String>>() }

    fun auth(login: String, password: String): MutableLiveData<Outcome<String>> {
        authRepository.auth(login, password)
                .map {
                    preferenceRepository.saveAuthToken(it.token)
                    it
                }
                .doOnSubscribe { _result.value = Outcome.loading(true) }
                .doAfterTerminate { _result.value = Outcome.loading(false) }
                .subscribeBy(onSuccess = {
                    _result.value = Outcome.success(it.token)
                }, onError = {
                    _result.value = Outcome.failure(it)
                })
        return _result
    }
}
