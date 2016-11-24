package io.github.anotherme17.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import io.github.anotherme17.commonadapter.manager.ItemViewManager;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/24 11:03
 * 修改备注：
 */
public class BaseMultiAdapter<T> extends BaseAdapter {
    private static final String TAG = "BaseMultiAdapter";

    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewManager<T> mViewManager;

    public BaseMultiAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mViewManager = new ItemViewManager<>();
    }

    public BaseMultiAdapter(Context context, List<T> datas, List<Integer> typeIds) {
        this(context, datas);
        mViewManager.addMapTab(typeIds);
    }

    public BaseMultiAdapter(Context context, List<T> datas, int[] typeIds) {
        this(context, datas);
        mViewManager.addMapTab(typeIds);
    }

    public void addSignalItem(T data, int position) {
        mDatas.add(position, data);
        notifyDataSetChanged();
    }

    public void addMultiItem(T data, int typeId, int position) {
        mDatas.add(position, data);
        mViewManager.addMapTab(typeId, position);
        notifyDataSetChanged();
    }

    protected void setSignalMode(int typeId) {
        mViewManager.isSignal = true;
        mViewManager.signalId = typeId;
    }

    protected void setMultiMode() {
        mViewManager.isSignal = false;
        mViewManager.signalId = 0;
    }

    protected void addItemViewType(int typeId) {
        mViewManager.addMapTab(typeId);
    }

    protected void addItemViewType(int typeId, int position) {
        mViewManager.addMapTab(typeId, position);
    }

    public void addItemViewDegelate(ItemViewDelegate<T> viewDelegate) {
        mViewManager.addDelegate(viewDelegate);
    }

    public int getItemViewDegelateSize() {
        return mViewManager.getDelegateCount();
    }

    public ItemViewDelegate<T> getItemViewDegelate(int position) {
        return mViewManager.getItemViewDelegate(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getItemViewDegelateSize();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewManager.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate<T> viewDelegate = getItemViewDegelate(position);
        int layoutId = viewDelegate.getItemLayoutId();
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(mContext, view, parent, position);
            viewHolder.mLayoutId = layoutId;
            viewDelegate.onViewHolderCreated(mContext, view, mDatas.get(position), position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        viewDelegate.convert(mContext, viewHolder, mDatas.get(position), position);
        //convert(viewHolder, mDatas.get(position), position);

        return viewHolder.getConvertView();
    }

}
