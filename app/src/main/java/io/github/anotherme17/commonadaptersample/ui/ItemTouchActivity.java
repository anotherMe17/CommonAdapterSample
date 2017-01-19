package io.github.anotherme17.commonadaptersample.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.base.BaseActivity;
import io.github.anotherme17.commonadaptersample.model.ItemTouchModel;
import io.github.anotherme17.commonadaptersample.ui.widget.Divider;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;

/**
 * Created by Administrator on 2017/1/19.
 */

public class ItemTouchActivity extends BaseActivity {


    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.act_normal_toolbar)
    Toolbar mActNormalToolbar;
    @Bind(R.id.act_normal_rv)
    RecyclerView mRv;

    private List<ItemTouchModel> mDatas;

    private RecyclerViewAdapter<ItemTouchModel> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_rv;
    }

    @Override
    protected void init() {
        mTitle.setText("ItemTouchActivity");

        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new Divider(this));

        mDatas = getData();

       /* mAdapter = new RecyclerViewAdapter.Builder<ItemTouchModel>(mRv)
                .addDelegate(new ItemTouchDelegate())
                .setData(mDatas)
                .setItemTouchHelper(Constants.SWIP_ENABLE | Constants.DRAG_ENABLE)
                .build();*/
    }

    private List<ItemTouchModel> getData() {
        List<ItemTouchModel> models = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            models.add(new ItemTouchModel("", "测试条目 ----------" + i));
        }
        return models;
    }

}
