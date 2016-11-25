package io.github.anotherme17.commonadaptersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.anotherme17.commonrvadapter.RvBaseMultiAdapter;
import io.github.anotherme17.commonrvadapter.common.RvCommonAdapterUtil;
import io.github.anotherme17.commonrvadapter.wapper.LoadMoreWrapper;

/**
 * 项目名称：CommonAdapterSample
 * 类描述：
 * 创建人：renhao
 * 创建时间：2016/11/24 16:12
 * 修改备注：
 */
public class RvActivity extends AppCompatActivity {
    private static final String TAG = "RvActivity";
    @Bind(R.id.rv_item_type_1)
    Button mType1;
    @Bind(R.id.rv_item_type_2)
    Button mType2;
    @Bind(R.id.rv_item_type_3)
    Button mType3;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private int itemType = 0;

    private List<String> datas = new ArrayList<>();
    private List<Integer> types = new ArrayList<>();
    private RvCommonAdapterUtil<String> adapterUitl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        ButterKnife.bind(this);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        adapterUitl = new RvCommonAdapterUtil<String>(RvActivity.this, datas);
        ItemView1 itemView1 = new ItemView1();
        adapterUitl.addItemViewDegelate(itemView1);
        adapterUitl.setSignalMode(itemView1.getItemViewId());

        adapterUitl.initWraper(RvCommonAdapterUtil.EMPTY_VIEW | RvCommonAdapterUtil.HEAD_AND_FOOTER_VIEW | RvCommonAdapterUtil.LOAD_MORE_VIEW);

        TextView emptyView = new TextView(this);
        emptyView.setText("this is empty view");
        adapterUitl.setEmptyView(emptyView);
        TextView headerView = new TextView(this);
        headerView.setText("this is header view");
        adapterUitl.setHeaderView(headerView);
        TextView footerView = new TextView(this);
        footerView.setText("this is footer view");
        adapterUitl.setFooterView(footerView);
        TextView loadMoreView = new TextView(this);
        loadMoreView.setText("this is load more view");
        adapterUitl.setLoadMoreView(loadMoreView);

        adapterUitl.setLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Logger.v("TEST", "load more");
            }
        });

        adapterUitl.loadWrapper(mRecyclerview);
    }

    private void setItemType1() {
        itemType = 1;
        datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        adapterUitl = new RvCommonAdapterUtil.Builder<String>()
                .createSignalAdapter(RvActivity.this, datas, new ItemView1())
                .load(mRecyclerview);
        mRecyclerview.setAdapter(adapterUitl);
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
        adapterUitl = new RvCommonAdapterUtil.Builder<String>()
                .createMultiAdapter(RvActivity.this, datas, types)
                .addItemDegelate(new ItemView1())
                .addItemDegelate(new ItemView2())
                .load(mRecyclerview);
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
        adapterUitl = new RvCommonAdapterUtil.Builder<String>()
                .createMultiAdapter(RvActivity.this, datas, types)
                .addItemDegelate(new ItemView1())
                .addItemDegelate(new ItemView2())
                .addItemDegelate(new ItemView3())
                .setOnItemClickListener(new RvBaseMultiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        Logger.v("TEST", "Item Click Position = " + position);
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                })
                .load(mRecyclerview);
    }

    private void addItem1() {
        datas.add(2, "test");
        adapterUitl.notifyDataSetChanged();
        //adapterUitl.addSignalItem("test", 2);
    }

    private void addItem3() {
     /*   datas.add(2, "test");
        types.add(2, ItemView2.TYPE);
        adapterUitl.notifyDataSetChanged();*/
        adapterUitl.addMultiItem("Test", ItemView3.TYPE, 2);
    }

    @OnClick({R.id.rv_item_type_1, R.id.rv_item_type_2, R.id.rv_item_type_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_item_type_1:
                if (itemType != 1)
                    setItemType1();
                else
                    addItem1();
                break;
            case R.id.rv_item_type_2:
                setItemType2();
                break;
            case R.id.rv_item_type_3:
                if (itemType != 3) {
                    setItemType3();
                } else {
                    addItem3();
                }
                break;
        }
    }
}
