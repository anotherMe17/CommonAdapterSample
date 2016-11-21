package io.github.anotherme17.commonadaptersample;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.orhanobut.logger.Logger;

import io.github.anotherme17.commonadapter.ItemViewDelegate;
import io.github.anotherme17.commonadapter.ViewHolder;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 15:54
 * 修改备注：
 */
public class ItemView2 implements ItemViewDelegate<String> {
    private static final String TAG = "ItemView2";

    public static final int TYPE = 2;

    @Override
    public int getItemLayoutId() {
        return R.layout.item2;
    }

    @Override
    public boolean isForViewType(String data, int position, int indexViewType) {
        return TYPE == indexViewType;
    }

    @Override
    public void convert(Context context,ViewHolder viewHolder, final String data, final int position) {
        CheckBox checkBox = viewHolder.getView(R.id.item2_cb);
        checkBox.setText(data);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Logger.v("TEST", "Item data = " + data
                        + " position = " + position
                        + " check = " + b);
            }
        });
    }
}
