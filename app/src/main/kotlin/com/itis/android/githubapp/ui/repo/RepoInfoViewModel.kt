package com.itis.android.githubapp.ui.repo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class RepoInfoViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = GlobalScope.coroutineContext

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}
