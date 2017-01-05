package io.github.anotherme17.commonrvadapter.manager;

import android.support.annotation.LayoutRes;
import android.support.v4.util.SparseArrayCompat;

import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;

/**
 * Created by user798 on 2016/12/28.
 */

public class RvDelegateManager<T> {

    protected SparseArrayCompat<RvItemViewDelegate<T>> mDelegates = new SparseArrayCompat<>();

    public void addDelegate(RvItemViewDelegate<T> delegate) {
        int viewType = delegate.getItemLayoutId();
        if (mDelegates.get(viewType) != null) {
            throw new IllegalArgumentException("An ItemViewDelegate is already registered for the viewTypeId = "
                    + viewType
                    + ". Already registered ItemViewDelegate is "
                    + mDelegates.get(viewType));
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
            throw new IllegalArgumentException("Can not find ItemViewDelegate = " + delegate);
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
            throw new IllegalArgumentException("Can not find ItemViewDelegate Which Layout Id = " + layoutId);
        }
    }

    public int getItemViewType(int position, T data) {
        if (mDelegates.size() <= 0) {
            throw new RuntimeException("Delegate Size = 0 Please add Delegate");
        }

        for (int i = 0; i < mDelegates.size(); i++) {
            RvItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isDelegate(position, data)) {
                return delegate.getItemLayoutId();
            }
        }
        throw new RuntimeException("Can not Find Delegate Please Check You Code");
    }

    public RvItemViewDelegate getDelegateByViewType(int viewType) {
        return mDelegates.get(viewType);
    }

}
