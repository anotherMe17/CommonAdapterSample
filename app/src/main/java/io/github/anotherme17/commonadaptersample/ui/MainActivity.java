package io.github.anotherme17.commonadaptersample.ui;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.base.BaseActivity;
import io.github.anotherme17.commonadaptersample.delegate.ActDelegate;
import io.github.anotherme17.commonadaptersample.model.ActModel;
import io.github.anotherme17.commonadaptersample.util.SnackbarUtil;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemClickListener;

/**
 * Created by user798 on 2017/1/5.
 */

public class MainActivity extends BaseActivity {

    @Bind(R.id.normal_rv)
    RecyclerView mNormalRv;

    private int[] img = new int[]{R.drawable.logo,
            R.drawable.logo, R.drawable.logo, R.drawable.logo};

    private String[] txt = new String[]{"Normal\nRecyclerView", "ItemTouch", "3", "4"};

    private Class[] mClasses = new Class[]{NormalRvActivity.class,ItemTouchActivity.class};

    private List<ActModel> mList = new ArrayList<>();

    private RecyclerViewAdapter<ActModel> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.normal_recyclerview;
    }

    @Override
    protected void init() {

        for (int i = 0; i < txt.length; i++) {
            ActModel model = new ActModel(txt[i], img[i]);
            mList.add(model);
        }

        mNormalRv.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new RecyclerViewAdapter.Builder<ActModel>(mNormalRv)
                .addDelegate(new ActDelegate())
                .setData(mList)
                .setOnRVItemClickListener(new OnRvItemClickListener() {
                    @Override
                    public void onRvItemClick(ViewGroup parent, View itemView, int position) {
                        if (position > mClasses.length - 1) {
                            SnackbarUtil.show(mNormalRv, "该功能未完成，敬请期待！！！");
                            return;
                        }
                        startActivity(mClasses[position]);
                    }
                })
                .build();
    }

    private void startActivity(Class aClass) {
        Intent intent = new Intent(MainActivity.this, aClass);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
