package com.itis.android.githubapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.repository.SearchRepository
import com.itis.android.githubapp.ui.base.BaseViewModel
import com.itis.android.githubapp.utils.vm.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mUsers: MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
    private val mRepos: MutableLiveData<List<Repository>> by lazy { MutableLiveData<List<Repository>>() }

    private val mQuery: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val mItemClick = SingleLiveEvent<String>()

    val navigateToReposDetails: SingleLiveEvent<String>
        get() = mItemClick

    val results: LiveData<List<Repository>> = Transformations
            .switchMap(mQuery) { search ->
                findRepos(search)
            }

    fun onItemClick(repoName: String) {
        mItemClick.value = repoName
    }

    fun search(input: String) {
        if (input == mQuery.value) {
            return
        }
        mQuery.value = input
    }

    private fun findRepos(query: String): MutableLiveData<List<Repository>> {
        launch {
            invokeSuspend {
                val res = searchRepository.searchReposAsync(query)
                mRepos.postValue(res)
            }

        }
        return mRepos
    }
}
