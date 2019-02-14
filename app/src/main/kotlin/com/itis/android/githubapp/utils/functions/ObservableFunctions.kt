package com.itis.android.githubapp.utils.functions

import androidx.appcompat.widget.SearchView
import com.itis.android.githubapp.utils.constants.STRING_EMPTY
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

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

fun channelFromSearchView(searchView: SearchView, broadcast: ConflatedBroadcastChannel<String>) =
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                broadcast.offer(newText ?: STRING_EMPTY)
                return true
            }
        })
