package io.github.anotherme17.commonadaptersample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.anotherme17.commonadapter.CommonAdapter;
import io.github.anotherme17.commonadapter.MultiAdapter;
import io.github.anotherme17.commonadapter.ViewHolder;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.act_main_listview)
    ListView mListview;
    @Bind(R.id.item_type_1)
    Button mItemType1;
    @Bind(R.id.item_type_2)
    Button mItemType2;
    @Bind(R.id.item_type_3)
    Button mItemType3;

    private int itemType = 1;

    private CommonAdapter<String> mAdapter;
    private MultiAdapter<String> mMultiAdapter;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    private void setItemType1() {
        itemType = 1;
        datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");

        mAdapter = new CommonAdapter<String>(MainActivity.this, datas, R.layout.item_normal) {
            @Override
            public void convert(Context context, ViewHolder viewHolder, final String data, final int position) {
                viewHolder.setText(R.id.item_normal_tv, data)
                        .setOnClickListener(R.id.item_normal_tv, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Logger.v("TEST", "Item data = " + data + " position = " + position + " is clicked");
                            }
                        });
            }
        };
        mListview.setAdapter(mAdapter);
    }

    private void setItemType2() {
        itemType = 2;
        datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        List<Integer> types = new ArrayList<>();
        types.add(ItemView1.TYPE);
        types.add(ItemView2.TYPE);
        types.add(ItemView1.TYPE);
        types.add(ItemView2.TYPE);

        mMultiAdapter = new MultiAdapter<>(MainActivity.this, datas, types)
                .addViewDelegate(new ItemView1(), ItemView1.TYPE)
                .addViewDelegate(new ItemView2(), ItemView2.TYPE);
        mListview.setAdapter(mMultiAdapter);

    }

    private void setItemType3() {
        itemType = 3;

        datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        datas.add("5");
        datas.add("6");
        List<Integer> types = new ArrayList<>();
        types.add(ItemView1.TYPE);
        types.add(ItemView2.TYPE);
        types.add(ItemView3.TYPE);
        types.add(ItemView1.TYPE);
        types.add(ItemView2.TYPE);
        types.add(ItemView1.TYPE);

        mMultiAdapter = new MultiAdapter<>(MainActivity.this, datas, types)
                .addViewDelegate(new ItemView1(), ItemView1.TYPE)
                .addViewDelegate(new ItemView2(), ItemView2.TYPE)
                .addViewDelegate(new ItemView3(), ItemView3.TYPE);
        mListview.setAdapter(mMultiAdapter);
    }

    private void addItem() {
        datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
    }

    @OnClick({R.id.item_type_1, R.id.item_type_2, R.id.item_type_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_type_1:
                setItemType1();
                break;
            case R.id.item_type_2:
                setItemType2();
                break;
            case R.id.item_type_3:
                if (itemType != 3) {
                    setItemType3();
                } else {
                    addItem();
                }
                break;
        }
    }
}
