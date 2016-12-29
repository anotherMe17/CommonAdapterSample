package io.github.anotherme17.commonrvadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/24 14:32
 * 修改备注：
 */
public class RvBaseMultiAdapter<T> extends RecyclerView.Adapter<RvViewHolder> {
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
/*
    protected Context mContext;
    protected List<T> mDatas;

    protected RvItemViewManager<T> mViewManager;

    protected OnItemClickListener mOnItemClickListener;

    public RvBaseMultiAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mViewManager = new RvItemViewManager<>();
    }

    public RvBaseMultiAdapter(Context context, List<T> datas, List<Integer> typeIds) {
        this(context, datas);
        mViewManager.addMapTab(typeIds);
    }

    public RvBaseMultiAdapter(Context context, List<T> datas, int[] typeIds) {
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

    public void setSignalMode(int typeId) {
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

    public void addItemViewDegelate(RvItemViewDelegate<T> viewDelegate) {
        mViewManager.addDelegate(viewDelegate);
    }

    public int getItemViewDegelateSize() {
        return mViewManager.getDelegateCount();
    }

    public RvItemViewDelegate<T> getItemViewDegelate(int position) {
        return mViewManager.getItemViewDelegate(position);
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvItemViewDelegate<T> delegate = mViewManager.getItemViewDegelateByIndex(viewType);
        int layoutId = delegate.getItemLayoutId();
        RvViewHolder holder = RvViewHolder.createViewHolder(mContext, parent, layoutId);
        delegate.onViewHolderCreated(mContext, holder.getConvertView());
        setListener(parent, holder, mViewManager.getViewTypeId(viewType));
        return holder;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        RvItemViewDelegate<T> delegate = mViewManager.getItemViewDelegate(position);
        delegate.convert(mContext, holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewManager.getItemViewType(position);
    }

    protected void setListener(final ViewGroup parent, final RvViewHolder viewHolder, int viewType) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }*/
}
