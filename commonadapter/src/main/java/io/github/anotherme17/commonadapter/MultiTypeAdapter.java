package io.github.anotherme17.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 项目名称：RapidDevelopemt
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 9:31
 * 修改备注：
 */
public class MultiTypeAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected List<Integer> mTypes = null;

    protected ItemViewDelegateManager<T> mDelegateManager;

    public MultiTypeAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mDelegateManager = new ItemViewDelegateManager<>();
    }

    public MultiTypeAdapter(Context context, List<T> datas, List<Integer> types) {
        this(context, datas);
        mTypes = types;
    }

    /**
     * 添加Item的类型
     *
     * @param viewDelegate Item类型
     * @return this
     */
    public MultiTypeAdapter<T> addItemViewDelegate(ItemViewDelegate<T> viewDelegate) {
        mDelegateManager.addDelegate(viewDelegate);
        return this;
    }

    public MultiTypeAdapter<T> addItemViewDelegate(ItemViewDelegate<T> viewDelegate, int viewtype) {
        mDelegateManager.addDelegate(viewDelegate, viewtype);
        return this;
    }

    public int getItemDelegateManagerSize() {
        return mDelegateManager.getCount();
    }

    @Override
    public int getViewTypeCount() {
        return getItemDelegateManagerSize() > 0 ? getItemDelegateManagerSize() : super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (mTypes != null) {
                return mTypes.get(position);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("types size = " + mTypes.size() + " must equal or bigger than datas = " + mDatas.size());
        }
        if (getItemDelegateManagerSize() > 0) {
            return mDelegateManager.getItemViewType(mDatas.get(position), position);
        }
        return super.getItemViewType(position);
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

    public List<Integer> getTypes() {
        return mTypes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate<T> viewDelegate = mDelegateManager.getItemViewDelegate(mDatas.get(position),
                position, getItemViewType(position));
        int layoutId = viewDelegate.getItemLayoutId();

        ViewHolder viewHolder = null;
        if (convertView == null) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(mContext, view, parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(mContext, view, mDatas.get(position), position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        viewDelegate.convert(mContext, viewHolder, mDatas.get(position), position);
        //convert(viewHolder, mDatas.get(position), position);

        return viewHolder.getConvertView();
    }

    public void onViewHolderCreated(Context context, View view, T data, int position) {

    }

/*    protected void convert(ViewHolder viewHolder, T data, int position) {
        //mDelegateManager.convert(viewHolder, data, position);
        //viewDelegate.convert(viewHolder, mDatas.get(position), position);
    }*/
}
