package com.ifhu.meiwei.ui.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

/**
 * @author fuhongliang
 */
public class SafeAnimatorListenerAdapter extends AnimatorListenerAdapter {
    @Override
    public void onAnimationCancel(Animator animation) {
        super.onAnimationCancel(animation);
        animation.removeAllListeners();
        if(animation instanceof ValueAnimator){
            ValueAnimator valueAnimator = (ValueAnimator) animation;
            valueAnimator.removeAllUpdateListeners();
        }
    }
}
