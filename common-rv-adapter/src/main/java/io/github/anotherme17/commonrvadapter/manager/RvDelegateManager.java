package io.github.anotherme17.commonrvadapter.manager;

import android.support.annotation.LayoutRes;
import android.support.v4.util.SparseArrayCompat;

import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.exception.DelegateAlreadyExistException;
import io.github.anotherme17.commonrvadapter.exception.DelegateNotFoundException;


/**
 * ItemDelegate 帮助类
 *
 * @author anotherme17
 * @version 1.0.0
 */
public class RvDelegateManager<T> {

    protected SparseArrayCompat<RvItemViewDelegate<T>> mDelegates = new SparseArrayCompat<>();

    public void addDelegate(RvItemViewDelegate<T> delegate) {
        int viewType = delegate.getItemLayoutId();
        if (mDelegates.get(viewType) != null) {
            throw new DelegateAlreadyExistException(viewType, mDelegates.get(viewType));
        } else {
            mDelegates.put(viewType, delegate);
        }
    }

    public void addDelegate(RvItemViewDelegate<T>... delegates) {
        for (RvItemViewDelegate<T> delegate : delegates) {
            addDelegate(delegate);
        }
    }

    public void removeDelegate(RvItemViewDelegate<T> delegate) {
        int indexToRemove = mDelegates.indexOfValue(delegate);
        if (indexToRemove >= 0) {
            mDelegates.removeAt(indexToRemove);
        } else {
            throw new DelegateNotFoundException(delegate);
        }
    }

    public void removeDelegate(RvItemViewDelegate<T>... delegates) {
        for (RvItemViewDelegate<T> delegate : delegates) {
            removeDelegate(delegate);
        }
    }

    public void removeDelegate(@LayoutRes int layoutId) {
        int indexToRemove = mDelegates.indexOfKey(layoutId);
        if (indexToRemove >= 0) {
            mDelegates.removeAt(indexToRemove);
        } else {
            throw new DelegateNotFoundException(layoutId);
        }
    }

    public void removeAllDelegate() {
        mDelegates.clear();
    }

    public int getItemViewType(int position, T data) {
        if (mDelegates.size() <= 0) {
            throw new DelegateNotFoundException();
        }

        for (int i = 0; i < mDelegates.size(); i++) {
            RvItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isDelegate(position, data)) {
                return delegate.getItemLayoutId();
            }
        }
        throw new DelegateNotFoundException();
    }

    public RvItemViewDelegate getDelegateByViewType(int viewType) {
        return mDelegates.get(viewType);
    }

}
