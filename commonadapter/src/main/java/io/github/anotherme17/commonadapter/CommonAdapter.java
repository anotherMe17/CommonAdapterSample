package io.github.anotherme17.commonadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

/**
 * 项目名称：RapidDevelopemt
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 13:18
 * 修改备注：
 */
public abstract class CommonAdapter<T> extends MultiTypeAdapter<T> {
    private static final String TAG = "CommonAdapter";

    public CommonAdapter(Context context, List<T> datas, @LayoutRes final int layoutId) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T data, int position, int indexViewType) {
                return true;
            }

            @Override
            public void convert(Context context,ViewHolder viewHolder, T data, int position) {
                CommonAdapter.this.convert(context,viewHolder, data, position);
            }
        });
    }

    public abstract void convert(Context context,ViewHolder viewHolder, T data, int position);
}
