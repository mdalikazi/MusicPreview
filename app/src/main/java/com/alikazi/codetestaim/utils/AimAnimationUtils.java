package com.alikazi.codetestaim.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.widget.Toolbar;

public class AimAnimationUtils {

    private static final String LOG_TAG = AppConstants.AIM_LOG_TAG;

    public static void animateToolbar(Context context, final Toolbar toolbar, final ToolbarAnimationListener listener) {
        if (toolbar != null) {
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            float toolbarHeight = layoutParams.height;
            ValueAnimator animator = ValueAnimator.ofFloat(toolbarHeight, getDefaultActionBarHeightInPixels(context));
            animator.setDuration(400);
            animator.setStartDelay(500);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setTarget(toolbar);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
                    Object animatedHeight = animation.getAnimatedValue();
                    if (animatedHeight instanceof Float) {
                        lp.height = ((Float) animatedHeight).intValue();
                    }
                    toolbar.setLayoutParams(lp);
                }
            });

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    listener.onToolbarAnimationEnd();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator.start();
        }
    }

    private static float getDefaultActionBarHeightInPixels(Context context) {
        TypedValue typedValue = new TypedValue();
        boolean canGetValue = context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        if (canGetValue) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        } else {
            return 0f;
        }
    }

    public interface ToolbarAnimationListener {
        void onToolbarAnimationEnd();
    }
}
