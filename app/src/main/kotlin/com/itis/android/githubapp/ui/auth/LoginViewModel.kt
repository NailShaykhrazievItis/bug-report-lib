package com.itis.android.githubapp.ui.auth

import androidx.lifecycle.MutableLiveData
import com.itis.android.githubapp.model.common.Outcome
import com.itis.android.githubapp.repository.AuthRepository
import com.itis.android.githubapp.repository.PreferenceRepository
import com.itis.android.githubapp.ui.base.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
        private val authRepository: AuthRepository,
        private val preferenceRepository: PreferenceRepository
) : BaseViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var _result: MutableLiveData<String>

    fun result(): MutableLiveData<String> {
        if (!::_result.isInitialized) {
            _result = MutableLiveData()
        }
        return _result
    }

    fun auth(login: String, password: String): MutableLiveData<String> {
        _loading.postValue(true)
        launch {
            val result = withContext(Dispatchers.IO) {
                authRepository.getAuth(login, password)
            }
            when (result) {
                is Outcome.Success -> {
                    launch(Dispatchers.IO) {
                        preferenceRepository.saveAuthToken(result.data.token)
                    }
                    _result.postValue(result.data.token)
                }
                is Outcome.Error -> {
                    _error.postValue(result.error)
                }
            }
        }
        _loading.postValue(false)
        return _result
    }
}
