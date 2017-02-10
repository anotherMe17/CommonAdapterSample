package io.github.anotherme17.commonrvadapter.exception;

import android.support.annotation.LayoutRes;

import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;

/**
 * Created by Administrator on 2017/2/10.
 */

public class DelegateNotFoundException extends RuntimeException {

    public DelegateNotFoundException() {
        super("Can not Find any Delegate");
    }

    public DelegateNotFoundException(RvItemViewDelegate delegate) {
        super("Can not find ItemViewDelegate = " + delegate);
    }

    public DelegateNotFoundException(@LayoutRes int layoutId) {
        super("Can not find ItemViewDelegate Which Layout Id = " + layoutId);
    }
}
