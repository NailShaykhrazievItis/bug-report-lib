package com.itis.android.githubapp.model.common

sealed class Outcome<T> {

    data class Success<T>(var data: T) : Outcome<T>()
    data class Error<T>(val error: Throwable) : Outcome<T>()

    companion object {
        fun <T> success(data: T): Outcome<T> = Success(data)
        fun <T> error(error: Throwable): Outcome<T> = Error(error)
    }
}
