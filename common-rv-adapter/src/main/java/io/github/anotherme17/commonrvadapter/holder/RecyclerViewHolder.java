package io.github.anotherme17.commonrvadapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;
import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;
import io.github.anotherme17.commonrvadapter.listener.OnNoDoubleClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemLongClickListener;

/**
 * @author anotherme17
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;

    private RvHolderHelper mHelper;

    /*=== listener ===*/
    private OnRvItemClickListener mOnRvItemClickListener;
    private OnRvItemLongClickListener mOnRvItemLongClickListener;

    public RecyclerViewHolder(View emptyView) {
        super(emptyView);
    }

    public RecyclerViewHolder(View itemView, RecyclerView recyclerView,
                              RecyclerViewAdapter recyclerViewAdapter,
                              OnRvItemClickListener onRvItemClickListener,
                              OnRvItemLongClickListener onRvItemLongClickListener) {
        super(itemView);

        this.mRecyclerView = recyclerView;
        this.mAdapter = recyclerViewAdapter;
        this.mOnRvItemClickListener = onRvItemClickListener;
        this.mOnRvItemLongClickListener = onRvItemLongClickListener;

        /*=== init helper ===*/
        mHelper = new RvHolderHelper(mRecyclerView, this);

        /*=== set listener ===*/
        itemView.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (v.getId() == RecyclerViewHolder.this.itemView.getId() && mOnRvItemClickListener != null) {
                    mOnRvItemClickListener.onRvItemClick(mRecyclerView, v, getAdapterPositionWapper());
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (v.getId() == RecyclerViewHolder.this.itemView.getId() && mOnRvItemLongClickListener != null) {
                    return mOnRvItemLongClickListener.onRvItemLongClick(mRecyclerView, v, getAdapterPositionWapper());
                }
                return false;
            }
        });
    }

    public RvHolderHelper getRvHolderHelper() {
        return mHelper;
    }

    public int getAdapterPositionWapper() {
        if (mAdapter.getHeadersCount() > 0) {
            return getAdapterPosition() - mAdapter.getHeadersCount();
        } else {
            return getAdapterPosition();
        }
    }
}
