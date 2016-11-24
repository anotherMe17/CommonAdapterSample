package io.github.anotherme17.commonrvadapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.github.anotherme17.commonrvadapter.RvBaseMultiAdapter;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/24 16:05
 * 修改备注：
 */
public class RvCommonAdapterUtil<T> extends RvBaseMultiAdapter<T> {
    private static final String TAG = "RvCommonAdapterUtil";

    public RvCommonAdapterUtil(Context context, List<T> datas) {
        super(context, datas);
    }

    public RvCommonAdapterUtil(Context context, List<T> datas, int[] typeIds) {
        super(context, datas, typeIds);
    }

    public RvCommonAdapterUtil(Context context, List<T> datas, List<Integer> typeIds) {
        super(context, datas, typeIds);
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
