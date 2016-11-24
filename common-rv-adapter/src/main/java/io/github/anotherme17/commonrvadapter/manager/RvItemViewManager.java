package io.github.anotherme17.commonrvadapter.manager;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;
import java.util.List;

import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;


/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/24 9:11
 * 修改备注：
 */
public class RvItemViewManager<T> {
    public boolean isSignal = true;
    public int signalId = 0;
    protected SparseArrayCompat<RvItemViewDelegate<T>> mDelegate = new SparseArrayCompat<>();

    /**
     *
     */
    protected List<Integer> mMapTab = new ArrayList<>();

    public RvItemViewManager<T> addDelegate(@NonNull RvItemViewDelegate<T> viewDelegate) {
        int viewTypeId = viewDelegate.getItemViewId();
        if (mDelegate.get(viewTypeId) != null) {
            throw new IllegalArgumentException("An ItemViewDelegate is already registered for the viewTypeId = "
                    + viewTypeId
                    + ". Already registered ItemViewDelegate is "
                    + mDelegate.get(viewTypeId));
        }
        mDelegate.put(viewTypeId, viewDelegate);
        return this;
    }

    public RvItemViewManager<T> addDelegate(@NonNull RvItemViewDelegate<T> viewDelegate, int viewTypeId) {
        if (mDelegate.get(viewTypeId) != null) {
            throw new IllegalArgumentException("An ItemViewDelegate is already registered for the viewTypeId = "
                    + viewTypeId
                    + ". Already registered ItemViewDelegate is "
                    + mDelegate.get(viewTypeId));
        }
        mDelegate.put(viewTypeId, viewDelegate);
        return this;
    }

    public RvItemViewManager<T> removeDelegate(@NonNull RvItemViewDelegate<T> viewDelegate) {
        int indexToRemove = mDelegate.indexOfValue(viewDelegate);
        if (indexToRemove >= 0) {
            removeMapTabAll(mDelegate.keyAt(indexToRemove));
            mDelegate.removeAt(indexToRemove);
        } else {
            throw new IllegalArgumentException("Can not find ItemViewDelegate = " + viewDelegate);
        }

        return this;
    }

    public RvItemViewManager<T> removeDelegate(int viewTypeId) {
        int indexToRemove = mDelegate.indexOfKey(viewTypeId);
        if (indexToRemove >= 0) {
            removeMapTabAll(mDelegate.keyAt(indexToRemove));
            mDelegate.removeAt(indexToRemove);
        } else {
            throw new IllegalArgumentException("Can not find ItemType = " + viewTypeId);
        }

        return this;
    }

    /**
     * 将types和degelate关联起来
     *
     * @param typeIds 传入的type
     */
    public void addMapTab(List<Integer> typeIds) {
        mMapTab = typeIds;
    }

    public void addMapTab(int[] typeIds) {
        mMapTab = new ArrayList<>();
        for (int type : typeIds) {
            mMapTab.add(type);
        }
    }

    public void addMapTab(int typeId, int position) {
        if (position > getMapTabCount())
            throw new ArrayIndexOutOfBoundsException("position = " + position + "Can not over than MapTab size = " + getMapTabCount());
        mMapTab.add(position, typeId);
    }

    public void addMapTab(int typeId) {
        mMapTab.add(typeId);
    }

    public int removeMapTabAll(int typeId) {
        int removeNum = 0;
        while (removeMapTab(typeId)) {
            removeNum++;
        }
        return removeNum;
    }

    public boolean removeMapTab(int typeId) {
        return mMapTab.remove(Integer.valueOf(typeId));
    }

    public int getItemViewType(int position) {
        return isSignal ? 0 : mDelegate.indexOfKey(mMapTab.get(position));
    }

    public int getItemViewLayoutId(int viewTypeId) {
        return mDelegate.get(viewTypeId).getItemLayoutId();
    }

    public int getItemViewLayoutIdWithPosition(int position) {
        return isSignal ? getItemViewLayoutId(signalId) : getItemViewLayoutId(mMapTab.get(position));
    }

    public RvItemViewDelegate<T> getItemViewDelegate(int position) {
        return isSignal ? mDelegate.get(signalId) : mDelegate.get(mMapTab.get(position));
    }

    public RvItemViewDelegate<T> getItemViewDegelateByIndex(int index) {
        return isSignal ? mDelegate.get(signalId) : mDelegate.valueAt(index);
    }

    public int getViewTypeId(int position) {
        return isSignal ? signalId : mMapTab.get(position);
    }

    public int getDelegateCount() {
        return isSignal ? 1 : mDelegate.size();
    }

    public int getMapTabCount() {
        return isSignal ? 1 : mMapTab.size();
    }
}
