package com.itis.android.githubapp.ui.auth

import androidx.lifecycle.MutableLiveData
import com.itis.android.githubapp.repository.AuthRepository
import com.itis.android.githubapp.repository.PreferenceRepository
import com.itis.android.githubapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
        private val authRepository: AuthRepository,
        private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mResult: MutableLiveData<String>

    fun result(): MutableLiveData<String> {
        if (!::mResult.isInitialized) {
            mResult = MutableLiveData()
        }
        return mResult
    }

    fun auth(login: String, password: String): Job = this.launch {
        invokeSuspend {
            val result = authRepository.getAuthAsync(login, password)
            result.await().token.let {
                launch(Dispatchers.IO) {
                    preferenceRepository.saveAuthToken(it)
                }
                mResult.postValue(it)
            }
        }
    }
}
