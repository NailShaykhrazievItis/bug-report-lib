package com.itis.android.githubapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.itis.android.githubapp.R
import com.itis.android.githubapp.model.common.Outcome
import com.itis.android.githubapp.utils.extensions.provideViewModel
import com.itis.android.githubapp.utils.functions.observableFromSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private var searchDisposable: Disposable? = null
    private val viewModel: SearchViewModel by provideViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading().observe(this, Observer {
            pb_search_progress.visibility = if (it) View.VISIBLE else View.GONE
        })
        searchDisposable = observableFromSearchView(sv_main)
                .debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.search(it)
                }
        viewModel.results.observe(this, Observer {
            when (it) {
                is Outcome.Success -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchDisposable?.dispose()
    }
}
