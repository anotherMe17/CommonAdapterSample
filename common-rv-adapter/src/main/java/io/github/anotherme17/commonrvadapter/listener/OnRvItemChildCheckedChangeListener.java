package io.github.anotherme17.commonrvadapter.listener;

import android.view.ViewGroup;
import android.widget.CompoundButton;

/**
 * Item子控件的状态改变监听事件
 *
 * @author anotherme17
 */
public interface OnRvItemChildCheckedChangeListener {

    /**
     * Item子控件状态改变时返回
     *
     * @param parent    状态发生改变的View的ParentView
     * @param childView 状态发生改变的View
     * @param position  Item所在的位置
     * @param isChecked 选中状态
     */
    void onRvItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked);
}
