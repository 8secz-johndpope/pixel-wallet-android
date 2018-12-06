package com.piction.pixelwallet.util.extension


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * [LiveData.observe]의 Wrapper Extension
 *
 * LiveData의 new value가 null이 아닐 때만 [onChanged] block을 호출함
 */
inline fun <T> LifecycleOwner.observeLiveData(data: LiveData<T>, crossinline onChanged: (T) -> Unit) {
    data.observe(this, Observer { it ->
        it?.let { onChanged(it) }
    })
}
