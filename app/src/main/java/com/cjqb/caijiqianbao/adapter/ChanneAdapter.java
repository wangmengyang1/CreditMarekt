package com.cjqb.caijiqianbao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.ChanneInfoActivity;
import com.cjqb.caijiqianbao.activity.LoginActivity;
import com.cjqb.caijiqianbao.activity.WebViewActivity;
import com.cjqb.caijiqianbao.bean.homeBean.Product;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import static com.cjqb.caijiqianbao.fragment.HomeFragment.mProductDetailOff;

public class ChanneAdapter extends RecyclerView.Adapter {
    private Context mContext;
    public View mHeadView;
    private List<Product> mList;
    private static final int HEAD = -1;
    private static final int FOOT = 0;
    public static final int HOME_CHANNE = 0;
    public static final int CREDIT_CHANNE = 1;
    private boolean mIsAddHead = false;
    private int mType;


    public List<Product> getList() {
        return mList;
    }

    public void setList(List<Product> mList) {
        this.mList = mList;
    }

    public ChanneAdapter(Context context, List<Product> product) {
        mList = product;
        mContext = context;

    }

    public void notifyList(List<Product> product) {
        mList = product;
    }

    public void setType(int type) {
        mType = type;
    }

    public void setHeadView(View view) {
        mHeadView = view;
    }


    public void setisHead(boolean isAddHead) {
        mIsAddHead = isAddHead;
        if (mIsAddHead) mList.add(0, new Product());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mIsAddHead) {

            return HEAD;
        }
        return FOOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            return new headholder(mHeadView);
        }
        return new channerHolder(LayoutInflater.from(mContext).inflate(R.layout.channe_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof channerHolder)
            ((channerHolder) holder).setData(mList.get(position), position);
        if (holder instanceof headholder)
            ((headholder) holder).setData(mList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class headholder extends RecyclerView.ViewHolder {

        public headholder(View itemView) {
            super(itemView);
        }

        void setData(Product bean, int position) {
//            mName.setText(bean);
        }
    }

    class channerHolder extends RecyclerView.ViewHolder {
        ImageView mImgChanne;
        TextView mCPersonText;
        TextView mTvMoney;
        TextView mTvName;
        TextView mTvContent;
        TextView mTvMoneyYuan;
        TextView mTvDay;
        LinearLayout mLladdImg;
        Button mCAppleBut;
        RelativeLayout mRlRoot;


        public channerHolder(View itemView) {
            super(itemView);
            mImgChanne = itemView.findViewById(R.id.channe_img);
            mCAppleBut = itemView.findViewById(R.id.channe_apple);
            mCPersonText = itemView.findViewById(R.id.channe_person);
            mTvName = itemView.findViewById(R.id.channe_name);
            mTvMoney = itemView.findViewById(R.id.channe_money);
            mTvMoneyYuan = itemView.findViewById(R.id.channe_money_yuan);
            mTvContent = itemView.findViewById(R.id.channe_content);
            mTvDay = itemView.findViewById(R.id.channe_day);
            mLladdImg = itemView.findViewById(R.id.channe_addImg_ll);
            mRlRoot = itemView.findViewById(R.id.rl_roots);
        }

        void setData(final Product bean, final int position) {
            ImgLoader.display(mContext, bean.getGoods_pic(), mImgChanne);
            if (mType == CREDIT_CHANNE) {
                mTvMoney.setTextColor(mContext.getResources().getColor(R.color.textColor));
                mTvMoneyYuan.setTextColor(mContext.getResources().getColor(R.color.textColor));
                mTvMoney.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mTvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mCPersonText.setVisibility(View.VISIBLE);

                SpannableString spannableString = new SpannableString("已有" + bean.getPeople_num() + "人申请");
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9800")), 2, spannableString.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mCPersonText.setText(spannableString);
            }
            mTvName.setText(bean.getGoods_name());
            mTvContent.setText(bean.getGoods_brief());
            mTvMoney.setText(bean.getAmount());
            mTvDay.setText(bean.getGoods_feature_term());
            mCAppleBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
                        LoginActivity.forward(mContext);
                    } else {
//                        ChanneInfoActivity.forward(mContext, bean.getProduct_id());
//                        WebViewActivity.forward(mContext, bean.getGoods_url());
                        if (mProductDetailOff == 0) {
                            apple(bean.getProduct_id(), bean.getGoods_url(),bean.getGoods_name());
                        }else{
                            ChanneInfoActivity.forward(mContext, bean.getProduct_id());
                        }
                    }
                }
            });
            mRlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
                        LoginActivity.forward(mContext);
                    } else {
//                        ChanneInfoActivity.forward(mContext, bean.getProduct_id());
//                        WebViewActivity.forward(mContext, bean.getGoods_url());
//                        apple(bean.getProduct_id(), bean.getGoods_url());
                        if (mProductDetailOff == 0) {
                            apple(bean.getProduct_id(), bean.getGoods_url(),bean.getGoods_name());
                        }else{
                            ChanneInfoActivity.forward(mContext, bean.getProduct_id());
                        }
                    }
                }
            });
            mLladdImg.removeAllViews();
            List<String> product_icon = bean.getProduct_icon();
            for (String s : product_icon) {
                Glide.with(mContext)
                        .asBitmap()
                        .load(s).
                        skipMemoryCache(false)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                ImageView img = new ImageView(mContext);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(10, 0, 0, 0);
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                int screenHeight = ScreenDimenUtil.getInstance().getScreenHeight();
                                int screenWdith = ScreenDimenUtil.getInstance().getScreenWdith();
                                lp.width = DpUtil.dp2px(mContext, (width / 5) * 2);
                                lp.height = DpUtil.dp2px(mContext, (height / 5) * 2);
                                img.setLayoutParams(lp);
                                img.setImageBitmap(bitmap);
                                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                mLladdImg.addView(img);
                            }
                        });

            }
        }
    }

    private void apple(int id, final String url,final String title) {
        OkGo.<JsonBean>post(HttpUrl.apple)
                .params("extend_id", id)
                .params("type", "1")
                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        WebViewActivity.forward(mContext, url,title);

                    }
                });
    }
}