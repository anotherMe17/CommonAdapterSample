package io.github.anotherme17.commonadaptersample.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.base.BaseActivity;
import io.github.anotherme17.commonadaptersample.delegate.StringDelegate;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;
import io.github.anotherme17.commonrvadapter.animation.ItemAnimator17;

/**
 * Created by Administrator on 2017/1/22.
 */

public class AnimationActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.act_normal_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.add_btn)
    Button mAddBtn;
    @Bind(R.id.del_btn)
    Button mDelBtn;
    @Bind(R.id.act_animation_rv)
    RecyclerView mRv;

    private RecyclerViewAdapter<String> mAdapter;
    private List<String> mDatas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animation;
    }

    @Override
    protected void init() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mDatas = getData();
        mAdapter = new RecyclerViewAdapter.Builder<String>(mRv)
                .addDelegate(new StringDelegate())
                .setData(mDatas)
                .setLoadAnimation(RecyclerViewAdapter.SLIDEIN_LEFT)
                .setFirstShowEnable(false)
                .build();
        //SlideScaleInOutRightItemAnimator animator17 = new SlideScaleInOutRightItemAnimator(mRv);
        ItemAnimator17 animator17=new ItemAnimator17(mRv);
        mRv.setItemAnimator(animator17);
    }

    private List<String> getData() {
        List<String> strings = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            strings.add("测试条目 ----------" + i);
        }
        return strings;
    }

    @OnClick(R.id.add_btn)
    public void addItem() {
        mAdapter.addItem(5, "Add Item");
    }

    @OnClick(R.id.del_btn)
    public void removeItem() {
        mAdapter.removeItem(5);
    }
}
