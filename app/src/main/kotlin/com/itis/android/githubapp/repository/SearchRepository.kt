package com.itis.android.githubapp.repository

import com.itis.android.githubapp.api.service.SearchService
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.utils.extensions.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single

class SearchRepository(private val searchApi: SearchService) {

    fun searchRepos(query: String): Single<List<Repository>> =
            searchApi.searchRepos(query)
                    .map { it.items }
                    .subscribeSingleOnIoObserveOnUi()

    fun searchUsers(query: String): Single<List<User>> =
            searchApi.searchUsers(query)
                    .map { it.items }
                    .subscribeSingleOnIoObserveOnUi()
}
