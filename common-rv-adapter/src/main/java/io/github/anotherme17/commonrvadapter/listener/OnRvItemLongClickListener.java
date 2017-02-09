package io.github.anotherme17.commonrvadapter.listener;

import android.view.View;
import android.view.ViewGroup;


/**
 * @author anotherme17
 */
public interface OnRvItemLongClickListener {
    boolean onRvItemLongClick(ViewGroup parent, View itemView, int position);
}
