package com.nvvi9.giphyme.utils

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
import androidx.dynamicanimation.animation.SpringAnimation

class MultiSpringAddListener(onEnd: (Boolean) -> Unit, vararg springs: SpringAnimation) {
    private val listeners: List<OnAnimationEndListener>
    private var wasCancelled = false

    init {
        listeners = mutableListOf()
        for (springAnimation in springs) {
            val onAnimationEndListener: OnAnimationEndListener = object : OnAnimationEndListener {
                override fun onAnimationEnd(
                    animation: DynamicAnimation<*>?,
                    canceled: Boolean,
                    value: Float,
                    velocity: Float
                ) {
                    animation?.removeEndListener(this)
                    wasCancelled = wasCancelled || canceled
                    listeners.remove(this)
                    if (listeners.isEmpty()) {
                        onEnd(wasCancelled)
                    }
                }
            }
            springAnimation.addEndListener(onAnimationEndListener)
            listeners.add(onAnimationEndListener)
        }
    }
}