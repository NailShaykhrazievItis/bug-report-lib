package com.itis.testhelper.ui.base

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(throwable: Throwable)
}