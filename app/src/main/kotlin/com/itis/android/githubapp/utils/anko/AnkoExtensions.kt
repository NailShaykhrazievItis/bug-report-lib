package com.itis.android.githubapp.utils.anko

import androidx.fragment.app.Fragment
import com.itis.android.githubapp.utils.constants.STRING_EMPTY
import org.jetbrains.anko.toast

fun Fragment.toast(text: String?) = requireActivity().toast(text ?: STRING_EMPTY)
