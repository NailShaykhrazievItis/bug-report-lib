package com.itis.android.githubapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.android.githubapp.api.service.UserService
import com.itis.android.githubapp.ui.user.UserViewModel

class ViewModelFactory(private val movieService: UserService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            when {
                modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                    UserViewModel() as? T
                            ?: throw IllegalArgumentException("Unknown ViewModel class")
                }
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
}
