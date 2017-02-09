package io.github.anotherme17.commonrvadapter.helper;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;


/**
 * ItemTouch的帮助类
 *
 * @author anotherme17
 * @version 1.0.0
 */
public class BaseItemTouchHelper extends ItemTouchHelper.Callback {
    /*================ final 参数================*/

    /**
     * 允许拖动
     */
    public static final int DRAG_ENABLE = 0x01;
    /**
     * 允许滑动删除
     */
    public static final int SWIP_ENABLE = 0x02;
    /**
     * 当Item的类型相同时才允许互换位置
     */
    public static final int SAME_MOVE = 0x04;

    /**
     * 默认的ItemTouch模式
     */
    public static final int DEFAULT_MODEL = SWIP_ENABLE | DRAG_ENABLE;

    @IntDef({DRAG_ENABLE, SWIP_ENABLE, SAME_MOVE, DEFAULT_MODEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemTouchModel {

    }

    /*================ 默认的拖动和滑动的参数 ================*/
    protected int defaultDragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    protected int defaultSwipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

    /*================ 参数 ================*/

    /**
     * 是否允许长按拖动
     */
    private boolean isLongPressDragEnabled = false;
    /**
     * 是否允许滑动
     */
    private boolean isItemViewSwipeEnabled = false;
    /**
     * 是否在相同Item时才允许互换位置
     */
    private boolean isSameMove = false;

    /*================ 移动和滑动的监听 ================*/
    protected OnItemDragListener mOnItemDragListener;
    protected OnItemSwipedListener mOnItemSwipedListener;

    /*================ 在create时需要赋值和初始化的对象 ================*/
    protected ItemTouchHelper mItemTouchHelper;
    protected RecyclerViewAdapter mAdapter;

    /*================================ 分界线 ================================*/

    public BaseItemTouchHelper() {
        mItemTouchHelper = new ItemTouchHelper(this);
    }

    public void setRvAdapter(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void setItemTouchMode(int model) {
        isLongPressDragEnabled = (model & DRAG_ENABLE) != 0;
        isItemViewSwipeEnabled = (model & SWIP_ENABLE) != 0;
        isSameMove = (model & SAME_MOVE) != 0;
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
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);

        mAdapter.itemMoved(target, viewHolder);

        if (mOnItemDragListener != null)
            mOnItemDragListener.onItemMoved(recyclerView, target, viewHolder);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.removeItem(viewHolder);

        if (mOnItemSwipedListener != null)
            mOnItemSwipedListener.onItemSwiped(viewHolder, direction);
    }

    public void attacth2RecycleView(RecyclerView recyclerView) {
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void startDrag(RecyclerView.ViewHolder holder) {
        mItemTouchHelper.startDrag(holder);
    }

    public void startSwiped(RecyclerView.ViewHolder holder) {
        mItemTouchHelper.startSwipe(holder);
    }

    public void setDragFlag(int flag) {
        defaultDragFlags = flag;
    }

    public void setSwipedFlag(int flag) {
        defaultSwipeFlags = flag;
    }

    /*========== listener ==========*/

    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        mOnItemDragListener = onItemDragListener;
    }

    public void setOnItemSwipedListener(OnItemSwipedListener onItemSwipedListener) {
        mOnItemSwipedListener = onItemSwipedListener;
    }

    public interface OnItemDragListener {
        void onItemMoved(RecyclerView recyclerView, RecyclerView.ViewHolder from, RecyclerView.ViewHolder to);
    }

    public interface OnItemSwipedListener {
        void onItemSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }
}
