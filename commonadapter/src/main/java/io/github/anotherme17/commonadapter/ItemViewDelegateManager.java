package io.github.anotherme17.commonadapter;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

/**
 * 项目名称：RapidDevelopemt
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 9:32
 * 修改备注：
 */
public class ItemViewDelegateManager<T> {

    public SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat<>();

    public int getCount() {
        return delegates.size();
    }

    public ItemViewDelegateManager<T> addDelegate(@NonNull ItemViewDelegate<T> viewDelegate) {
        int viewType = getCount();
        delegates.put(viewType, viewDelegate);
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(@NonNull ItemViewDelegate<T> viewDelegate, @NonNull int viewType) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException("An ItemViewDelegate is already registered for the viewType = "
                    + viewType
                    + ". Already registered ItemViewDelegate is "
                    + delegates.get(viewType));
        }

        delegates.put(viewType, viewDelegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(@NonNull ItemViewDelegate<T> viewDelegate) {
        int indexToRemove = delegates.indexOfValue(viewDelegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        } else {
            throw new IllegalArgumentException("Can not find ItemViewDelegate = " + viewDelegate);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(@NonNull int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        } else {
            throw new IllegalArgumentException("Can not find ItemType = " + itemType);
        }

        return this;
    }

    public int getItemViewType(T data, int position) {
        int size = getCount();
        System.out.println("ItemViewDelegate size = " + size);
        for (int i = size - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            int indexOfKey = delegates.keyAt(i);
            if (delegate.isForViewType(data, position, indexOfKey)) {
                return indexOfKey;
            }
        }

        throw new IllegalArgumentException("No ItemViewDelegate match data = " + data + "pstotion = " + position);
    }

/*    public void convert(ViewHolder viewHolder, T data, int position) {
        int size = getCount();
        for (int i = size - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position, delegates.keyAt(i))) {
                delegate.convert(viewHolder, data, position);
                return;
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegate match data = " + data + "pstotion = " + position);
    }*/

    public int getItemViewLayoutId(@NonNull int viewType) {
        return delegates.get(viewType).getItemLayoutId();
    }

    public int getItemViewLayoutId(T data, int position, int viewType) {
        return getItemViewDelegate(data, position, viewType).getItemLayoutId();
    }

    public int getItemViewType(@NonNull ItemViewDelegate<T> viewDelegate) {
        int index = delegates.indexOfValue(viewDelegate);

        if (index >= 0) {
            return delegates.keyAt(index);
        } else {
            throw new IllegalArgumentException("Can not find ItemViewDelegate = " + viewDelegate);
        }
    }

    public ItemViewDelegate<T> getItemViewDelegate(T data, int position, int viewType) {
        int size = getCount();
        for (int i = size - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position, viewType)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }
}
