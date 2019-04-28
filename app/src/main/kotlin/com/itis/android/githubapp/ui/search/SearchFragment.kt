package com.itis.android.githubapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.itis.android.githubapp.R
import com.itis.android.githubapp.ui.adapters.SearchAdapter
import com.itis.android.githubapp.ui.base.BaseFragment
import com.itis.android.githubapp.ui.repodetails.RepoDetailsActivity
import com.itis.android.githubapp.utils.extensions.provideViewModel
import com.itis.android.githubapp.utils.functions.channelFromSearchView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach

@ExperimentalCoroutinesApi
class SearchFragment : BaseFragment(), CoroutineScope by MainScope() {

    override val viewModel: SearchViewModel by provideViewModel()

    private var searchAdapter: SearchAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineContext.cancelChildren()
    }

    override fun initObservers() {
        provideLoadingObservers(pb_search_progress)
        provideErrorObservers(container_search)

        searchWithChannel()

        viewModel.results.observe(viewLifecycleOwner, Observer {
            tv_no_results.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            tv_title_result.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            searchAdapter?.submitList(it)
        })
        initNavigateObservers()
    }


    private fun searchWithChannel() {
        val broadcast = ConflatedBroadcastChannel<String>()
        launch {
            var lastTimeout: Job? = null
            broadcast.consumeEach {
                lastTimeout?.cancel()
                lastTimeout = launch {
                    delay(2000)
                    viewModel.search(it)
                    Log.e("CONSUME", "Hi: $it")
                }
            }
            lastTimeout?.join()
        }
        channelFromSearchView(sv_main, broadcast)
    }

    private fun initRecycler() {
        searchAdapter = SearchAdapter(Glide.with(this)) { repo ->
            viewModel.onItemClick(repo.name)
        }
        activity?.let {
            val divider = DividerItemDecoration(it, DividerItemDecoration.VERTICAL)
            rv_search.addItemDecoration(divider)
        }
        rv_search.adapter = searchAdapter
    }

    private fun initNavigateObservers() {
        viewModel.navigateToReposDetails.observe(viewLifecycleOwner, Observer {
            activity?.run {
                val intent = Intent(this, RepoDetailsActivity::class.java)
                intent.putExtra(RepoDetailsActivity.EXTRA_REPO_NAME, it)
                startActivity(intent)
            }
        })
    }
}
