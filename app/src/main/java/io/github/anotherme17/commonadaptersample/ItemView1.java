package io.github.anotherme17.commonadaptersample;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;

import io.github.anotherme17.commonadapter.ItemViewDelegate;
import io.github.anotherme17.commonadapter.ViewHolder;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 15:46
 * 修改备注：
 */
public class ItemView1 implements ItemViewDelegate<String> {
    private static final String TAG = "ItemView1";

    public static final int TYPE = 1;

    @Override
    public int getItemLayoutId() {
        return R.layout.item1;
    }

    @Override
    public boolean isForViewType(String data, int position, int indexViewType) {
        return TYPE == indexViewType;
    }

    @Override
    public void convert(Context context, ViewHolder viewHolder, final String data, final int position) {
        Button button = viewHolder.getView(R.id.item1_btn);
        button.setText(data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.v("TEST", "Item data = " + data + " position = " + position + " is clicked");
            }
        });
    }
}
