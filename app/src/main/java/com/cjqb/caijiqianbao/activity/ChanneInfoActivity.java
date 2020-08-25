package com.cjqb.caijiqianbao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cjqb.caijiqianbao.Constants;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.productBean.ProductData;
import com.cjqb.caijiqianbao.bean.productBean.ProductJsonData;
import com.cjqb.caijiqianbao.bean.productBean.Product_detail;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.lzy.okgo.OkGo;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

public class ChanneInfoActivity extends BaseActivity {

    private ImageView mImg;
    private TextView mTvName;
    private Button mBut;
    private LinearLayout mLladdImg;
    private TextView mTvNum;
    private TextView mTvContent;
    private TextView mTvMoney;
    private TextView mTvTig;
    private TextView mTvDay;
    private String mUrl;

    public static void forward(Context context, int product_id) {
        Intent intent = new Intent(context, ChanneInfoActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, product_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channe_info);
        mImg = (ImageView) findViewById(R.id.channe_info_img);
        View viewById = findViewById(R.id.btn_info_back);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvName = (TextView) findViewById(R.id.channe_info_name_tv);
        mTvNum = (TextView) findViewById(R.id.channe_info_num_tv);
        mTvContent = (TextView) findViewById(R.id.channe_info_content_tv);
        mTvMoney = (TextView) findViewById(R.id.channe_info_money_tv);
        mTvDay = (TextView) findViewById(R.id.channe_day_tv);
        mTvTig = (TextView) findViewById(R.id.channe_tig_tv);
        mBut = (Button) findViewById(R.id.channe_apple_but);
        mLladdImg = (LinearLayout) findViewById(R.id.channe_addImg_ll);
        getInfo();
        mBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apple();
            }
        });
    }

    private void getInfo() {
        OkGo.<ProductJsonData>get(HttpUrl.PRODUCT + getIntent().getIntExtra(Constants.PRODUCT_ID, 0))
                .execute(new CommonCallback<ProductJsonData>() {
                    @Override
                    public ProductJsonData convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), ProductJsonData.class);
                    }

                    @Override
                    public void onSuccess(Response<ProductJsonData> response) {
                        super.onSuccess(response);
                        ProductJsonData bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                ProductData data = bean.getData();
                                if (data == null )return;
                                Product_detail product_detail = data.getProduct_detail();

                                ImgLoader.display(ChanneInfoActivity.this, product_detail.getGoods_pic(), mImg);
                                mTvName.setText(product_detail.getGoods_name());
                                mTvNum.setText(product_detail.getPeople_num() + "人已申请");
                                mTvContent.setText(product_detail.getGoods_brief());
                                mTvMoney.setText(product_detail.getAmount());
                                String goods_content = product_detail.getGoods_content();
//                                mTvTig.setText(Html.fromHtml(product_detail.getGoods_content()));
//                                mTvTig.setText("放款快：审核通过率快至3分钟放款，成功率高\\n额度高：额度高达可借20万，秒速放款");
                                mTvTig.setText(goods_content.replace("\\n", "\n"));

                                mTvDay.setText(product_detail.getGoods_feature_term());
                                mUrl = product_detail.getGoods_url();


                                mLladdImg.removeAllViews();
                                List<String> product_icon = product_detail.getProduct_icon();
                                for (String s : product_icon) {
                                    final ImageView img = new ImageView(ChanneInfoActivity.this);
                                    Glide.with(ChanneInfoActivity.this)
                                            .asBitmap()
                                            .load(s).
                                            skipMemoryCache(false)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                    lp.setMargins(10, 0, 0, 0);
                                                    int width = bitmap.getWidth();
                                                    int height = bitmap.getHeight();
                                                    lp.width = DpUtil.dp2px(ChanneInfoActivity.this, (width / 5) * 2);
                                                    lp.height = DpUtil.dp2px(ChanneInfoActivity.this, (height / 5) * 2);
                                                    img.setLayoutParams(lp);
                                                    img.setImageBitmap(bitmap);
                                                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                                    mLladdImg.addView(img);
                                                }
                                            });
                                }
                            }
                        }

                    }
                });
    }

    private void apple() {
        OkGo.<JsonBean>post(HttpUrl.apple)
                .params("extend_id", getIntent().getIntExtra(Constants.PRODUCT_ID, 0))
                .params("type", "1")
                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        WebViewActivity.forward(ChanneInfoActivity.this, mUrl,mTvName.getText().toString());

                    }
                });
    }


}
