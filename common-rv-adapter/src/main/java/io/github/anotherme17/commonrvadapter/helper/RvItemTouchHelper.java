package io.github.anotherme17.commonrvadapter.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by user798 on 2017/1/5.
 */

public class RvItemTouchHelper extends BaseItemTouchHelper {

    public static final float ALPHA_FULL = 1.0f;

    float mMoveThreshold = 0.1f;
    float mSwipeThreshold = 0.7f;

    private OnItemSwipingListener mOnItemSwipingListener;

    public RvItemTouchHelper() {
        super();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && !mAdapter.isHeaderOrFooter(viewHolder)) {
            View itemView = viewHolder.itemView;
            float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
            ViewCompat.setAlpha(viewHolder.itemView, alpha);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (!mAdapter.isHeaderOrFooter(viewHolder)) {
            ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
            viewHolder.itemView.setSelected(false);
        }
    }

    @Override
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return mMoveThreshold;
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return mSwipeThreshold;
    }

    /**
     * Set the fraction that the user should move the View to be considered as swiped.
     * The fraction is calculated with respect to RecyclerView's bounds.
     * <p>
     * Default value is .5f, which means, to swipe a View, user must move the View at least
     * half of RecyclerView's width or height, depending on the swipe direction.
     *
     * @param swipeThreshold A float value that denotes the fraction of the View size. Default value
     *                       is .8f .
     */
    public void setSwipeThreshold(float swipeThreshold) {
        mSwipeThreshold = swipeThreshold;
    }


    /**
     * Set the fraction that the user should move the View to be considered as it is
     * dragged. After a view is moved this amount, ItemTouchHelper starts checking for Views
     * below it for a possible drop.
     *
     * @param moveThreshold A float value that denotes the fraction of the View size. Default value is
     *                      .1f .
     */
    public void setMoveThreshold(float moveThreshold) {
        mMoveThreshold = moveThreshold;
    }


    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE
                && !mAdapter.isHeaderOrFooter(viewHolder)) {
            View itemView = viewHolder.itemView;

            c.save();
            if (dX > 0) {
                c.clipRect(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + dX, itemView.getBottom());
                c.translate(itemView.getLeft(), itemView.getTop());
            } else {
                c.clipRect(itemView.getRight() + dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom());
                c.translate(itemView.getRight() + dX, itemView.getTop());
            }
            if (mOnItemSwipingListener != null)
                mOnItemSwipingListener.onItemSwiping(c, viewHolder, dX, dY, isCurrentlyActive);
            c.restore();
        }
    }

    public void setOnItemSwipingListener(OnItemSwipingListener listener) {
        this.mOnItemSwipingListener = listener;
    }

    public interface OnItemSwipingListener {
        void onItemSwiping(Canvas c, RecyclerView.ViewHolder viewHolder,
                           float dX, float dY, boolean isCurrentlyActive);
    }


}
