package io.github.anotherme17.commonrvadapter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.anotherme17.commonrvadapter.Constants;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.helper.BaseItemTouchHelper;
import io.github.anotherme17.commonrvadapter.helper.RvItemTouchHelper;
import io.github.anotherme17.commonrvadapter.holder.RecyclerViewHolder;
import io.github.anotherme17.commonrvadapter.listener.OnItemDragCallback;
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

public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private int mDefaultItemId = 0;

    protected Context mContext;

    private List<T> mData;

    private RecyclerView mRecyclerView;

    private RvDelegateManager<T> mDelegateManager;

    private RvHeadAndFootAdapter mHeadAndFootAdapter;

    private boolean mIsIgnoreCheckedChanged = true;

    /*=== helper ===*/
    private RvItemTouchHelper mRvItemTouchHelper = null;

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
        mDelegateManager = new RvDelegateManager<>();
    }

    public RecyclerViewAdapter(RecyclerView recyclerView, int defaultItemId) {
        this(recyclerView);
        this.mDefaultItemId = defaultItemId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false),
                mRecyclerView, this, mOnRvItemClickListener, mOnRvItemLongClickListener);
        holder.getRvHolderHelper().setOnItemChildCheckedChangeListener(mOnRvItemChildCheckedChangeListener);
        holder.getRvHolderHelper().setOnItemChildClickListener(mOnRvItemChildClickListener);
        holder.getRvHolderHelper().setOnItemChildLongClickListener(mOnRvItemChildLongClickListener);
        holder.getRvHolderHelper().setOnItemChildTouchListener(mOnRvItemChildTouchListener);
        //setItemChildListener(holder.getRvHolderHelper(), viewType);

        RvItemViewDelegate delegate = mDelegateManager.getDelegateByViewType(viewType);
        delegate.onViewHolderCreated(mContext, holder.getRvHolderHelper());
        delegate.setItemChildListener(holder.getRvHolderHelper(), viewType);

        holder.getRvHolderHelper().setOnItemDragCallback(new OnItemDragCallback() {
            @Override
            public void setDragView(RecyclerViewHolder holder) {
                if (mRvItemTouchHelper != null)
                    mRvItemTouchHelper.getItemTouchHelper().startDrag(holder);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        // 在设置值的过程中忽略选中状态变化
        mIsIgnoreCheckedChanged = true;
        //fillData(holder.getRvHolderHelper(), position, getItem(position));
        mDelegateManager.getDelegateByViewType(holder.getItemViewType()).convert(mContext, holder.getRvHolderHelper(), position, getItem(position));

        mIsIgnoreCheckedChanged = false;
    }

    @Override
    public int getItemViewType(int position) {
        return mDefaultItemId == 0 ? mDelegateManager.getItemViewType(position, mData.get(position)) : mDefaultItemId;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public boolean isIgnoreCheckedChanged() {
        return mIsIgnoreCheckedChanged;
    }

    public void setItemTouch(int model) {
        if (mRvItemTouchHelper == null)
            mRvItemTouchHelper = new RvItemTouchHelper(this);
        mRvItemTouchHelper.setModel(model);
        mRvItemTouchHelper.getItemTouchHelper().attachToRecyclerView(mRecyclerView);
    }

    public void setItemTouchListener(RvItemTouchHelper.OnItemDragAndSwipeListener onItemDragAndSwipeListener) {
        if (mRvItemTouchHelper == null) {
            setItemTouch(Constants.DEFAULT_MODEL);
        }
        mRvItemTouchHelper.setOnItemDragAndSwipeListener(onItemDragAndSwipeListener);
    }

    public RvItemTouchHelper getItemTouchHelper() {
        return mRvItemTouchHelper;
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


    public void notifyItemChangedWrapper(int position) {
        if (mHeadAndFootAdapter == null) {
            notifyItemChanged(position);
        } else {
            mHeadAndFootAdapter.notifyItemChanged(mHeadAndFootAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param posotion
     * @param model
     */
    public void setItem(int posotion, T model) {
        mData.set(posotion, model);
        notifyItemChangedWrapper(posotion);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(T oldModel, T newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    public void notifyItemMovedWrapper(int fromPosition, int toPosition) {
        if (mHeadAndFootAdapter == null) {
            notifyItemMoved(fromPosition, toPosition);
        } else {
            mHeadAndFootAdapter.notifyItemMoved(mHeadAndFootAdapter.getHeadersCount() + fromPosition, mHeadAndFootAdapter.getHeadersCount() + toPosition);
        }
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        notifyItemChangedWrapper(fromPosition);
        notifyItemChangedWrapper(toPosition);

        // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作

        mData.add(toPosition, mData.remove(fromPosition));
        notifyItemMovedWrapper(fromPosition, toPosition);
    }

    /**
     * 移动数据条目的位置。该方法在 ItemTouchHelper.Callback 的 onMoved 方法中调用
     *
     * @param fromHolder
     * @param toHolder
     */
    public void moveItem(RecyclerView.ViewHolder fromHolder, RecyclerView.ViewHolder toHolder) {
        int fromPosition = fromHolder.getAdapterPosition();
        int toPosition = toHolder.getAdapterPosition();
        if (mHeadAndFootAdapter != null) {
            mHeadAndFootAdapter.notifyItemChanged(fromPosition);
            mHeadAndFootAdapter.notifyItemChanged(toPosition);
            // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作
            mData.add(toPosition - mHeadAndFootAdapter.getHeadersCount(), mData.remove(fromPosition - mHeadAndFootAdapter.getHeadersCount()));
            mHeadAndFootAdapter.notifyItemMoved(fromPosition, toPosition);
        } else {
            moveItem(fromPosition, toPosition);
        }
    }

    /**
     * @return 获取第一个数据模型
     */
    public
    @Nullable
    T getFirstItem() {
        return getItemCount() > 0 ? getItem(0) : null;
    }

    /**
     * @return 获取最后一个数据模型
     */
    public
    @Nullable
    T getLastItem() {
        return getItemCount() > 0 ? getItem(getItemCount() - 1) : null;
    }


    public void addHeaderView(View headerView) {
        getHeaderAndFooterAdapter().addHeaderView(headerView);
    }

    public void addFooterView(View footerView) {
        getHeaderAndFooterAdapter().addFooterView(footerView);
    }

    public int getHeadersCount() {
        return mHeadAndFootAdapter == null ? 0 : mHeadAndFootAdapter.getHeadersCount();
    }

    public int getFootersCount() {
        return mHeadAndFootAdapter == null ? 0 : mHeadAndFootAdapter.getFootersCount();
    }

    public RvHeadAndFootAdapter getHeaderAndFooterAdapter() {
        if (mHeadAndFootAdapter == null) {
            synchronized (RecyclerViewAdapter.this) {
                if (mHeadAndFootAdapter == null) {
                    mHeadAndFootAdapter = new RvHeadAndFootAdapter(this);
                }
            }
        }
        return mHeadAndFootAdapter;
    }

    /**
     * 是否是头部或尾部
     *
     * @param viewHolder
     * @return
     */
    public boolean isHeaderOrFooter(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() < getHeadersCount() || viewHolder.getAdapterPosition() >= getHeadersCount() + getItemCount();
    }

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

    /*=== delegate ===*/
    public void addDelegate(RvItemViewDelegate<T> delegate) {
        mDelegateManager.addDelegate(delegate);
    }

    public void removeDelegate(RvItemViewDelegate<T> delegate) {
        mDelegateManager.removeDelegate(delegate);
    }

    public void removeDelegate(@LayoutRes int itemLayoutId) {
        mDelegateManager.removeDelegate(itemLayoutId);
    }

    /*================ Builder ================*/

    public static class Builder<T> {
        private final RecyclerViewAdapter<T> mAdapter;
        private final RecyclerView mRecyclerView;

        public Builder(RecyclerView recyclerView) {
            mAdapter = new RecyclerViewAdapter<T>(recyclerView);
            this.mRecyclerView = recyclerView;
        }

        public Builder<T> addDelegate(RvItemViewDelegate<T> delegate) {
            mAdapter.addDelegate(delegate);
            return this;
        }

        public Builder<T> removeDelegate(RvItemViewDelegate<T> delegate) {
            mAdapter.removeDelegate(delegate);
            return this;
        }

        public Builder<T> setData(List<T> data) {
            mAdapter.setData(data);
            return this;
        }

        public Builder<T> addHeaderView(View headerView) {
            mAdapter.addHeaderView(headerView);
            return this;
        }

        public Builder<T> addFooterView(View footerView) {
            mAdapter.addFooterView(footerView);
            return this;
        }

        /**
         * <P>设置是否允许ItemTouch</P>
         * <P>可以通过 @link Builder#setItemTouchMode(int)} 设置ItemTouch的类型</P>
         * <P>可以通过 {@link Builder#setItemTouchHelper(BaseItemTouchHelper)} 设置拓展ItemTouch的Help类</P>
         *
         * @param enable true-false
         */
        public Builder<T> setItemTouchEnable(boolean enable) {
            return this;
        }

        /**
         * <P> 设置ItemTouch的类型</P>
         * <P> 使用前必须先设置 {@link Builder#setItemTouchEnable(boolean)}</P>
         * <P>可以通过{@link Builder#setItemTouchHelper(BaseItemTouchHelper)} 设置拓展ItemTouch的Help类</P>
         *
         * @param model ItemTouch的模式
         */
        public Builder<T> setItemTouchMode(int model) {
            mAdapter.setItemTouch(model);
            return this;
        }

        /**
         * <P>设置拓展ItemTouch的Help类</P>
         * <P>使用前必须先设置 {@link Builder#setItemTouchEnable(boolean)} 为true </P>
         * <P>可以通过 {@link Builder#setItemTouchMode(int)} 设置ItemTouch的类型 默认为 BaseItemHelper.DEFAULT_MODEL</P>
         *
         * @param itemTouchHelper itemtouch的help类  继承自{@linkplain BaseItemTouchHelper}
         */
        public Builder<T> setItemTouchHelper(BaseItemTouchHelper itemTouchHelper) {
            return this;
        }

        public RecyclerViewAdapter<T> build() {
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            return mAdapter;
        }

        /*=== set listener ===*/
        public Builder<T> setOnRVItemClickListener(OnRvItemClickListener onRVItemClickListener) {
            mAdapter.setOnRVItemClickListener(onRVItemClickListener);
            return this;
        }

        public Builder<T> setOnRVItemLongClickListener(OnRvItemLongClickListener onRVItemLongClickListener) {
            mAdapter.setOnRVItemLongClickListener(onRVItemLongClickListener);
            return this;
        }

        public Builder<T> setOnItemChildClickListener(OnRvItemChildClickListener onItemChildClickListener) {
            mAdapter.setOnItemChildClickListener(onItemChildClickListener);
            return this;
        }

        public Builder<T> setOnItemChildLongClickListener(OnRvItemChildLongClickListener onItemChildLongClickListener) {
            mAdapter.setOnItemChildLongClickListener(onItemChildLongClickListener);
            return this;
        }

        public Builder<T> setOnItemChildCheckedChangeListener(OnRvItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
            mAdapter.setOnItemChildCheckedChangeListener(onItemChildCheckedChangeListener);
            return this;
        }

        public Builder<T> setOnRVItemChildTouchListener(OnRvItemChildTouchListener onRVItemChildTouchListener) {
            mAdapter.setOnRVItemChildTouchListener(onRVItemChildTouchListener);
            return this;
        }

        public Builder<T> setOnItemTouchCallBack(RvItemTouchHelper.OnItemDragAndSwipeListener onItemDragAndSwipeListener) {
            mAdapter.setItemTouchListener(onItemDragAndSwipeListener);
            return this;
        }
    }
}
