package com.itis.android.githubapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.repository.PreferenceRepository
import com.itis.android.githubapp.repository.RepoRepository
import com.itis.android.githubapp.repository.UserRepository
import com.itis.android.githubapp.ui.base.BaseViewModel
import com.itis.android.githubapp.utils.vm.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(
        private val userRepository: UserRepository,
        private val repoRepository: RepoRepository,
        private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mUser: MutableLiveData<User>
    private lateinit var mRepos: MutableLiveData<List<Repository>>
    private val mSignOut = SingleLiveEvent<Any>()

    val navigateToAuth: LiveData<Any?>
        get() = mSignOut

    fun getUser(): MutableLiveData<User> {
        if (!::mUser.isInitialized) {
            mUser = MutableLiveData()
            fetchUserData()
        }
        return mUser
    }

    fun getRepos(): MutableLiveData<List<Repository>> {
        if (!::mRepos.isInitialized) {
            mRepos = MutableLiveData()
            fetchRepositories()
        }
        return mRepos
    }

    fun signOutClick() {
        preferenceRepository.removeToken()
        mSignOut.call()
    }

    private fun fetchUserData() = launch {
        invokeSuspend {
            val result = userRepository.getUserByTokenAsync()
            mUser.postValue(result.await())
        }
    }

    private fun fetchRepositories() = launch {
        invokeSuspend {
            val result = repoRepository.getUserReposAsync()
            mRepos.postValue(result.await())
        }
    }
}
