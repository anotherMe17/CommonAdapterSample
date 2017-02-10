package io.github.anotherme17.commonrvadapter.exception;

import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;

/**
 * Created by Administrator on 2017/2/10.
 */

public class DelegateAlreadyExistException extends RuntimeException {

    public DelegateAlreadyExistException(int viewType, RvItemViewDelegate delegate) {
        super("An ItemViewDelegate is already registered for the viewTypeId = "
                + viewType
                + ". Already registered ItemViewDelegate is "
                + delegate);
    }
}
