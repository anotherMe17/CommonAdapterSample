package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * Item中的ChildView的长按事件
 *
 * @author anotherme17
 */
public interface OnRvItemChildLongClickListener {

    /**
     * Item中的ChildView的长按事件
     *
     * @param parent    parentView
     * @param childView 被长按的View
     * @param position  Item的位置
     * @return
     */
    boolean onRvItemChildLongClick(ViewGroup parent, View childView, int position);
}
