package io.github.anotherme17.commonadaptersample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import io.github.anotherme17.commonadaptersample.App;
import io.github.anotherme17.commonadaptersample.R;
import io.github.anotherme17.commonadaptersample.delegate.NormalDelegate;
import io.github.anotherme17.commonadaptersample.delegate.TextViewDelegate;
import io.github.anotherme17.commonadaptersample.engine.ApiEngine;
import io.github.anotherme17.commonadaptersample.model.BannerModel;
import io.github.anotherme17.commonadaptersample.model.NormalModel;
import io.github.anotherme17.commonadaptersample.ui.widget.Divider;
import io.github.anotherme17.commonadaptersample.util.SnackbarUtil;
import io.github.anotherme17.commonrvadapter.Constants;
import io.github.anotherme17.commonrvadapter.adapter.RecyclerViewAdapter;
import io.github.anotherme17.commonrvadapter.listener.OnNoDoubleClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildCheckedChangeListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemChildLongClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemClickListener;
import io.github.anotherme17.commonrvadapter.listener.OnRvItemLongClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user798 on 2016/12/29.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerViewAdapter<NormalModel> mAdapter;
    private RecyclerView mDataRv;
    private ItemTouchHelper mItemTouchHelper;
    private BGABanner mBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recyclerview);
        mDataRv = (RecyclerView) findViewById(R.id.rv_recyclerview_data);
        //mAdapter = new RecyclerViewAdapter<>(mDataRv);

        /*=== Builder Model ===*/
        mAdapter = new RecyclerViewAdapter.Builder<NormalModel>(mDataRv)
                .addDelegate(new NormalDelegate())
                .addDelegate(new TextViewDelegate())
                .setItemTouchHelper(mDataRv, Constants.SWIP_ENABLE
                        | Constants.DRAG_ENABLE)
                .setOnRVItemClickListener(new OnRvItemClickListener() {
                    @Override
                    public void onRvItemClick(ViewGroup parent, View itemView, int position) {
                        showSnackbar("点击了条目 " + mAdapter.getItem(position).title);
                    }
                })
                .setOnRVItemLongClickListener(new OnRvItemLongClickListener() {
                    @Override
                    public boolean onRvItemLongClick(ViewGroup parent, View itemView, int position) {
                        showSnackbar("长按了条目 " + mAdapter.getItem(position).title);
                        return true;
                    }
                })
                .setOnItemChildClickListener(new OnRvItemChildClickListener() {
                    @Override
                    public void onRvItemChildClick(ViewGroup parent, View childView, int position) {
                        if (childView.getId() == R.id.tv_item_normal_delete) {
                            mAdapter.removeItem(position);
                        }
                    }
                })
                .setOnItemChildLongClickListener(new OnRvItemChildLongClickListener() {
                    @Override
                    public boolean onRvItemChildLongClick(ViewGroup parent, View childView, int position) {
                        if (childView.getId() == R.id.tv_item_normal_delete) {
                            showSnackbar("长按了删除 " + mAdapter.getItem(position).title);
                            return true;
                        }
                        return false;
                    }
                })
                .setOnItemChildCheckedChangeListener(new OnRvItemChildCheckedChangeListener() {
                    @Override
                    public void onRvItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked) {
                        mAdapter.getItem(position).selected = isChecked;
                        showSnackbar((isChecked ? "选中 " : "取消选中") + mAdapter.getItem(position).title);
                    }
                })
                /*.setOnRVItemChildTouchListener(new OnRvItemChildTouchListener() {
                    @Override
                    public boolean onRvItemChilcTouch(RecyclerViewHolder viewHolder, View childView, MotionEvent event) {
                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                            mItemTouchHelper.startDrag(viewHolder);
                        }
                        return false;
                      *//*  Logger.i("TEST", "onRvItemChilcTouch");
                        return true;*//*
                    }
                })*/
                .attatchView(mDataRv);

      /*  mAdapter.addDelegate(new NormalDelegate());
        mAdapter.addDelegate(new TextViewDelegate());
        mAdapter.setOnRVItemClickListener(new OnRvItemClickListener() {
            @Override
            public void onRvItemClick(ViewGroup parent, View itemView, int position) {
                showSnackbar("点击了条目 " + mAdapter.getItem(position).title);
            }
        });
        mAdapter.setOnRVItemLongClickListener(new OnRvItemLongClickListener() {
            @Override
            public boolean onRvItemLongClick(ViewGroup parent, View itemView, int position) {
                showSnackbar("长按了条目 " + mAdapter.getItem(position).title);
                return true;
            }
        });
        mAdapter.setOnItemChildClickListener(new OnRvItemChildClickListener() {
            @Override
            public void onRvItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_item_normal_delete) {
                    mAdapter.removeItem(position);
                }
            }
        });
        mAdapter.setOnItemChildLongClickListener(new OnRvItemChildLongClickListener() {
            @Override
            public boolean onRvItemChildLongClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_item_normal_delete) {
                    showSnackbar("长按了删除 " + mAdapter.getItem(position).title);
                    return true;
                }
                return false;
            }
        });
        mAdapter.setOnItemChildCheckedChangeListener(new OnRvItemChildCheckedChangeListener() {
            @Override
            public void onRvItemChildCheckedChanged(ViewGroup parent, CompoundButton childView, int position, boolean isChecked) {
                mAdapter.getItem(position).selected = isChecked;
                showSnackbar((isChecked ? "选中 " : "取消选中") + mAdapter.getItem(position).title);
            }
        });
        mAdapter.setOnRVItemChildTouchListener(new OnRvItemChildTouchListener() {
            @Override
            public boolean onRvItemChilcTouch(RecyclerViewHolder viewHolder, View childView, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mItemTouchHelper.startDrag(viewHolder);
                }
                return false;
            }
        });*/

        // 设置分割线
        mDataRv.addItemDecoration(new Divider(this));


        // 初始化拖拽排序和滑动删除
      /*  mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mDataRv);*/


        // 测试 GridLayoutManager
        mDataRv.setLayoutManager(getLinearLayoutManager());
        // 测试 LinearLayoutManager
