package com.nvvi9.giphyme.utils;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MultiSpringAddListener {

    private final List<DynamicAnimation.OnAnimationEndListener> listeners;
    private boolean wasCancelled = false;

    public MultiSpringAddListener(Consumer<Boolean> onEnd, SpringAnimation... springs) {
        listeners = new ArrayList<>(springs.length);
        for (SpringAnimation springAnimation : springs) {
            DynamicAnimation.OnAnimationEndListener onAnimationEndListener = new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                    if (animation != null) {
                        animation.removeEndListener(this);
                    }
                    wasCancelled = wasCancelled || canceled;
                    listeners.remove(this);
                    if (listeners.isEmpty()) {
                        onEnd.accept(wasCancelled);
                    }
                }
            };

            springAnimation.addEndListener(onAnimationEndListener);
            listeners.add(onAnimationEndListener);
        }
    }
}
