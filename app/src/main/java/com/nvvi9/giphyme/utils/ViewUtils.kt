package com.nvvi9.giphyme.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.dynamicanimation.animation.DynamicAnimation.ViewProperty
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nvvi9.giphyme.R

fun EditText.showKeyboard() {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun BottomNavigationView.showBottomNav() {
    if (visibility == View.VISIBLE) return
    val parent = parent as ViewGroup
    if (!isLaidOut) {
        measure(
            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.AT_MOST)
        )
        layout(
            parent.left,
            parent.height - measuredHeight,
            parent.right,
            parent.height
        )
    }
    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    drawable.setBounds(left, parent.height, right, parent.height + height)
    parent.overlay.add(drawable)
    val valueAnimator = ValueAnimator.ofInt(parent.height, top)
    valueAnimator.startDelay = 100
    valueAnimator.duration = 300
    valueAnimator.interpolator =
        AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in)
    valueAnimator.addUpdateListener { animation: ValueAnimator ->
        val newTop = animation.animatedValue as Int
        drawable.setBounds(left, newTop, right, newTop + height)
    }
    valueAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            parent.overlay.remove(drawable)
            visibility = View.VISIBLE
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    valueAnimator.start()
}

fun BottomNavigationView.hideBottomNav() {
    if (visibility == View.GONE) return
    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    val parent = parent as ViewGroup
    drawable.setBounds(left, top, right, bottom)
    parent.overlay.add(drawable)
    visibility = View.GONE
    val valueAnimator = ValueAnimator.ofInt(top, parent.height)
    valueAnimator.startDelay = 100
    valueAnimator.duration = 200
    valueAnimator.interpolator =
        AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_linear_in)
    valueAnimator.addUpdateListener { animation: ValueAnimator ->
        val newTop = animation.animatedValue as Int
        drawable.setBounds(left, newTop, right, newTop + height)
    }
    valueAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            parent.overlay.remove(drawable)
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
    valueAnimator.start()
}

private fun View.drawToBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = Canvas(bitmap)
    c.translate(-scrollX.toFloat(), -scrollY.toFloat())
    draw(c)
    return bitmap
}

fun View.spring(
    property: ViewProperty,
    stiffness: Float?,
    damping: Float?,
    startVelocity: Float?
): SpringAnimation {
    val key = property.getKey()
    val tag = getTag(key)
    val springAnimation: SpringAnimation
    if (tag is SpringAnimation) {
        springAnimation = tag
    } else {
        val springForce = SpringForce()
        springForce.dampingRatio = damping!!
        springForce.stiffness = stiffness!!
        springAnimation = SpringAnimation(this, property)
        if (startVelocity != null) {
            springAnimation.setStartVelocity(startVelocity)
        }
        springAnimation.spring = springForce
        this.tag = springAnimation
    }
    return springAnimation
}

@IdRes
private fun ViewProperty.getKey(): Int = when (this) {
    SpringAnimation.TRANSLATION_X -> R.id.translation_x
    SpringAnimation.TRANSLATION_Y -> R.id.translation_y
    SpringAnimation.TRANSLATION_Z -> R.id.translation_z
    SpringAnimation.SCALE_X -> R.id.scale_x
    SpringAnimation.SCALE_Y -> R.id.scale_y
    SpringAnimation.ROTATION -> R.id.rotation
    SpringAnimation.ROTATION_X -> R.id.rotation_x
    SpringAnimation.ROTATION_Y -> R.id.rotation_y
    SpringAnimation.X -> R.id.x
    SpringAnimation.Y -> R.id.y
    SpringAnimation.Z -> R.id.z
    SpringAnimation.ALPHA -> R.id.alpha
    SpringAnimation.SCROLL_X -> R.id.scroll_x
    SpringAnimation.SCROLL_Y -> R.id.scroll_y
    else -> throw IllegalStateException("Unknown ViewProperty: $this")
}
