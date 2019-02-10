package com.itis.android.githubapp.utils.functions

import androidx.appcompat.widget.SearchView
import com.itis.android.githubapp.utils.constants.STRING_EMPTY
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun observableFromSearchView(searchView: SearchView): Observable<String> {
    val subject: PublishSubject<String> = PublishSubject.create()
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onComplete()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            subject.onNext(newText ?: STRING_EMPTY)
            return true
        }
    })
    return subject
}

suspend fun observableFromSearchViewWithChannel(searchView: SearchView, channel: Channel<String>) =
        withContext(Dispatchers.Main) {

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    channel.cancel()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    launch {
                        channel.send(newText ?: STRING_EMPTY)
                    }
                    return true
                }
            })
        }

