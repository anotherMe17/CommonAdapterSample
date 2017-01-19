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
    protected final int defaultDragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    protected final int defaultSwipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

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
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
