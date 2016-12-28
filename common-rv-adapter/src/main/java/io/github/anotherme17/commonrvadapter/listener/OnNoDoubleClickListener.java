package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;

/**
 * Created by user798 on 2016/12/28.
 */

public abstract class OnNoDoubleClickListener implements View.OnClickListener {

    private int mClickSpaceTime = 800;
    private long mLastClickTime = 0;

    public OnNoDoubleClickListener() {

    }

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

    public abstract void onNoDoubleClick(View v);

}
