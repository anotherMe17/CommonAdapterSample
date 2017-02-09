package io.github.anotherme17.commonrvadapter.listener;

import android.view.MotionEvent;
import android.view.View;

import io.github.anotherme17.commonrvadapter.holder.RecyclerViewHolder;

/**
 * @author anotherme17
 */
public interface OnRvItemChildTouchListener {
    boolean onRvItemChilcTouch(RecyclerViewHolder viewHolder, View childView, MotionEvent event);
}
