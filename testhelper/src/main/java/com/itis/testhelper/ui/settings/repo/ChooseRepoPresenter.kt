package com.itis.testhelper.ui.settings.repo

import com.itis.testhelper.model.Repository
import com.itis.testhelper.repository.UserRepository
import com.itis.testhelper.ui.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChooseRepoPresenter(
        private val view: ChooseRepoView,
        private val userRepository: UserRepository
) : BasePresenter(view) {

    init {
        launch {
            invokeSuspend {
                view.showRepos(userRepository.getRepositoriesAsync())
            }
        }
    }

    fun onRepoClick(repo: Repository) = saveRepoName(repo.name)

    fun saveRepoName(name: String) {
        launch {
            if (name.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    userRepository.saveRepoName(name)
                }
                view.successReturn()
            }
        }
    }

    fun onFabClick() = view.showDialog()
}