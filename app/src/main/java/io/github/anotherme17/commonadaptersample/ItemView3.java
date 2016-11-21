package io.github.anotherme17.commonadaptersample;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import io.github.anotherme17.commonadapter.ItemViewDelegate;
import io.github.anotherme17.commonadapter.ViewHolder;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/21 16:55
 * 修改备注：
 */
public class ItemView3 implements ItemViewDelegate<String> {
    private static final String TAG = "ItemView3";

    public static final int TYPE = 3;

    @Override
    public int getItemLayoutId() {
        return R.layout.item3;
    }

    @Override
    public boolean isForViewType(String data, int position, int viewType) {
        return TYPE == viewType;
    }

    @Override
    public void convert(Context context, ViewHolder viewHolder, final String data, final int position) {
        final EditText editText = viewHolder.getView(R.id.item3_et);
        Button submit = viewHolder.getView(R.id.item3_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.v("TEST", "Item data = " + data + " position = " + position + " is clicked "
                        + "message = " + editText.getText().toString());
            }
        });
    }
}
