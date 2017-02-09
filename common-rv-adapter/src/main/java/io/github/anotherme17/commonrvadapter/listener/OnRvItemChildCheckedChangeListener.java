package io.github.anotherme17.commonrvadapter.listener;

import android.view.ViewGroup;
import android.widget.CompoundButton;

/**
 * @author anotherme17
 */
public interface OnRvItemChildCheckedChangeListener {
    void onRvItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked);
}
