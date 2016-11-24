package io.github.anotherme17.commonadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 项目名称：RapidDevelopemt
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 9:32
 * 修改备注：
 */
public interface ItemViewDelegate<T> {

    public abstract
    @LayoutRes
    int getItemLayoutId();

    public abstract int getItemViewId();

    public abstract void onViewHolderCreated(Context context, View view, T data, int position);

    public abstract void convert(Context context, ViewHolder viewHolder, T data, int position);
}
