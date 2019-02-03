package com.itis.android.githubapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.itis.android.githubapp.utils.constants.ERROR_UNDEFAUIND
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

abstract class BaseFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    protected abstract val viewModel: BaseViewModel

    protected abstract fun initObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    protected fun provideLoadingObservers(view: View) {
        viewModel.isLoading().observe(viewLifecycleOwner, Observer {
            view.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    protected fun provideErrorObservers(view: View) {
        viewModel.error().observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, it.message ?: ERROR_UNDEFAUIND, Snackbar.LENGTH_SHORT)
        })
    }
}
