package com.itis.android.githubapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private lateinit var mLoading: MutableLiveData<Boolean>
    private lateinit var mError: MutableLiveData<Throwable>

    fun isLoading(): MutableLiveData<Boolean> {
        if (!::mLoading.isInitialized) {
            mLoading = MutableLiveData()
        }
        return mLoading
    }

    fun error(): MutableLiveData<Throwable> {
        if (!::mError.isInitialized) {
            mError = MutableLiveData()
        }
        return mError
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T> invokeSuspend(suspendBlock: suspend () -> T): T? = try {
        mLoading.postValue(true)
        suspendBlock()
    } catch (throwable: Throwable) {
        mError.postValue(throwable)
        null
    } finally {
        mLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}
