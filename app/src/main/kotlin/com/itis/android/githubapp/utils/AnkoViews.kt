@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.itis.android.githubapp.utils

import android.view.ViewManager
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.textInput(): TextInputLayout = textInput() {}
inline fun ViewManager.textInput(init: (@AnkoViewDslMarker TextInputLayout).() -> Unit): TextInputLayout {
    return ankoView({ TextInputLayout(it) }, theme = 0, init = init)
}

inline fun ViewManager.buttonX(): AppCompatButton = buttonX {}
inline fun ViewManager.buttonX(theme: Int, init: (@AnkoViewDslMarker AppCompatButton).() -> Unit): AppCompatButton {
    return ankoView({ AppCompatButton(it) }, theme = theme, init = init)
}

inline fun ViewManager.buttonX(init: (@AnkoViewDslMarker AppCompatButton).() -> Unit): AppCompatButton {
    return ankoView({ AppCompatButton(it) }, theme = 0, init = init)
}
