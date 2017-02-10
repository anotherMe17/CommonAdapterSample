package io.github.anotherme17.commonrvadapter.listener;

import android.view.MotionEvent;
import android.view.View;

import io.github.anotherme17.commonrvadapter.holder.RecyclerViewHolder;

/**
 * Item中的ChildView的touch事件
 * @author anotherme17
 */
public interface OnRvItemChildTouchListener {
    /**
     * Item中的ChildView的touch事件
     * @param viewHolder
     * @param childView
     * @param event
     * @return
     */
    boolean onRvItemChilcTouch(RecyclerViewHolder viewHolder, View childView, MotionEvent event);
}
