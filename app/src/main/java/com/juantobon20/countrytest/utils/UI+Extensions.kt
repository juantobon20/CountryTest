package com.juantobon20.countrytest.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.juantobon20.countrytest.R
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <State> LifecycleOwner.handleState(
    state: Lifecycle.State = Lifecycle.State.RESUMED,
    stateFlow: StateFlow<State>,
    block: (state: State) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            stateFlow.collect(block)
        }
    }
}

fun ImageView.loadImageFromUrl(url: String?) {
    if (url.isNullOrEmpty()) {
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.not_found))
        return
    }

    Glide.with(context)
        .load(url)
        .error(R.drawable.not_found)
        .thumbnail(Glide.with(context).load(R.raw.loader))
        .into(this)
}