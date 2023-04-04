package com.nvvi9.giphyme.utils

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class SpringAddItemAnimator : DefaultItemAnimator() {
    private val pendingAdds = mutableListOf<RecyclerView.ViewHolder>()

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        holder.itemView.translationY = holder.itemView.bottom / 3f
        pendingAdds.add(holder)
        return true
    }

    override fun runPendingAnimations() {
        super.runPendingAnimations()
        if (pendingAdds.isNotEmpty()) {
            var i = pendingAdds.size - 1
            while (i >= 0) {
                val holder = pendingAdds[i]
                val tySpring = holder.itemView.spring(
                    SpringAnimation.TRANSLATION_Y,
                    350f,
                    0.6f,
                    null
                )
                val aSpring = holder.itemView.spring(
                    SpringAnimation.ALPHA,
                    100f,
                    SpringForce.DAMPING_RATIO_NO_BOUNCY,
                    null
                )
                MultiSpringAddListener({
                    if (!it) {
                        dispatchAddFinished(holder)
                        dispatchFinishedWhenDone()
                    } else {
                        clearAnimatedValues(holder.itemView)
                    }
                }, tySpring, aSpring)
                dispatchAddStarting(holder)
                aSpring.animateToFinalPosition(1f)
                tySpring.animateToFinalPosition(0f)
                pendingAdds.removeAt(i)
                --i
            }
        }
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        item.itemView.spring(SpringAnimation.TRANSLATION_Y, 200f, 0.3f, null).cancel()
        item.itemView.spring(SpringAnimation.ALPHA, 200f, 0.3f, null).cancel()
        if (pendingAdds.remove(item)) {
            dispatchAddFinished(item)
            clearAnimatedValues(item.itemView)
        }
        super.endAnimation(item)
    }

    override fun isRunning(): Boolean {
        return pendingAdds.isNotEmpty() || super.isRunning()
    }

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }

    private fun clearAnimatedValues(view: View) {
        view.alpha = 1f
        view.translationY = 0f
    }
}