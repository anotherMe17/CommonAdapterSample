package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * Item的Click事件
 *
 * @author anotherme17
 */
public interface OnRvItemClickListener {

    /**
     * Item的Click事件
     *
     * @param parent   ParentView
     * @param itemView 被点击的Item的View
     * @param position item的位置
     */
    void onRvItemClick(ViewGroup parent, View itemView, int position);
}
