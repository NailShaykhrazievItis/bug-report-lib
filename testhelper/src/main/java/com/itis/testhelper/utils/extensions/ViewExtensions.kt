package com.itis.testhelper.utils.extensions

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import com.itis.testhelper.utils.STRING_EMPTY

@SuppressLint("RestrictedApi")
fun View.getTextFromView(): String = when (this) {
    is TextView -> this.text.toString()
    is MenuView.ItemView -> this.itemData.title.toString()
    else -> STRING_EMPTY
}

fun View.getRectByView(): Rect {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Rect(location[0], location[1],
            location[0] + this.width,
            location[1] + this.height)
}
