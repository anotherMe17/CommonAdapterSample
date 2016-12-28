package io.github.anotherme17.commonrvadapter.listener;

import android.view.MotionEvent;
import android.view.View;

import io.github.anotherme17.commonrvadapter.holder.RecyclerViewHolder;

/**
 * Created by user798 on 2016/12/28.
 */

public interface OnRvItemChildTouchListener {
    boolean onRvItemChilcTouch(RecyclerViewHolder viewHolder, View childView, MotionEvent event);
}
