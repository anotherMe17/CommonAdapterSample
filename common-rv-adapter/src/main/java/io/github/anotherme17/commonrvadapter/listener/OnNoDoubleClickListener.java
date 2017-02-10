package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;

/**
 * 防止连续点击两次的ClickListener
 *
 * @author anotherme17
 */
public abstract class OnNoDoubleClickListener implements View.OnClickListener {

    /**
     * 两次点击事件间隔,默认为800ms,可以在构造时通过{@link OnNoDoubleClickListener#OnNoDoubleClickListener(int)} 对时间间隔进行设置
     */
    private int mClickSpaceTime = 800;
    private long mLastClickTime = 0;

    public OnNoDoubleClickListener() {

    }

    /**
     * 带两次点击事件的时间间隔的构造方法
     *
     * @param clickSpaceTime 两次点击事件的时间间隔(ms)
     */
    public OnNoDoubleClickListener(int clickSpaceTime) {
        this.mClickSpaceTime = clickSpaceTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - mLastClickTime) > mClickSpaceTime) {
            mLastClickTime = currentTime;
            onNoDoubleClick(v);

        }
    }

    /**
     * 过滤之后的点击时间
     *
     * @param v 被点击的View
     */
    public abstract void onNoDoubleClick(View v);

}
