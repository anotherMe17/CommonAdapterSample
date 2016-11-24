package io.github.anotherme17.commonadaptersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.anotherme17.commonadapter.common.CommonAdapterUitl;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.act_main_listview)
    ListView mListview;
    @Bind(R.id.item_type_1)
    Button mItemType1;
    @Bind(R.id.item_type_2)
    Button mItemType2;
    @Bind(R.id.item_type_3)
    Button mItemType3;

    private int itemType = 0;

    private List<String> datas;
    private List<Integer> types;
    private CommonAdapterUitl<String> adapterUitl;

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
        datas.add("4");
        adapterUitl = new CommonAdapterUitl.Builder<String>()
                .createSignalAdapter(MainActivity.this, datas, new ItemView1())
                .load(mListview);
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
        types.add(ItemView1.TYPE);
        types.add(ItemView2.TYPE);
        types.add(ItemView1.TYPE);
        adapterUitl = new CommonAdapterUitl.Builder<String>()
                .createMultiAdapter(MainActivity.this, datas, types)
                .addItemDegelate(new ItemView1())
                .addItemDegelate(new ItemView2())
                .load(mListview);
    }

    private void setItemType3() {
        itemType = 3;
        datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        types = new ArrayList<>();
        types.add(ItemView1.TYPE);
        types.add(ItemView2.TYPE);
        types.add(ItemView1.TYPE);
        types.add(ItemView1.TYPE);
        types.add(ItemView2.TYPE);
        types.add(ItemView1.TYPE);
        adapterUitl = new CommonAdapterUitl.Builder<String>()
                .createMultiAdapter(MainActivity.this, datas, types)
                .addItemDegelate(new ItemView1())
                .addItemDegelate(new ItemView2())
                .load(mListview);
    }

    private void addItem1() {
        datas.add(2, "test");
        adapterUitl.notifyDataSetChanged();
        //adapterUitl.addSignalItem("test", 2);
    }

    private void addItem3() {
        datas.add(2,"test");
        types.add(2,ItemView2.TYPE);
        adapterUitl.notifyDataSetChanged();
        //adapterUitl.addMultiItem("Test", ItemView2.TYPE, 2);
    }

    @OnClick({R.id.item_type_1, R.id.item_type_2, R.id.item_type_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_type_1:
                if (itemType != 1)
                    setItemType1();
                else
                    addItem1();
                break;
            case R.id.item_type_2:
                setItemType2();
                break;
            case R.id.item_type_3:
                if (itemType != 3) {
                    setItemType3();
                } else {
                    addItem3();
                }
                break;
        }
    }
}
