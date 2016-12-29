package io.github.anotherme17.commonrvadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;

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

    public abstract boolean isDelegate(int position, T itemData);

    public abstract void onViewHolderCreated(Context context, RvHolderHelper rvHolderHelper);

    public abstract void setItemChildListener(RvHolderHelper helper, int viewType);

    public abstract void convert(Context context, RvHolderHelper rvHolderHelper, int position, T itemData);
}
