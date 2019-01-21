package com.itis.android.githubapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.model.common.Outcome
import com.itis.android.githubapp.repository.RepoRepository
import com.itis.android.githubapp.repository.UserRepository
import io.reactivex.rxkotlin.subscribeBy

class ProfileViewModel(
        private val userRepository: UserRepository,
        private val repoRepository: RepoRepository
) : ViewModel() {

    private lateinit var _user: MutableLiveData<Outcome<User>>
    private lateinit var _repos: MutableLiveData<Outcome<List<Repository>>>
    private lateinit var _loading: MutableLiveData<Boolean>

    fun getUser(): MutableLiveData<Outcome<User>> {
        if (!::_user.isInitialized) {
            _user = MutableLiveData()
            fetchUserData()
        }
        return _user
    }

    fun getRepos(): MutableLiveData<Outcome<List<Repository>>> {
        if (!::_repos.isInitialized) {
            _repos = MutableLiveData()
            fetchRepositories()
        }
        return _repos
    }

    fun isLoading(): MutableLiveData<Boolean> {
        if (!::_loading.isInitialized) {
            _loading = MutableLiveData()
        }
        return _loading
    }

    private fun fetchUserData() {
        userRepository.getUserByToken()
                .doOnSubscribe { _loading.value = true }
                .doAfterTerminate { _loading.value = false }
                .subscribeBy(onSuccess = {
                    _user.value = Outcome.success(it)
                }, onError = {
                    _user.value = Outcome.error(it)
                })
    }

    private fun fetchRepositories() {
        repoRepository.getUserRepos()
                .doOnSubscribe { _loading.value = true }
                .doAfterTerminate { _loading.value = false }
                .subscribeBy(onSuccess = {
                    _repos.value = Outcome.success(it)
                }, onError = {
                    _repos.value = Outcome.error(it)
                })
    }
}
