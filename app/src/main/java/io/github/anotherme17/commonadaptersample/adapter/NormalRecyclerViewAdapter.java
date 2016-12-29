package io.github.anotherme17.commonadaptersample.adapter;

import android.support.v7.widget.RecyclerView;

import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.model.NormalModel;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;

/**
 * Created by user798 on 2016/12/29.
 */

public class NormalRecyclerViewAdapter extends RecyclerViewAdapter<NormalModel> {


    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_normal);
    }

  /*  @Override
    protected void setItemChildListener(RvHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildLongClickListener(R.id.tv_item_normal_delete);
        helper.setItemChildCheckedChangeListener(R.id.cb_item_normal_status);
        helper.setOnItemChildTouchListener(R.id.iv_item_normal_avatar);
    }

    @Override
    public void fillData(RvHolderHelper helper, int position, NormalModel model) {
        Glide.with(mContext).load(model.avatorPath).placeholder(R.drawable.holder_avatar).error(R.drawable.holder_avatar).into(helper.getImageView(R.id.iv_item_normal_avatar));
        helper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);

        helper.setChecked(R.id.cb_item_normal_status, model.selected);
    }*/
}
