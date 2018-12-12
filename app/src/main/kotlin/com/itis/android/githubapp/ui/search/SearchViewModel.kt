package com.itis.android.githubapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.model.common.Outcome
import com.itis.android.githubapp.repository.SearchRepository
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private lateinit var _users: MutableLiveData<List<User>>
    private val _repos: MutableLiveData<Outcome<List<Repository>>> by lazy { MutableLiveData<Outcome<List<Repository>>>() }
    private lateinit var _loading: MutableLiveData<Boolean>
    private val _query: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun search(input: String) {
        if (input == _query.value) {
            return
        }
        _query.value = input
    }

    val results: LiveData<Outcome<List<Repository>>> = Transformations
            .switchMap(_query) { search ->
                findRepos(search)
//                if (search.isNullOrBlank()) {
////                    AbsentLiveData.create()
//                } else {
//                    findRepos(search)
//                }
            }

    fun isLoading(): MutableLiveData<Boolean> {
        if (!::_loading.isInitialized) {
            _loading = MutableLiveData()
        }
        return _loading
    }

    fun findUsers(): MutableLiveData<List<User>> {
        return _users
    }

    private fun findRepos(query: String): MutableLiveData<Outcome<List<Repository>>> {
        searchRepository.searchRepos(query)
                .doOnSubscribe { _loading.value = true }
                .doAfterTerminate { _loading.value = false }
                .subscribeBy(onSuccess = {
                    _repos.value = Outcome.success(it)
                }, onError = {
                    _repos.value = Outcome.failure(it)
                })
        return _repos
    }
}
