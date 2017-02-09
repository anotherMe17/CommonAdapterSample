package io.github.anotherme17.commonrvadapter.animation;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * @author anotherme17
 */
public abstract class BaseAnimation {

    protected long mDuration = 300;
    protected Interpolator mInterpolator = new LinearInterpolator();

    public abstract Animator[] getAnimators(View view);

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }
}
