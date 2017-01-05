package io.github.anotherme17.commonadaptersample.delegate;

import android.content.Context;

import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.model.ActModel;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;

/**
 * Created by user798 on 2017/1/5.
 */

public class ActDelegate implements RvItemViewDelegate<ActModel> {

    @Override
    public int getItemLayoutId() {
        return R.layout.item_act;
    }

    @Override
    public boolean isDelegate(int position, ActModel itemData) {
        return true;
    }

    @Override
    public void onViewHolderCreated(Context context, RvHolderHelper rvHolderHelper) {

    }

    @Override
    public void setItemChildListener(RvHolderHelper helper, int viewType) {

    }

    @Override
    public void convert(Context context, RvHolderHelper rvHolderHelper, int position, ActModel itemData) {
        rvHolderHelper.setText(R.id.item_act_txt, itemData.getTxt())
                .setImageDrawable(R.id.item_act_img, context.getResources().getDrawable(itemData.getImg()));
    }
}
