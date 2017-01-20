package io.github.anotherme17.commonrvadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;

/**
 * 实现Item的接口
 *
 * @author anotherme17
 * @version 1.0.0
 */
public interface RvItemViewDelegate<T> {

    /**
     * 设置Item的LayoutId
     */
    public abstract
    @LayoutRes
    int getItemLayoutId();

    /**
     * <P>通过 {@code position} 和 {@code itemData} 判断是否为该位置的Item <br/>
     * 如果返回true 则将依次调用以下接口  <br/>
     * {@link RvItemViewDelegate#onViewHolderCreated(Context, RvHolderHelper)} <br/>
     * {@link RvItemViewDelegate#setItemChildListener(RvHolderHelper, int)}<br/>
     * {@link RvItemViewDelegate#convert(Context, RvHolderHelper, int, Object)}<br/>
     * </P>
     *
     * @param position Item的位置
     * @param itemData Item的内容
     * @return {@code true} - 是该位置的Item <br/>
     * <code> false</code> - 不是该位置的Item
     */
    public abstract boolean isDelegate(int position, T itemData);

    /**
     * Item被创建时调用
     *
     * @param context {@link Context} 上下文
     * @param helper  {@link RvHolderHelper}
     */
    public abstract void onViewHolderCreated(Context context, RvHolderHelper helper);

    /**
     * 设置Item内部ChildView 的监听 在 {@link io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter#onCreateViewHolder(ViewGroup, int)} 中调用
     *
     * @param helper   {@link RvHolderHelper}
     * @param viewType Item 的 layoutId
     */
    public abstract void setItemChildListener(RvHolderHelper helper, int viewType);

    /**
     * 在此为Item里的View 赋值
     *
     * @param context  {@link Context} 上下文
     * @param helper   {@link RvHolderHelper}
     * @param position Item的位置 不包括头和尾
     * @param itemData Item的内容
     */
    public abstract void convert(Context context, RvHolderHelper helper, int position, T itemData);
}
