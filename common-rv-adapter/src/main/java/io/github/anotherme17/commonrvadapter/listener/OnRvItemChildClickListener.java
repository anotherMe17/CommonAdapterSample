package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * Item中的ChildView的点击事件
 *
 * @author anotherme17
 */
public interface OnRvItemChildClickListener {

    /**
     * Item中的ChildView的点击事件
     *
     * @param parent    parentView
     * @param childView 被点击的View
     * @param position  Item所在的位置
     */
    void onRvItemChildClick(ViewGroup parent, View childView, int position);
}
