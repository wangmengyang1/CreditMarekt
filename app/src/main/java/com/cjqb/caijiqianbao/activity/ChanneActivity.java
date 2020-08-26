package com.cjqb.caijiqianbao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.Constants;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.adapter.ChanneAdapter;
import com.cjqb.caijiqianbao.bean.homeBean.Product;
import com.cjqb.caijiqianbao.bean.productListBean.ProductListJsonData;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class ChanneActivity extends BaseActivity {
    private RecyclerView mRvStrategy;
    private ChanneAdapter mChanneAdapter;
    List<Product> product = new ArrayList<>();

    public static void forward(Context context, int product_id,String title) {
        Intent intent = new Intent(context, ChanneActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, product_id);
        intent.putExtra(Constants.TO_NAME, title);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);
        mRvStrategy = findViewById(R.id.strategy_rv);
        mChanneAdapter = new ChanneAdapter(this, product);
        mRvStrategy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvStrategy.setHasFixedSize(true);
        mRvStrategy.setFocusableInTouchMode(false);
        mRvStrategy.setFocusable(false);
        mRvStrategy.setAdapter(mChanneAdapter);
        setTitle(getIntent().getStringExtra(Constants.TO_NAME));
        getList();
    }

    private void getList() {
        OkGo.<ProductListJsonData>get(HttpUrl.PRODUCT_ID+
                getIntent().getIntExtra(Constants.PRODUCT_ID, 0))
                .execute(new CommonCallback<ProductListJsonData>() {
                    @Override
                    public ProductListJsonData convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), ProductListJsonData.class);
                    }
                    @Override
                    public void onSuccess(Response<ProductListJsonData> response) {
                        super.onSuccess(response);
                        ProductListJsonData bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                mChanneAdapter.notifyList(bean.getData().getProduct());
                                mChanneAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
    }
}
