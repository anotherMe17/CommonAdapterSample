package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author anotherme17
 */
public interface OnRvItemChildLongClickListener {
    boolean onRvItemChildLongClick(ViewGroup parent, View childView, int position);
}
