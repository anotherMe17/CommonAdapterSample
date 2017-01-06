package io.github.anotherme17.commonadaptersample.delegate;

import android.content.Context;

import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.model.NormalModel;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;

/**
 * Created by user798 on 2016/12/29.
 */

public class TextViewDelegate implements RvItemViewDelegate<NormalModel> {


    @Override
    public int getItemLayoutId() {
        return R.layout.item1;
    }

    @Override
    public boolean isDelegate(int position, NormalModel itemData) {
        if (itemData.title.equals("title2") || itemData.title.equals("title7")) {
            return true;
        }
        return false;
    }

    @Override
    public void onViewHolderCreated(Context context, RvHolderHelper rvHolderHelper) {

    }

    @Override
    public void setItemChildListener(RvHolderHelper helper, int viewType) {
        helper.setItemChildTouchListener(R.id.item1_btn);
    }

    @Override
    public void convert(Context context, RvHolderHelper rvHolderHelper, int position, NormalModel itemData) {
        rvHolderHelper.setText(R.id.item1_tv, itemData.title);
    }
}
