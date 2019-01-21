package com.itis.android.githubapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    protected lateinit var _loading: MutableLiveData<Boolean>
    protected lateinit var _error: MutableLiveData<Throwable>

    fun isLoading(): MutableLiveData<Boolean> {
        if (!::_loading.isInitialized) {
            _loading = MutableLiveData()
        }
        return _loading
    }

    fun error(): MutableLiveData<Throwable> {
        if (!::_error.isInitialized) {
            _error = MutableLiveData()
        }
        return _error
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}