package com.itis.android.githubapp.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itis.android.githubapp.repository.PreferenceRepository

class SplashScreenViewModel(private val preferenceRepository: PreferenceRepository) : ViewModel() {

    fun checkUserToken(): MutableLiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        result.value = preferenceRepository.getAuthToken().isNotEmpty()
        return result
    }
}
