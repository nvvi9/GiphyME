package com.nvvi9.giphyme.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nvvi9.giphyme.R;

public class ViewUtils {

    public static void showKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showBottomNav(BottomNavigationView view) {
        if (view.getVisibility() == View.VISIBLE) return;

        ViewGroup parent = (ViewGroup) view.getParent();

        if (!view.isLaidOut()) {
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.AT_MOST)
            );
            view.layout(parent.getLeft(), parent.getHeight() - view.getMeasuredHeight(), parent.getRight(), parent.getHeight());
        }

        BitmapDrawable drawable = new BitmapDrawable(view.getContext().getResources(), drawToBitmap(view));

        drawable.setBounds(view.getLeft(), parent.getHeight(), view.getRight(), parent.getHeight() + view.getHeight());
        parent.getOverlay().add(drawable);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(parent.getHeight(), view.getTop());
        valueAnimator.setStartDelay(100);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(AnimationUtils.loadInterpolator(view.getContext(), android.R.interpolator.linear_out_slow_in));
        valueAnimator.addUpdateListener(animation -> {
            Integer newTop = (Integer) animation.getAnimatedValue();
            drawable.setBounds(view.getLeft(), newTop, view.getRight(), newTop + view.getHeight());
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                parent.getOverlay().remove(drawable);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        valueAnimator.start();
    }

    public static void hideBottomNav(BottomNavigationView view) {
        if (view.getVisibility() == View.GONE) return;

        BitmapDrawable drawable = new BitmapDrawable(view.getContext().getResources(), drawToBitmap(view));

        ViewGroup parent = (ViewGroup) view.getParent();

        drawable.setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());

        parent.getOverlay().add(drawable);

        view.setVisibility(View.GONE);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(view.getTop(), parent.getHeight());

        valueAnimator.setStartDelay(100);
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(AnimationUtils.loadInterpolator(view.getContext(), android.R.interpolator.fast_out_linear_in));

        valueAnimator.addUpdateListener(animation -> {
            Integer newTop = (Integer) animation.getAnimatedValue();
            drawable.setBounds(view.getLeft(), newTop, view.getRight(), newTop + view.getHeight());
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                parent.getOverlay().remove(drawable);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        valueAnimator.start();
    }

    private static Bitmap drawToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);

        return bitmap;
    }

    public static SpringAnimation spring(View view, DynamicAnimation.ViewProperty property, Float stiffness, Float damping, @Nullable Float startVelocity) {
        final int key = getKey(property);

        Object tag = view.getTag(key);
        SpringAnimation springAnimation;

        if (tag instanceof SpringAnimation) {
            springAnimation = (SpringAnimation) tag;
        } else {
            SpringForce springForce = new SpringForce();
            springForce.setDampingRatio(damping);
            springForce.setStiffness(stiffness);

            springAnimation = new SpringAnimation(view, property);
            if (startVelocity != null) {
                springAnimation.setStartVelocity(startVelocity);
            }

            springAnimation.setSpring(springForce);

            view.setTag(springAnimation);
        }

        return springAnimation;
    }

    @IdRes
    private static int getKey(DynamicAnimation.ViewProperty property) {
        int key;

        if (property.equals(SpringAnimation.TRANSLATION_X)) {
            key = R.id.translation_x;
        } else if (property.equals(SpringAnimation.TRANSLATION_Y)) {
            key = R.id.translation_y;
        } else if (property.equals(SpringAnimation.TRANSLATION_Z)) {
            key = R.id.translation_z;
        } else if (property.equals(SpringAnimation.SCALE_X)) {
            key = R.id.scale_x;
        } else if (property.equals(SpringAnimation.SCALE_Y)) {
            key = R.id.scale_y;
        } else if (property.equals(SpringAnimation.ROTATION)) {
            key = R.id.rotation;
        } else if (property.equals(SpringAnimation.ROTATION_X)) {
            key = R.id.rotation_x;
        } else if (property.equals(SpringAnimation.ROTATION_Y)) {
            key = R.id.rotation_y;
        } else if (property.equals(SpringAnimation.X)) {
            key = R.id.x;
        } else if (property.equals(SpringAnimation.Y)) {
            key = R.id.y;
        } else if (property.equals(SpringAnimation.Z)) {
            key = R.id.z;
        } else if (property.equals(SpringAnimation.ALPHA)) {
            key = R.id.alpha;
        } else if (property.equals(SpringAnimation.SCROLL_X)) {
            key = R.id.scroll_x;
        } else if (property.equals(SpringAnimation.SCROLL_Y)) {
            key = R.id.scroll_y;
        } else {
            throw new IllegalStateException("Unknown ViewProperty: " + property);
        }

        return key;
    }
}
