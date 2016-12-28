package io.github.anotherme17.commonrvadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;
import io.github.anotherme17.commonrvadapter.holder.RecyclerViewHolder;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildCheckedChangeListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildLongClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildTouchListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemLongClickListener;
import io.github.anotherme17.commonrvadapter.manager.RvDelegateManager;

/**
 * Created by user798 on 2016/12/28.
 */

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;

    private List<T> mData;

    private RecyclerView mRecyclerView;

    private RvDelegateManager mDelegateManager;

    private RvHeadAndFootAdapter mHeadAndFootAdapter;

    private boolean mIsIgnoreCheckedChanged = true;

    /*=== listener ===*/
    private OnRvItemChildCheckedChangeListener mOnRvItemChildCheckedChangeListener;
    private OnRvItemChildClickListener mOnRvItemChildClickListener;
    private OnRvItemChildLongClickListener mOnRvItemChildLongClickListener;
    private OnRvItemChildTouchListener mOnRvItemChildTouchListener;
    private OnRvItemClickListener mOnRvItemClickListener;
    private OnRvItemLongClickListener mOnRvItemLongClickListener;

    public RecyclerViewAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false),
                mRecyclerView, this, mOnRvItemClickListener, mOnRvItemLongClickListener);
        holder.getRvHolderHelper().setOnItemChildCheckedChangeListener(mOnRvItemChildCheckedChangeListener);
        holder.getRvHolderHelper().setOnItemChildClickListener(mOnRvItemChildClickListener);
        holder.getRvHolderHelper().setOnItemChildLongClickListener(mOnRvItemChildLongClickListener);
        holder.getRvHolderHelper().setOnItemChildTouchListener(mOnRvItemChildTouchListener);
        setItemChildListener(holder, viewType);
        return holder;
    }

    /**
     * 为item的孩子节点设置监听器，并不是每一个数据列表都要为item的子控件添加事件监听器，所以这里采用了空实现，需要设置事件监听器时重写该方法即可
     *
     * @param helper
     */
    protected void setItemChildListener(RecyclerViewHolder helper, int viewType) {
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // 在设置值的过程中忽略选中状态变化
        mIsIgnoreCheckedChanged = true;

        fillData(holder.getRvHolderHelper(), position, getItem(position));

        mIsIgnoreCheckedChanged = false;
    }

    /**
     * 填充Item的数据
     *
     * @param rvHolderHelper
     * @param position
     * @param item
     */
    public abstract void fillData(RvHolderHelper rvHolderHelper, int position, T item);

    @Override
    public int getItemViewType(int position) {
        return mDelegateManager.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public boolean isIgnoreCheckedChanged() {
        return mIsIgnoreCheckedChanged;
    }

    /**
     * 获取指定索引位置的数据模型
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return mData;
    }

    public void notifyItemRangeInsertedWrapper(int startPosition, int itemCount) {
        if (mHeadAndFootAdapter == null) {
            notifyItemRangeChanged(startPosition, itemCount);
        } else {
            mHeadAndFootAdapter.notifyItemRangeChanged(mHeadAndFootAdapter.getHeadersCount() + startPosition, itemCount);
        }
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param data
     */
    public void addNewData(List<T> data) {
        if (data != null) {
            mData.addAll(0, data);
            notifyItemRangeInsertedWrapper(0, data.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public void addMoreData(List<T> data) {
        if (data != null) {
            int size = mData.size();
            mData.addAll(size, data);
            notifyItemRangeInsertedWrapper(size, data.size());
        }
    }

    public void notifyDataSetChangedWrapper() {
        if (mHeadAndFootAdapter == null) {
            notifyDataSetChanged();
        } else {
            mHeadAndFootAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(List<T> data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChangedWrapper();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChangedWrapper();
    }

    public void notifyItemRemovedWrapper(int position) {
        if (mHeadAndFootAdapter == null) {
            notifyItemRemoved(position);
        } else {
            mHeadAndFootAdapter.notifyItemRemoved(mHeadAndFootAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemovedWrapper(position);
    }

    /**
     * 删除指定数据条目。该方法在 ItemTouchHelper.Callback 的 onSwiped 方法中调用
     *
     * @param viewHolder
     */
    public void removeItem(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        if (mHeadAndFootAdapter != null) {
            mData.remove(position - mHeadAndFootAdapter.getHeadersCount());
            mHeadAndFootAdapter.notifyItemRemoved(position);
        } else {
            removeItem(position);
        }
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(T model) {
        removeItem(mData.indexOf(model));
    }

    public void notifyItemInsertedWrapper(int position) {
        if (mHeadAndFootAdapter == null) {
            notifyItemInserted(position);
        } else {
            mHeadAndFootAdapter.notifyItemInserted(mHeadAndFootAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, T model) {
        mData.add(position, model);
        notifyItemInsertedWrapper(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(T model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(T model) {
        addItem(mData.size(), model);
    }

    // TODO: 2016/12/28 ... By user798

    /*=== listener ===*/

    /**
     * 设置item的点击事件监听器
     *
     * @param onRVItemClickListener
     */
    public void setOnRVItemClickListener(OnRvItemClickListener onRVItemClickListener) {
        mOnRvItemClickListener = onRVItemClickListener;
    }

    /**
     * 设置item的长按事件监听器
     *
     * @param onRVItemLongClickListener
     */
    public void setOnRVItemLongClickListener(OnRvItemLongClickListener onRVItemLongClickListener) {
        mOnRvItemLongClickListener = onRVItemLongClickListener;
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(OnRvItemChildClickListener onItemChildClickListener) {
        mOnRvItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 设置item中的子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(OnRvItemChildLongClickListener onItemChildLongClickListener) {
        mOnRvItemChildLongClickListener = onItemChildLongClickListener;
    }

    /**
     * 设置item子控件选中状态变化事件监听器
     *
     * @param onItemChildCheckedChangeListener
     */
    public void setOnItemChildCheckedChangeListener(OnRvItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        mOnRvItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    /**
     * 设置item子控件触摸事件监听器
     *
     * @param onRVItemChildTouchListener
     */
    public void setOnRVItemChildTouchListener(OnRvItemChildTouchListener onRVItemChildTouchListener) {
        mOnRvItemChildTouchListener = onRVItemChildTouchListener;
    }
}
