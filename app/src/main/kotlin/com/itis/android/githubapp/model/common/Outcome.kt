package com.itis.android.githubapp.model.common

sealed class Outcome<out T : Any> {

    data class Success<out T : Any>(val data: T) : Outcome<T>()
    data class Error(val error: Throwable) : Outcome<Nothing>()

    companion object {
        fun <T : Any> success(data: T): Outcome<T> = Success(data)
        fun error(error: Throwable): Outcome<Nothing> = Error(error)
    }
}
