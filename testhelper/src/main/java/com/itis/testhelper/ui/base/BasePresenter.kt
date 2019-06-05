package com.itis.testhelper.ui.base

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren

open class BasePresenter(
        private var view: BaseView?
) : CoroutineScope by MainScope() {

    protected val handler = CoroutineExceptionHandler { _, exception ->
        view?.showError(exception)
    }

    fun onDestroy() {
        coroutineContext.cancelChildren()
        view = null
    }

    suspend fun <T> invokeSuspend(suspendBlock: suspend () -> T): T? = try {
        view?.showLoading()
        suspendBlock()
    } catch (throwable: Throwable) {
        view?.showError(throwable)
        null
    } finally {
        view?.hideLoading()
    }
}
