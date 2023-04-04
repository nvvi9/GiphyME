package com.nvvi9.giphyme.ui.listeners

import android.text.Editable
import android.text.TextWatcher

class TextChangedListener(private val listener: (String) -> Unit) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        listener(s.toString())
    }
}