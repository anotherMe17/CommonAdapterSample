package io.github.anotherme17.commonadaptersample.ui;

import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
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
import io.github.anotherme17.commonadaptersample.delegate.ItemTouchDelegate;
import io.github.anotherme17.commonadaptersample.model.ItemTouchModel;
import io.github.anotherme17.commonadaptersample.ui.widget.Divider;
import io.github.anotherme17.commonadaptersample.util.SnackbarUtil;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;
import io.github.anotherme17.commonrvadapter.helper.BaseItemTouchHelper;
import io.github.anotherme17.commonrvadapter.helper.RvItemTouchHelper;

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

        RvItemTouchHelper helper = new RvItemTouchHelper();
        helper.setOnItemSwipingListener(new RvItemTouchHelper.OnItemSwipingListener() {
            @Override
            public void onItemSwiping(Canvas c, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                c.drawColor(ContextCompat.getColor(ItemTouchActivity.this, R.color.colorPrimary));
            }
        });

        mAdapter = new RecyclerViewAdapter.Builder<ItemTouchModel>(mRv)
                .addDelegate(new ItemTouchDelegate())
                .setData(mDatas)
                .setItemTouchHelper(helper)
                .setItemTouchEnable(true)
                .setItemTouchModel(BaseItemTouchHelper.DRAG_ENABLE | BaseItemTouchHelper.SWIP_ENABLE)
                .setItemSwipListener(new BaseItemTouchHelper.OnItemSwipedListener() {
                    @Override
                    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        show("Item Swiped");
                    }
                })
                .setItemDragListener(new BaseItemTouchHelper.OnItemDragListener() {
                    @Override
                    public void onItemMoved(RecyclerView recyclerView, RecyclerView.ViewHolder from, RecyclerView.ViewHolder to) {
                        show("Item Moved from " + from.getAdapterPosition() + " to " + to.getAdapterPosition());
                    }
                })
                .build();
    }

    private List<ItemTouchModel> getData() {
        List<ItemTouchModel> models = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            models.add(new ItemTouchModel("", "测试条目 ----------" + i));
        }
        return models;
    }

    private void show(String msg) {
        SnackbarUtil.show(mRv, msg);
    }

}
