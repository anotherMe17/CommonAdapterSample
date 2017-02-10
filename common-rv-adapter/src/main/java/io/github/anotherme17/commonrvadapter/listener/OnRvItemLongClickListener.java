package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;
import android.view.ViewGroup;


/**
 * Item的长按事件
 *
 * @author anotherme17
 */
public interface OnRvItemLongClickListener {

    /**
     * Item的长按事件
     *
     * @param parent   ParentView
     * @param itemView 被点击的Item的View
     * @param position item的位置
     * @return
     */
    boolean onRvItemLongClick(ViewGroup parent, View itemView, int position);
}
