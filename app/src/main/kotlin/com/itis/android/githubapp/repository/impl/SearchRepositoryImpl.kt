package com.itis.android.githubapp.repository.impl

import com.itis.android.githubapp.api.service.SearchService
import com.itis.android.githubapp.model.Repository
import com.itis.android.githubapp.model.User
import com.itis.android.githubapp.repository.SearchRepository
import com.itis.android.githubapp.utils.extensions.subscribeSingleOnIoObserveOnUi
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepositoryImpl(private val searchApi: SearchService) : SearchRepository {

    override fun searchRepos(query: String): Single<List<Repository>> =
            searchApi.searchRepos(query)
                    .map { it.items }
                    .subscribeSingleOnIoObserveOnUi()

    override suspend fun searchReposAsync(query: String): List<Repository> = withContext(Dispatchers.IO) {
        searchApi.searchReposAsync(query).await().items
//        async {
//            res.await().items
//        }
    }

    fun searchUsers(query: String): Single<List<User>> =
            searchApi.searchUsers(query)
                    .map { it.items }
                    .subscribeSingleOnIoObserveOnUi()
}
