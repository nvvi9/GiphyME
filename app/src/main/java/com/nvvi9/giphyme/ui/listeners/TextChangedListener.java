package com.nvvi9.giphyme.ui.listeners;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.function.Consumer;

public class TextChangedListener implements TextWatcher {

    private final Consumer<String> listener;

    public TextChangedListener(Consumer<String> listener) {
        this.listener = listener;
    }

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        listener.accept(s.toString());
    }
}
