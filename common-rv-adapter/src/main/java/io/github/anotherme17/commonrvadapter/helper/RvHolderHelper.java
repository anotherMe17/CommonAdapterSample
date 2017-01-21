package io.github.anotherme17.commonrvadapter.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;
import io.github.anotherme17.commonrvadapter.holder.RecyclerViewHolder;
import io.github.anotherme17.commonrvadapter.listener.OnItemDragCallback;
import io.github.anotherme17.commonrvadapter.listener.OnNoDoubleClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildCheckedChangeListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildLongClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildTouchListener;

/**
 * Created by user798 on 2016/12/28.
 */

public class RvHolderHelper implements View.OnLongClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {

    private SparseArrayCompat<View> mViews;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private View mContentView;

    private RecyclerViewHolder mHolder;

    private int mPosition;

    private Object mObject;

    /*=== listener ===*/
    private OnRvItemChildClickListener mOnRvItemChildClickListener;
    private OnRvItemChildLongClickListener mOnRvItemChildLongClickListener;
    private OnRvItemChildCheckedChangeListener mOnRvItemChildCheckedChangeListener;
    private OnRvItemChildTouchListener mOnRvItemChildTouchListener;
    private OnItemDragCallback mOnItemDragCallback;

    public RvHolderHelper(RecyclerView recyclerView, RecyclerViewHolder recyclerViewHolder) {
        this.mRecyclerView = recyclerView;
        this.mHolder = recyclerViewHolder;

        mViews = new SparseArrayCompat<>();
        mContentView = mHolder.itemView;
        mContext = mContentView.getContext();
    }

