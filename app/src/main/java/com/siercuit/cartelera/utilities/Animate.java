package com.siercuit.cartelera.utilities;

import android.animation.Animator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ValueAnimator;
import com.siercuit.cartelera.App;

public class Animate
{

    public static void actionBarColor(int colorFrom, int colorTo, final ActionBar actionBar)
    {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                actionBar.setBackgroundDrawable(new ColorDrawable((Integer) animator.getAnimatedValue()));
            }

        });
        colorAnimation.setDuration(500);
        colorAnimation.start();
    }

    public static void textColor(int colorFrom, int colorTo, final TextView textView)
    {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                textView.setTextColor((Integer) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    public static void textColor(final int colorFrom, final int colorTo, final TextView[] textViews)
    {
        for (final TextView textView : textViews) {
            final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    textView.setTextColor((Integer) animator.getAnimatedValue());
                }
            });
            colorAnimation.start();
        }
    }

    public static void viewReveal(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (view.isAttachedToWindow()) {
                int cx = (view.getLeft() + view.getRight()) / 2;
                int cy = (view.getTop() + view.getBottom()) / 2;
                int finalRadius = view.getWidth();
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
                anim.start();
            }
        } else {
            view.startAnimation(AnimationUtils.loadAnimation(App.getActivity(), android.R.anim.slide_in_left));
        }
    }

}
