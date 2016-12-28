package io.github.anotherme17.commonrvadapter.listener;

import android.view.ViewGroup;
import android.widget.CompoundButton;

/**
 * Created by user798 on 2016/12/28.
 */

public interface OnRvItemChildCheckedChangeListener {
    void onRvItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked);
}
