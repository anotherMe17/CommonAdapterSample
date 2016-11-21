package io.github.anotherme17.commonadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

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

    public abstract boolean isForViewType(T data, int position, int viewType);

    public abstract void convert(Context context,ViewHolder viewHolder, T data, int position);
}
