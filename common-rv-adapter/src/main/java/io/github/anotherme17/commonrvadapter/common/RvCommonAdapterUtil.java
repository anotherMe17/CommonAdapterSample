package io.github.anotherme17.commonrvadapter.common;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import io.github.anotherme17.commonrvadapter.RvBaseMultiAdapter;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.wapper.EmptyWrapper;
import io.github.anotherme17.commonrvadapter.wapper.HeaderAndFooterWrapper;
import io.github.anotherme17.commonrvadapter.wapper.LoadMoreWrapper;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/24 16:05
 * 修改备注：
 */
public class RvCommonAdapterUtil<T> extends RvBaseMultiAdapter<T> {
    private static final String TAG = "RvCommonAdapterUtil";

    public static final int NULL = 0;
    public static final int EMPTY_VIEW = 0x1;
    public static final int LOAD_MORE_VIEW = 0x2;
    public static final int HEAD_AND_FOOTER_VIEW = 0x4;
    private int mMode = NULL;

    private EmptyWrapper<T> mEmptyWrapper;
    private LoadMoreWrapper<T> mLoadMoreWrapper;
    private HeaderAndFooterWrapper<T> mHeaderAndFooterWrapper;

    private SparseArrayCompat<RecyclerView.Adapter<RecyclerView.ViewHolder>> mWrappers = new SparseArrayCompat<>();

    public RvCommonAdapterUtil(Context context, List<T> datas) {
        super(context, datas);
    }

    public RvCommonAdapterUtil(Context context, List<T> datas, int[] typeIds) {
        super(context, datas, typeIds);
    }

    public RvCommonAdapterUtil(Context context, List<T> datas, List<Integer> typeIds) {
        super(context, datas, typeIds);
    }

    public void initWraper(int mode) {
        this.mMode = mode;
        if ((mode & LOAD_MORE_VIEW) != 0) {
            mLoadMoreWrapper = new LoadMoreWrapper<>(this);
            mWrappers.put(LOAD_MORE_VIEW, mLoadMoreWrapper);
        }

        if ((mode & EMPTY_VIEW) != 0) {
            if (mWrappers.size() > 0)
                mEmptyWrapper = new EmptyWrapper<>(mLoadMoreWrapper);
            else
                mEmptyWrapper = new EmptyWrapper<>(this);
        }

        if ((mode & HEAD_AND_FOOTER_VIEW) != 0) {
            int wrappersSize = mWrappers.size();
            if (wrappersSize > 0)
                mHeaderAndFooterWrapper = new HeaderAndFooterWrapper<>(mWrappers.valueAt(mWrappers.size() - 1));
            else {
                mHeaderAndFooterWrapper = new HeaderAndFooterWrapper<>(this);
            }
        }
    }

    public void loadWrapper(RecyclerView recyclerView) {
        if ((mMode & HEAD_AND_FOOTER_VIEW) != 0) {
            recyclerView.setAdapter(mHeaderAndFooterWrapper);
            return;
        }
        if ((mMode & EMPTY_VIEW) != 0) {
            recyclerView.setAdapter(mEmptyWrapper);
            return;
        }

        if ((mMode & LOAD_MORE_VIEW) != 0) {
            recyclerView.setAdapter(mLoadMoreWrapper);
            return;
        }
        recyclerView.setAdapter(this);

    }

    public void setEmptyView(View emptyView) {
        mEmptyWrapper.setEmptyView(emptyView);
    }

    public void setEmptyView(int layoutId) {
        mEmptyWrapper.setEmptyView(layoutId);
    }

    public void setLoadMoreView(View loadMoreView) {
        mLoadMoreWrapper.setLoadMoreView(loadMoreView);
    }

    public void setLoadMoreView(int layoutId) {
        mLoadMoreWrapper.setLoadMoreView(layoutId);
    }

    public void setLoadMoreListener(LoadMoreWrapper.OnLoadMoreListener loadMoreListener) {
        mLoadMoreWrapper.setOnLoadMoreListener(loadMoreListener);
    }

    public void setHeaderView(View view) {
        mHeaderAndFooterWrapper.setHeaderView(view);
    }

    public void setFooterView(View view) {
        mHeaderAndFooterWrapper.setFooterView(view);
    }

    public static class Builder<T> {

        private RvCommonAdapterUtil<T> mAdapter;

        public Builder<T> createSignalAdapter(Context context, List<T> datas, RvItemViewDelegate<T> viewDelegate) {
            mAdapter = new RvCommonAdapterUtil<T>(context, datas);
            mAdapter.addItemViewDegelate(viewDelegate);
            mAdapter.setSignalMode(viewDelegate.getItemViewId());
            return this;
        }

        public Builder<T> createMultiAdapter(Context context, List<T> datas, List<Integer> typeIds) {
            mAdapter = new RvCommonAdapterUtil<T>(context, datas, typeIds);
            mAdapter.setMultiMode();
            return this;
        }

        public Builder<T> addItemDegelate(RvItemViewDelegate<T> viewDelegate) {
            mAdapter.addItemViewDegelate(viewDelegate);
            return this;
        }

        public Builder<T> setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mAdapter.setOnItemClickListener(onItemClickListener);
            return this;
        }

        public RvCommonAdapterUtil<T> load(RecyclerView recyclerView) {
            recyclerView.setAdapter(mAdapter);
            return mAdapter;
        }
    }
}