//        mDataRv.setLayoutManager(getLinearLayoutManager());


        // 测试没有 Header 和 Footer 的情况
//        mDataRv.setAdapter(mAdapter);

        // 测试有 Header 或 Footer 的情况
        //testHaveHeaderAndFooterAdapter();

        //loadBannerModels();
        loadNormalModels();
    }

    private RecyclerView.LayoutManager getGridLayoutManager() {
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 || position % 2 == 0) ? 1 : layoutManager.getSpanCount();
            }
        });
        return layoutManager;
    }

    private RecyclerView.LayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void testHaveHeaderAndFooterAdapter() {
        addBannerHeader();

        TextView header1Tv = new TextView(this);
        header1Tv.setBackgroundColor(Color.parseColor("#E15B5A"));
        header1Tv.setTextColor(Color.WHITE);
        header1Tv.setGravity(Gravity.CENTER);
        header1Tv.setPadding(30, 30, 30, 30);
        header1Tv.setText("头部1");
        header1Tv.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSnackbar("点击了头部1");
            }
        });
        // 当时 LinearLayoutManager 时，需要设置一下布局参数的宽度为填充父窗体，否则 header 和 footer 的宽度会是包裹内容
        header1Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addHeaderView(header1Tv);

        TextView footer1Tv = new TextView(this);
        footer1Tv.setBackgroundColor(Color.parseColor("#6C9FFC"));
        footer1Tv.setTextColor(Color.WHITE);
        footer1Tv.setGravity(Gravity.CENTER);
        footer1Tv.setPadding(30, 30, 30, 30);
        footer1Tv.setText("底部1");
        footer1Tv.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSnackbar("点击了底部1");
            }
        });
        footer1Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addFooterView(footer1Tv);

        TextView footer2Tv = new TextView(this);
        footer2Tv.setBackgroundColor(Color.parseColor("#51535B"));
        footer2Tv.setTextColor(Color.WHITE);
        footer2Tv.setGravity(Gravity.CENTER);
        footer2Tv.setPadding(50, 50, 50, 50);
        footer2Tv.setText("底部2");
        footer2Tv.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showSnackbar("点击了底部2");
            }
        });
        footer2Tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        mAdapter.addFooterView(footer2Tv);

        // 当有 Header 或者 Footer 时，需要传入 mAdapter.getHeaderAndFooterAdapter()
        mDataRv.setAdapter(mAdapter.getHeaderAndFooterAdapter());
    }

    private void addBannerHeader() {
        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.layout_header_banner, null);
        mBanner = (BGABanner) headerView.findViewById(R.id.banner);
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.drawable.holder_banner).error(R.drawable.holder_banner).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });
        mBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
                showSnackbar("点击了第" + (position + 1) + "页");
            }
        });
        mAdapter.addHeaderView(headerView);
    }

    /**
     * 加载广告条数据
     */
    private void loadBannerModels() {
        App.getInstance().getRetrofit().create(ApiEngine.class).loadBannerData("http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/4item.json").enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                mBanner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "加载广告条数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载列表数据
     */
    private void loadNormalModels() {
        App.getInstance().getRetrofit().create(ApiEngine.class).getNormalModels().enqueue(new Callback<List<NormalModel>>() {
            @Override
            public void onResponse(Call<List<NormalModel>> call, Response<List<NormalModel>> response) {
                mAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<NormalModel>> call, Throwable t) {
                showSnackbar("数据加载失败");
            }
        });
    }

    public void showSnackbar(String msg) {
        SnackbarUtil.show(mDataRv, msg);
    }

    /**
     * 该类参考：https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
     */
   /* private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        public static final float ALPHA_FULL = 1.0f;

        @Override
        public boolean isLongPressDragEnabled() {
            //return true;
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

            // 当加了 HeaderAndFooterAdapter 时需要加上下面的判断
            if (mAdapter.isHeaderOrFooter(viewHolder)) {
                dragFlags = swipeFlags = ItemTouchHelper.ACTION_STATE_IDLE;
            }

            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            mAdapter.moveItem(source, target);


            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.removeItem(viewHolder);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
                ViewCompat.setAlpha(viewHolder.itemView, alpha);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setSelected(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
            viewHolder.itemView.setSelected(false);
        }
    }*/
}
