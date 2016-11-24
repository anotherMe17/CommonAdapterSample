package io.github.anotherme17.commonadapter.common;

import android.content.Context;
import android.widget.ListView;

import java.util.List;

import io.github.anotherme17.commonadapter.BaseMultiAdapter;
import io.github.anotherme17.commonadapter.ItemViewDelegate;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 14:55
 * 修改备注：
 */
public class CommonAdapterUitl<T> extends BaseMultiAdapter<T> {

    public CommonAdapterUitl(Context context, List<T> datas) {
        super(context, datas);
    }

    public CommonAdapterUitl(Context context, List<T> datas, int[] typeIds) {
        super(context, datas, typeIds);
    }

    public CommonAdapterUitl(Context context, List<T> datas, List<Integer> typeIds) {
        super(context, datas, typeIds);
    }

    public static class Builder<T> {
        private CommonAdapterUitl<T> mAdapter;

        public Builder<T> createSignalAdapter(Context context, List<T> datas, ItemViewDelegate<T> viewDelegate) {
            mAdapter = new CommonAdapterUitl<T>(context, datas);
            mAdapter.addItemViewDegelate(viewDelegate);
            mAdapter.setSignalMode(viewDelegate.getItemViewId());
            return this;
        }

        public Builder<T> createMultiAdapter(Context context, List<T> datas, List<Integer> typeIds) {
            mAdapter = new CommonAdapterUitl<T>(context, datas, typeIds);
            mAdapter.setMultiMode();
            return this;
        }

        public Builder<T> addItemDegelate(ItemViewDelegate<T> viewDelegate) {
            mAdapter.addItemViewDegelate(viewDelegate);
            return this;
        }

        public CommonAdapterUitl<T> load(ListView listView) {
            listView.setAdapter(mAdapter);
            return mAdapter;
        }
    }
}
