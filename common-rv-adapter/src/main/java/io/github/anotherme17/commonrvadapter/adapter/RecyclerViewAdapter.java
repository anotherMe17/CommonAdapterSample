package io.github.anotherme17.commonrvadapter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.anotherme17.commonrvadapter.R;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.helper.BaseItemTouchHelper;
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
 * 万能RecyclerView的适配器
 *
 * @author anotherme17
 * @version 1.0.0
 */
public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private int mDefaultItemId = 0;

    protected Context mContext;

    private List<T> mData;

    private RecyclerView mRecyclerView;

    private RvDelegateManager<T> mDelegateManager;

    private RvHeadAndFootAdapter mHeadAndFootAdapter;

    private boolean mIsIgnoreCheckedChanged = true;

    protected boolean enableDragAndSwip = false;
    /*=== empty ===*/
    private boolean ifShowEmptyView = false;
    private FrameLayout mEmptyLayout;

    /*=== helper ===*/
    private BaseItemTouchHelper mTouchHelper = null;

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
        if (viewType == R.id.RvEmptyView_Type_Id)
            return new RecyclerViewHolder(mEmptyLayout);

        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false),
                mRecyclerView, this, mOnRvItemClickListener, mOnRvItemLongClickListener);
        holder.getRvHolderHelper().setOnItemChildCheckedChangeListener(mOnRvItemChildCheckedChangeListener);
        holder.getRvHolderHelper().setOnItemChildClickListener(mOnRvItemChildClickListener);
        holder.getRvHolderHelper().setOnItemChildLongClickListener(mOnRvItemChildLongClickListener);
        holder.getRvHolderHelper().setOnItemChildTouchListener(mOnRvItemChildTouchListener);

        RvItemViewDelegate delegate = mDelegateManager.getDelegateByViewType(viewType);
        delegate.onViewHolderCreated(mContext, holder.getRvHolderHelper());
        delegate.setItemChildListener(holder.getRvHolderHelper(), viewType);

        holder.getRvHolderHelper().setOnItemDragCallback(new OnItemDragCallback() {
            @Override
            public void setDragView(RecyclerViewHolder holder) {
                if (mTouchHelper != null && enableDragAndSwip)
                    mTouchHelper.startDrag(holder);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (holder.getItemViewType() == R.id.RvEmptyView_Type_Id)
            return;

        // 在设置值的过程中忽略选中状态变化
        mIsIgnoreCheckedChanged = true;

        mDelegateManager.getDelegateByViewType(holder.getItemViewType()).convert(mContext, holder.getRvHolderHelper(), position, getItem(position));

        mIsIgnoreCheckedChanged = false;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == R.id.RvEmptyView_Type_Id) {
            setFullSpan(holder);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == R.id.RvEmptyView_Type_Id)
                        return gridLayoutManager.getSpanCount();
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position - getHeadersCount());
                    }
                    return 1;
                }
            });
        }
    }

    /**
     * When set to true, the item will layout using all span area. That means, if orientation
     * is vertical, the view will have full width; if orientation is horizontal, the view will
     * have full height.
     * if the hold view use StaggeredGridLayoutManager they should using all span area
     *
     * @param holder True if this item should traverse all spans.
     */
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1)
            return R.id.RvEmptyView_Type_Id;
        return mDefaultItemId == 0 ? mDelegateManager.getItemViewType(position, mData.get(position)) : mDefaultItemId;
    }

    @Override
    public int getItemCount() {
        return getEmptyViewCount() == 1 ? 1 : mData.size();
    }

    /**
     * if show empty view will be return 1 or not will be return 0
     *
     * @return if show empty view will be return 1 or not will be return 0
     */
    public int getEmptyViewCount() {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() == 0)
            return 0;
        if (!ifShowEmptyView)
            return 0;
        if (mData.size() != 0)
            return 0;
        return 1;
    }

    public boolean isIgnoreCheckedChanged() {
        return mIsIgnoreCheckedChanged;
    }

    public void setIfShowEmptyView(boolean showEmptyView) {
        ifShowEmptyView = showEmptyView;
    }

    public void setEmptyView(View emptyView) {
        boolean insert = false;
        if (mEmptyLayout == null) {
            mEmptyLayout = new FrameLayout(emptyView.getContext());
            final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
            final ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            mEmptyLayout.setLayoutParams(layoutParams);
            insert = true;
        }
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(emptyView);
        ifShowEmptyView = true;
        if (insert) {
            if (getEmptyViewCount() == 1) {
                notifyItemInserted(0);
            }
        }
    }

    /**
     * 详情请见 {@link Builder#setItemTouchEnable(boolean)}
     *
     * @param enable
     */
    public void setItemTouchEnable(boolean enable) {
        if (mTouchHelper == null) {
            mTouchHelper = new BaseItemTouchHelper();
            mTouchHelper.setRvAdapter(this);
        }

        enableDragAndSwip = enable;
        mTouchHelper.attacth2RecycleView(enable ? mRecyclerView : null);
    }

    /**
     * 详情请见 {@link Builder#setItemTouchModel(int)}
     *
     * @param model
     */
    public void setItemTouchModel(int model) {
        if (mTouchHelper == null)
            throw new IllegalArgumentException("please use setItemTouchEnable(true) to create BaseItemTouchHelper first");
        mTouchHelper.setItemTouchMode(model);
    }

    public void setItemTouchDragFlag(int flag) {
        if (mTouchHelper == null)
            throw new IllegalArgumentException("please use setItemTouchEnable(true) to create BaseItemTouchHelper first");
        mTouchHelper.setDragFlag(flag);
    }

    public void setItemTouchSwipedFlag(int flag) {
        if (mTouchHelper == null)
            throw new IllegalArgumentException("please use setItemTouchEnable(true) to create BaseItemTouchHelper first");
        mTouchHelper.setSwipedFlag(flag);
    }

    /**
     * 详情请见 {@link Builder#setItemTouchHelper(BaseItemTouchHelper)}
     *
     * @param itemTouchHelper
     */
    public void setItemTouchHelper(BaseItemTouchHelper itemTouchHelper) {
        mTouchHelper = itemTouchHelper;
        mTouchHelper.setRvAdapter(this);
    }

    public void setItemDragListener(BaseItemTouchHelper.OnItemDragListener listener) {
        if (mTouchHelper == null)
            throw new IllegalArgumentException("please use setItemTouchEnable(true) to create BaseItemTouchHelper first");
        mTouchHelper.setOnItemDragListener(listener);
    }

    public void setItemSwipeListener(BaseItemTouchHelper.OnItemSwipedListener listener) {
        if (mTouchHelper == null)
            throw new IllegalArgumentException("please use setItemTouchEnable(true) to create BaseItemTouchHelper first");
        mTouchHelper.setOnItemSwipedListener(listener);
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
    public void itemMoved(RecyclerView.ViewHolder fromHolder, RecyclerView.ViewHolder toHolder) {
        int fromPosition = fromHolder.getAdapterPosition();
        int toPosition = toHolder.getAdapterPosition();

        int tFrom = fromPosition - mHeadAndFootAdapter.getHeadersCount();
        int tTo = toPosition - mHeadAndFootAdapter.getHeadersCount();

        if (tFrom < tTo) {
            for (int i = tFrom; i < tTo; i++) {
                Collections.swap(mData, i, i + 1);
            }
        } else {
            for (int i = tFrom; i > tTo; i--) {
                Collections.swap(mData, i, i - 1);
            }
        }

        mHeadAndFootAdapter.notifyItemMoved(fromPosition, toPosition);
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

        /**
         * 添加一种Item类型 必须实现 {@link RvItemViewDelegate} 的接口
         *
         * @param delegate Item {@link RvItemViewDelegate}
         */
        public Builder<T> addDelegate(RvItemViewDelegate<T> delegate) {
            mAdapter.addDelegate(delegate);
            return this;
        }

        public Builder<T> removeDelegate(RvItemViewDelegate<T> delegate) {
            mAdapter.removeDelegate(delegate);
            return this;
        }

        /**
         * 设置Data
         *
         * @param data Data
         */
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
         * <P>设置是否允许ItemTouch  <br/>
         * {@link BaseItemTouchHelper} 在此初始化
         * </P>
         * <P>可以通过 @link Builder#setItemTouchMode(int)} 设置ItemTouch的类型</P>
         * <P>可以通过 {@link Builder#setItemTouchHelper(BaseItemTouchHelper)} 设置拓展ItemTouch的Help类</P>
         *
         * @param enable true-false
         */
        public Builder<T> setItemTouchEnable(boolean enable) {
            mAdapter.setItemTouchEnable(enable);
            return this;
        }

        /**
         * <P> 设置ItemTouch的类型</P>
         * <p>
         * <li> {@link BaseItemTouchHelper#DRAG_ENABLE} 允许拖动</li>
         * <li> {@link BaseItemTouchHelper#SWIP_ENABLE} 允许滑动删除</li>
         * <li> {@link BaseItemTouchHelper#SAME_MOVE} 相同类型的Item才能互换位置</li>
         * </P>
         * <P> 使用前必须先设置 {@link Builder#setItemTouchEnable(boolean)}</P>
         * <P>可以通过{@link Builder#setItemTouchHelper(BaseItemTouchHelper)} 设置拓展ItemTouch的Help类</P>
         *
         * @param model ItemTouch的模式
         */
        public Builder<T> setItemTouchModel(int model) {
            mAdapter.setItemTouchModel(model);
            return this;
        }

        /**
         * <p>
         * 设置Item Drag 的方向  默认全选
         * <li> {@link android.support.v7.widget.helper.ItemTouchHelper#UP}</li>
         * <li> {@link android.support.v7.widget.helper.ItemTouchHelper#DOWN}</li>
         * <li> {@link android.support.v7.widget.helper.ItemTouchHelper#LEFT}</li>
         * <li> {@link android.support.v7.widget.helper.ItemTouchHelper#RIGHT}</li>
         * </P>
         *
         * @param flag flags
         */
        public Builder<T> setItemDragFlag(int flag) {
            mAdapter.setItemTouchDragFlag(flag);
            return this;
        }

        /**
         * <p>
         * 设置Item Swiped 的方向 默认全选
         * <li> {@link android.support.v7.widget.helper.ItemTouchHelper#START}</li>
         * <li> {@link android.support.v7.widget.helper.ItemTouchHelper#END}</li>
         * </P>
         *
         * @param flag flags
         */
        public Builder<T> setItemSwipFalg(int flag) {
            mAdapter.setItemTouchSwipedFlag(flag);
            return this;
        }

        /**
         * <P>设置拓展ItemTouch的Help类 设置后会清除原先的ItemTouchHelper</P>
         * <P>必须先设置 然后再设置 {@link Builder#setItemTouchEnable(boolean)} 为true </P>
         * <P>可以通过 {@link Builder#setItemTouchModel(int)} 设置ItemTouch的类型 默认为 BaseItemHelper.DEFAULT_MODEL</P>
         *
         * @param itemTouchHelper itemtouch的help类  继承自{@linkplain BaseItemTouchHelper}
         */
        public Builder<T> setItemTouchHelper(BaseItemTouchHelper itemTouchHelper) {
            mAdapter.setItemTouchHelper(itemTouchHelper);
            return this;
        }

        /**
         * 设置一个EmptyView
         *
         * @param emptyView EmptyView
         */
        public Builder<T> setEmptyView(View emptyView) {
            mAdapter.setEmptyView(emptyView);
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

        /**
         * 设置Item 拖动的监听
         *
         * @param listener Item 拖动的监听
         */
        public Builder<T> setItemDragListener(BaseItemTouchHelper.OnItemDragListener listener) {
            mAdapter.setItemDragListener(listener);
            return this;
        }

        /**
         * 设置Item 滑动删除的监听
         *
         * @param listener Item 滑动删除的监听
         */
        public Builder<T> setItemSwipListener(BaseItemTouchHelper.OnItemSwipedListener listener) {
            mAdapter.setItemSwipeListener(listener);
            return this;
        }
    }
}
