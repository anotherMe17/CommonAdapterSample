package io.github.anotherme17.commonrvadapter;

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
public interface RvItemViewDelegate<T> {

    public abstract
    @LayoutRes
    int getItemLayoutId();

    public abstract int getItemViewId();

    public abstract void onViewHolderCreated(Context context, View view);

    public abstract void convert(Context context, RvViewHolder rvViewHolder, T data, int position);
}
