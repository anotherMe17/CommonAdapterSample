package io.github.anotherme17.commonadaptersample.delegate;

import android.content.Context;

import com.bumptech.glide.Glide;

import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.model.NormalModel;
import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.helper.RvHolderHelper;

/**
 * Created by user798 on 2016/12/29.
 */

public class NormalDelegate implements RvItemViewDelegate<NormalModel> {


    @Override
    public int getItemLayoutId() {
        return R.layout.item_normal;
    }

    @Override
    public boolean isDelegate(int position, NormalModel itemData) {
        if (itemData.title.equals("title2") || itemData.title.equals("title7")) {
            return false;
        }
        return true;
    }

    @Override
    public void onViewHolderCreated(Context context, RvHolderHelper rvHolderHelper) {

    }

    @Override
    public void setItemChildListener(RvHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildCheckedChangeListener(R.id.cb_item_normal_status);
       // helper.setItemChildTouchListener(R.id.iv_item_normal_avatar);
    }

    @Override
    public void convert(Context context, RvHolderHelper helper, int position, NormalModel model) {
        Glide.with(context).load(model.avatorPath).placeholder(R.drawable.holder_avatar).error(R.drawable.holder_avatar).into(helper.getImageView(R.id.iv_item_normal_avatar));
        helper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);

        helper.setChecked(R.id.cb_item_normal_status, model.selected);
    }
}