    public RecyclerViewHolder getRecyclerViewHolder() {
        return mHolder;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public int getPosition() {
        if (mHolder != null) {
            return mHolder.getAdapterPositionWapper();
        }
        return mPosition;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置Item子控件点击事件
     *
     * @param onRvItemChildClickListener
     */
    public void setOnItemChildClickListener(OnRvItemChildClickListener onRvItemChildClickListener) {
        this.mOnRvItemChildClickListener = onRvItemChildClickListener;
    }

    /**
     * 为id为viewId的子控件设置点击事件
     *
     * @param viewId
     */
    public void setItemChildClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mOnRvItemChildClickListener != null && mRecyclerView != null) {
                        mOnRvItemChildClickListener.onRvItemChildClick(mRecyclerView, v, getPosition());
                    }
                }
            });
        }
    }

    /**
     * 设置Item子控件的长按点击事件
     *
     * @param onItemChildLOngClickListener
     */
    public void setOnItemChildLongClickListener(OnRvItemChildLongClickListener onItemChildLOngClickListener) {
        this.mOnRvItemChildLongClickListener = onItemChildLOngClickListener;
    }

    /**
     * 设置id为viewId的Item子控件的长按点击事件
     *
     * @param viewId
     */
    public void setItemChildLongClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(this);
        }
    }

    /**
     * 设置Item子控件的touch事件
     *
     * @param onItemChildTouchListener
     */
    public void setOnItemChildTouchListener(OnRvItemChildTouchListener onItemChildTouchListener) {
        this.mOnRvItemChildTouchListener = onItemChildTouchListener;
    }

    public void setOnItemDragCallback(OnItemDragCallback itemDragCallback) {
        this.mOnItemDragCallback = itemDragCallback;
    }

    /**
     * @param viewId
     */
    public void attacthTouchListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(this);
        }
    }


    /**
     * 设置Item子控件的状态改变监听事件
     *
     * @param onItemChildCheckedChangeListener
     */
    public void setOnItemChildCheckedChangeListener(OnRvItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        this.mOnRvItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    public void setItemChildCheckedChangeListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof CompoundButton) {
            ((CompoundButton) view).setOnCheckedChangeListener(this);
        }
    }


    @Override
    public boolean onLongClick(View v) {
        if (mRecyclerView != null && mOnRvItemChildLongClickListener != null) {
            return mOnRvItemChildLongClickListener.onRvItemChildLongClick(mRecyclerView, v, getPosition());
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mRecyclerView != null) {
            if (mOnItemDragCallback != null && MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                mOnItemDragCallback.setDragView(mHolder);

            return mOnRvItemChildTouchListener != null && mOnRvItemChildTouchListener.onRvItemChilcTouch(mHolder, v, event);
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mOnRvItemChildCheckedChangeListener != null) {
            if (mRecyclerView != null) {
                RecyclerViewAdapter recyclerViewAdapter;

                RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                recyclerViewAdapter = (RecyclerViewAdapter) adapter;
                if (!recyclerViewAdapter.isIgnoreCheckedChanged()) {
                    mOnRvItemChildCheckedChangeListener.onRvItemChildCheckedChanged(mRecyclerView, buttonView, getPosition(), isChecked);
                }
            }
        }
    }

    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    public TextView getTextView(@IdRes int viewId) {
        return getView(viewId);
    }

    public View getContentView() {
        return mContentView;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param text
     * @return
     */
    public RvHolderHelper setText(@IdRes int viewId, CharSequence text) {
        if (text == null) {
            text = "";
        }
        getTextView(viewId).setText(text);
        return this;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param stringResId 字符串资源id
     * @return
     */
    public RvHolderHelper setText(@IdRes int viewId, @StringRes int stringResId) {
        getTextView(viewId).setText(stringResId);
        return this;
    }

    /**
     * 设置对应id的控件的文字大小，单位为 sp
     *
     * @param viewId
     * @param size   文字大小，单位为 sp
     * @return
     */
    public RvHolderHelper setTextSizeSp(@IdRes int viewId, float size) {
        getTextView(viewId).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 设置对应id的控件的文字是否为粗体
     *
     * @param viewId
     * @param isBold 是否为粗体
     * @return
     */
    public RvHolderHelper setIsBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }

    /**
     * 设置对应id的控件的html文本内容
     *
     * @param viewId
     * @param source html文本
     * @return
     */
    public RvHolderHelper setHtml(@IdRes int viewId, String source) {
        if (source == null) {
            source = "";
        }
        getTextView(viewId).setText(Html.fromHtml(source));
        return this;
    }

    /**
     * 设置对应id的控件是否选中
     *
     * @param viewId
     * @param checked
     * @return
     */
    public RvHolderHelper setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public RvHolderHelper setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public RvHolderHelper setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public RvHolderHelper setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public RvHolderHelper setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public RvHolderHelper setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * @param viewId
     * @param textColorResId 颜色资源id
     * @return
     */
    public RvHolderHelper setTextColorRes(@IdRes int viewId, @ColorRes int textColorResId) {
        getTextView(viewId).setTextColor(mContext.getResources().getColor(textColorResId));
        return this;
    }

    /**
     * @param viewId
     * @param textColor 颜色值
     * @return
     */
    public RvHolderHelper setTextColor(@IdRes int viewId, int textColor) {
        getTextView(viewId).setTextColor(textColor);
        return this;
    }

    /**
     * @param viewId
     * @param backgroundResId 背景资源id
     * @return
     */
    public RvHolderHelper setBackgroundRes(@IdRes int viewId, int backgroundResId) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundResId);
        return this;
    }

    /**
     * @param viewId
     * @param color  颜色值
     * @return
     */
    public RvHolderHelper setBackgroundColor(@IdRes int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * @param viewId
     * @param colorResId 颜色值资源id
     * @return
     */
    public RvHolderHelper setBackgroundColorRes(@IdRes int viewId, @ColorRes int colorResId) {
        View view = getView(viewId);
        view.setBackgroundColor(mContext.getResources().getColor(colorResId));
        return this;
    }

    /**
     * @param viewId
     * @param imageResId 图像资源id
     * @return
     */
    public RvHolderHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * 设置字体是否为粗体
     *
     * @param viewId
     * @param isBold
     * @return
     */
    public RvHolderHelper setBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }

}
