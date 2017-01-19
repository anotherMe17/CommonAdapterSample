package io.github.anotherme17.commonadaptersample.delegate;

import android.content.Context;

import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.model.ItemTouchModel;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;

/**
 * Created by Administrator on 2017/1/19.
 */

public class ItemTouchDelegate implements RvItemViewDelegate<ItemTouchModel> {

    @Override
    public int getItemLayoutId() {
        return R.layout.item_touch;
    }

    @Override
    public boolean isDelegate(int position, ItemTouchModel itemData) {
        return true;
    }

    @Override
    public void onViewHolderCreated(Context context, RvHolderHelper rvHolderHelper) {

    }

    @Override
    public void setItemChildListener(RvHolderHelper helper, int viewType) {
        helper.setItemChildTouchListener(R.id.item_touch_img);
    }

    @Override
    public void convert(Context context, RvHolderHelper rvHolderHelper, int position, ItemTouchModel itemData) {
        rvHolderHelper.setText(R.id.item_touch_msg, itemData.getMessage());
    }
}
