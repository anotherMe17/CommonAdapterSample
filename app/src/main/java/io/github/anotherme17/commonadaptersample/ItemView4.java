package io.github.anotherme17.commonadaptersample;

import android.content.Context;
import android.view.View;

import io.github.anotherme17.commonrvadapter.RvItemViewDelegate;
import io.github.anotherme17.commonrvadapter.RvViewHolder;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/24 16:48
 * 修改备注：
 */
public class ItemView4 implements RvItemViewDelegate<String> {
    private static final String TAG = "Item4";

    public static final int TYPE = 9;

    @Override
    public int getItemLayoutId() {
        return R.layout.item4;
    }

    @Override
    public int getItemViewId() {
        return TYPE;
    }

    @Override
    public void onViewHolderCreated(Context context, View view) {

    }

    @Override
    public void convert(Context context, RvViewHolder rvViewHolder, String data, int position) {
        rvViewHolder.setText(R.id.item4_tv, data);
    }
}
