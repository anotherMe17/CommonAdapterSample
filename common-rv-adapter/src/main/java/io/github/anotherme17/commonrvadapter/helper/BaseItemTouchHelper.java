package io.github.anotherme17.commonrvadapter.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import io.github.anotherme17.commonrvadapter.Constants;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;


/**
 * ItemTouch的帮助类
 *
 * @author anotherme17
 */
public class BaseItemTouchHelper extends ItemTouchHelper.Callback {
    /*================ final 参数================*/
    /**
     * 默认的ItemTouch模式
     */
    public static final int DEFAULT_MODEL = Constants.SWIP_ENABLE | Constants.DRAG_ENABLE;
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

    public BaseItemTouchHelper(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
        mItemTouchHelper = new ItemTouchHelper(this);
    }

    protected void setItemTouchMode(int model) {
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

        mAdapter.onItemDragMoved(viewHolder, target);

        if (mItemTouchHelper != null)
            mOnItemDragListener.onItemMoved(recyclerView, viewHolder, target);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.removeItem(viewHolder);

        if (mOnItemSwipedListener != null)
            mOnItemSwipedListener.onItemSwiped(viewHolder, direction);
    }

    protected void attacth2RecycleView(RecyclerView recyclerView) {
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    protected void startDrag(RecyclerView.ViewHolder holder) {
        mItemTouchHelper.startDrag(holder);
    }

    protected void startSwiped(RecyclerView.ViewHolder holder) {
        mItemTouchHelper.startSwipe(holder);
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
