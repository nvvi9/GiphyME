package com.nvvi9.giphyme.utils;

import android.view.View;

import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpringAddItemAnimator extends DefaultItemAnimator {

    private final List<RecyclerView.ViewHolder> pendingAdds = new ArrayList<>();

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0f);
        holder.itemView.setTranslationY(holder.itemView.getBottom() / 3f);
        pendingAdds.add(holder);
        return true;
    }

    @Override
    public void runPendingAnimations() {
        super.runPendingAnimations();

        if (!pendingAdds.isEmpty()) {
            for (int i = pendingAdds.size() - 1; i >= 0; ) {
                RecyclerView.ViewHolder holder = pendingAdds.get(i);

                SpringAnimation tySpring = ViewUtils.spring(holder.itemView, SpringAnimation.TRANSLATION_Y, 350f, 0.6f, null);
                SpringAnimation aSpring = ViewUtils.spring(holder.itemView, SpringAnimation.ALPHA, 100f, SpringForce.DAMPING_RATIO_NO_BOUNCY, null);

                new MultiSpringAddListener(cancelled -> {
                    if (!cancelled) {
                        dispatchAddFinished(holder);
                        dispatchFinishedWhenDone();
                    } else {
                        clearAnimatedValues(holder.itemView);
                    }
                }, tySpring, aSpring);

                dispatchAddStarting(holder);
                aSpring.animateToFinalPosition(1f);
                tySpring.animateToFinalPosition(0f);
                pendingAdds.remove(i);
                --i;
            }
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        ViewUtils.spring(item.itemView, SpringAnimation.TRANSLATION_Y, 200f, 0.3f, null).cancel();
        ViewUtils.spring(item.itemView, SpringAnimation.ALPHA, 200f, 0.3f, null).cancel();
        if (pendingAdds.remove(item)) {
            dispatchAddFinished(item);
            clearAnimatedValues(item.itemView);
        }

        super.endAnimation(item);
    }

    @Override
    public boolean isRunning() {
        return !pendingAdds.isEmpty() || super.isRunning();
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    private void clearAnimatedValues(View view) {
        view.setAlpha(1f);
        view.setTranslationY(0f);
    }
}