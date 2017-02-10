package io.github.anotherme17.commonrvadapter.listener;

import io.github.anotherme17.commonrvadapter.holder.RecyclerViewHolder;

/**
 * 用于设置ItemDrag,对
 *
 * @author anotherme17
 */
public interface OnItemDragCallback {

    /**
     * 在对holder中的某一个View绑定监听后{@link io.github.anotherme17.commonrvadapter.helper.RvHolderHelper#attachItemDrag(int)}
     * 在该View被触发Touch事件后返回该Item的{@link RecyclerViewHolder},可使用{@link io.github.anotherme17.commonrvadapter.helper.RvItemTouchHelper} 设置拖动
     *
     * @param holder 被Touch的View所在的Item的holder
     */
    void setDragView(RecyclerViewHolder holder);

}
