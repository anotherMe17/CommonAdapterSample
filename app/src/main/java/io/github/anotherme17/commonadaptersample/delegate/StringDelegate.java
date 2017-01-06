package io.github.anotherme17.commonadaptersample.delegate;

import android.content.Context;

import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;

/**
 * Created by user798 on 2017/1/6.
 */

public  class StringDelegate implements RvItemViewDelegate<String> {


    @Override
    public int getItemLayoutId() {
        return R.layout.item_string;
    }

    @Override
    public boolean isDelegate(int position, String itemData) {
        return true;
    }

    @Override
    public void onViewHolderCreated(Context context, RvHolderHelper rvHolderHelper) {

    }

    @Override
    public void setItemChildListener(RvHolderHelper helper, int viewType) {

    }

    @Override
    public void convert(Context context, RvHolderHelper rvHolderHelper, int position, String itemData) {
        rvHolderHelper.setText(R.id.item_string_tv, itemData);
    }
}
