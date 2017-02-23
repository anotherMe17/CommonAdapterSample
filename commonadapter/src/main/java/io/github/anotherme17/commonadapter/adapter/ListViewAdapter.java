package io.github.anotherme17.commonadapter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import io.github.anotherme17.commonadapter.helper.ViewHolderHelper;

/**
 * @author AnotherMe17
 */
public abstract class ListViewAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mData;
    private LayoutInflater mInflater;

    public ListViewAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderHelper helper = ViewHolderHelper.get(mContext, convertView, parent, getItemLayoutId(), position);

        injectView(helper, getItem(position), position);

        return convertView;
    }

    public abstract
    @LayoutRes
    int getItemLayoutId();

    public abstract void injectView(ViewHolderHelper helper, T data, int position);
}
