package com.itis.android.githubapp.ui.base

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

abstract class BaseFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
}