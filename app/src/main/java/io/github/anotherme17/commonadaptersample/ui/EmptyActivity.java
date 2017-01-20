package io.github.anotherme17.commonadaptersample.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.base.BaseActivity;
import io.github.anotherme17.commonadaptersample.delegate.StringDelegate;
import io.github.anotherme17.commonadaptersample.ui.widget.Divider;
import io.github.anotherme17.commonadaptersample.util.SnackbarUtil;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;

/**
 * Created by Administrator on 2017/1/20.
 */

public class EmptyActivity extends BaseActivity {


    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.act_normal_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.act_normal_rv)
    RecyclerView mRv;

    private RecyclerViewAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_rv;
    }

    @Override
    protected void init() {
        mTitle.setText("NormalRecyclerView");

        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new Divider(this));

        View emptyView = getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) mRv.getParent(), false);
        TextView etv = (TextView) emptyView.findViewById(R.id.empty_view);
        etv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackbarUtil.show(mRv, "click empty view");
                mAdapter.setData(getData());
            }
        });
        mAdapter = new RecyclerViewAdapter.Builder<String>(mRv)
                .addDelegate(new StringDelegate())
                .setData(mDatas)
                .setEmptyView(emptyView)
                .build();
    }


    @OnClick(R.id.back)
    public void onClick() {
        finishAct();
    }

    private List<String> getData() {
        List<String> strings = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            strings.add("测试条目 ----------" + i);
        }
        return strings;
    }
}
