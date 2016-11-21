package io.github.anotherme17.commonadapter;

import android.content.Context;

import java.util.List;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 14:55
 * 修改备注：
 */
public class MultiAdapter<T> extends MultiTypeAdapter<T> {
    private static final String TAG = "MultiAdapter";

    public MultiAdapter(Context context, List<T> datas, List<Integer> types) {
        super(context, datas, types);
    }

    public MultiAdapter<T> addViewDelegate(ItemViewDelegate<T> viewDelegate, int viewType) {
        addItemViewDelegate(viewDelegate, viewType);
        return this;
    }
}
