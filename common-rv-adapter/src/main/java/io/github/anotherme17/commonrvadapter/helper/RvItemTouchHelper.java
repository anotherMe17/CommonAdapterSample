package io.github.anotherme17.commonrvadapter.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import io.github.anotherme17.commonrvadapter.Constants;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;

/**
 * Created by user798 on 2017/1/5.
 */

public class RvItemTouchHelper extends ItemTouchHelper.Callback {

    public static final float ALPHA_FULL = 1.0f;

    private boolean isLongPressDragEnabled = false;
    private boolean isItemViewSwipeEnabled = false;
    private boolean isSameMove = false;
    private int defaultDragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    private int defaultSwipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;


    private RecyclerViewAdapter mAdapter;

    private ItemTouchHelper mItemTouchHelper;

    private OnItemDragAndSwipeListener mOnItemDragAndSwipeListener;

    public RvItemTouchHelper(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;

        mItemTouchHelper = new ItemTouchHelper(this);
    }

    public void setModel(int model) {
        isLongPressDragEnabled = (model & Constants.DRAG_ENABLE) != 0;
        isItemViewSwipeEnabled = (model & Constants.SWIP_ENABLE) != 0;
        isSameMove = (model & Constants.SAME_MOVE) != 0;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isItemViewSwipeEnabled;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setSelected(true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = defaultDragFlags;
        int swipeFlags = defaultSwipeFlags;

        // 当加了 HeaderAndFooterAdapter 时需要加上下面的判断
        if (mAdapter.isHeaderOrFooter(viewHolder)) {
            dragFlags = swipeFlags = ItemTouchHelper.ACTION_STATE_IDLE;
        }

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (isSameMove) {
            if (viewHolder.getItemViewType() != target.getItemViewType()) {
                return false;
            }
        }
        if (mAdapter.isHeaderOrFooter(viewHolder) || mAdapter.isHeaderOrFooter(target))
            return false;
        if (mOnItemDragAndSwipeListener != null) {
            mOnItemDragAndSwipeListener.onItemMove(recyclerView, viewHolder, target);
        }
        mAdapter.moveItem(viewHolder, target);
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);

        if (mOnItemDragAndSwipeListener != null) {
            mOnItemDragAndSwipeListener.onItemMoved(recyclerView, viewHolder, target);
        }

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mOnItemDragAndSwipeListener != null) {
            mOnItemDragAndSwipeListener.onItemSwiped(viewHolder, direction);
        }
        mAdapter.removeItem(viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;
            float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
            ViewCompat.setAlpha(viewHolder.itemView, alpha);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
        viewHolder.itemView.setSelected(false);
    }

    public ItemTouchHelper getItemTouchHelper() {
        return mItemTouchHelper;
    }

    public void setOnItemDragAndSwipeListener(OnItemDragAndSwipeListener onItemDragAndSwipeListener) {
        this.mOnItemDragAndSwipeListener = onItemDragAndSwipeListener;
    }

    public void setDragFlags(int dragFlags) {
        this.defaultDragFlags = dragFlags;
    }

    public void setSwipeFlags(int swipeFlags) {
        this.defaultSwipeFlags = swipeFlags;
    }

    public interface OnItemDragAndSwipeListener {
        void onItemMove(RecyclerView recyclerView, RecyclerView.ViewHolder from, RecyclerView.ViewHolder to);

        void onItemMoved(RecyclerView recyclerView, RecyclerView.ViewHolder from, RecyclerView.ViewHolder to);

        void onItemSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }

}
