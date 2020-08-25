package com.cjqb.caijiqianbao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.adapter.StrategyAdapter;
import com.cjqb.caijiqianbao.bean.homeBean.Article;
import com.cjqb.caijiqianbao.bean.strategyListBean.StrategyListData;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

public class StrategyActivity extends BaseActivity {

    RecyclerView mRvStrategy;
    private int page = 1;
    private List<Article> mArticle;
    StrategyAdapter mAdapter;
    TextView mTvLoad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);
        mRvStrategy = findViewById(R.id.strategy_rv);
        mTvLoad = findViewById(R.id.tv_load);
        setTitle("贷款攻略");
        getList(page);
    }

    private void getList(int page) {
        OkGo.<StrategyListData>get(HttpUrl.ARTICLE_LIST)
                .params("p", page)
                .execute(new CommonCallback<StrategyListData>() {
                    @Override
                    public StrategyListData convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), StrategyListData.class);
                    }

                    @Override
                    public void onSuccess(Response<StrategyListData> response) {
                        super.onSuccess(response);
                        StrategyListData bean = response.body();
                        mTvLoad.setVisibility(View.GONE);
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                if (mArticle == null || mArticle.size() == 0) {
                                    mArticle = bean.getData().getArticle();
                                } else {
                                    mArticle.addAll(bean.getData().getArticle());

                                }
                                initRvStrategy();
                            }
                        }

                    }
                });
    }

    public static void forward(Context context) {
        context.startActivity(new Intent(context, StrategyActivity.class));
    }

    private void initRvStrategy() {
        if (mAdapter == null) {
            mAdapter = new StrategyAdapter(this, mArticle);
            mRvStrategy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRvStrategy.setHasFixedSize(true);
            mRvStrategy.setFocusableInTouchMode(false);
            mRvStrategy.setFocusable(false);
            mRvStrategy.setAdapter(mAdapter);
        }
        mAdapter.setList(mArticle);
        mAdapter.notifyDataSetChanged();
        mRvStrategy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
                        if (mArticle.size() % 10 == 0) {
                            mTvLoad.setVisibility(View.VISIBLE);
                            page = page + 1;
                            getList(page);
                        }
                    }
                }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 2)) {
                        //加载更多功能的代码
                        if (mArticle.size() % 10 == 0) {
                            page = page + 1;
                            getList(page);
                        }
                    }
                }
            }
        });

    }
}
