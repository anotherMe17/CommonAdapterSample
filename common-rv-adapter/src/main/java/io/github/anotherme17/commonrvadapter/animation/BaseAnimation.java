package io.github.anotherme17.commonrvadapter.animation;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * 设置滑动时Item的动画
 * {@link io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter#setLoadAnimation(BaseAnimation)}
 *
 * @author anotherme17
 */
public abstract class BaseAnimation {

    /**
     * 动画持续时间,默认为300ms,可通过{{@link BaseAnimation#setDuration(long)}}进行设置
     */
    private long mDuration = 300;

    /**
     * 动画插值器,默认为线性的,可通过{{@link BaseAnimation#setInterpolator(Interpolator)}} 进行设置
     */
    private Interpolator mInterpolator = new LinearInterpolator();

    /**
     * 设置动画
     *
     * @param view 要设置动画的View,即Item的View
     * @return 返回设置完成的动画集合
     */
    public abstract Animator[] getAnimators(View view);

    /**
     * @return 返回动画持续时间
     */
    public long getDuration() {
        return mDuration;
    }

    /**
     * @param duration 设置动画持续时间
     */
    public void setDuration(long duration) {
        mDuration = duration;
    }

    /**
     * @return 返回动画插值器
     */
    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    /**
     * @param interpolator 设置动画插值器
     */
    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }
}
