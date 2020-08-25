package com.cjqb.caijiqianbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.ChanneInfoActivity;
import com.cjqb.caijiqianbao.activity.LoginActivity;
import com.cjqb.caijiqianbao.activity.WebViewActivity;
import com.cjqb.caijiqianbao.bean.homeBean.Hot_list;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import static com.cjqb.caijiqianbao.fragment.HomeFragment.mProductDetailOff;


public class MainHotAdapter extends RecyclerView.Adapter<MainHotAdapter.Vh> {

    private final List<Hot_list> mList;

    private Context mContext;
    private LayoutInflater mInflater;

    public MainHotAdapter(Context context, List<Hot_list> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.main_hot_adapter, parent, false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.width = ScreenDimenUtil.getInstance().getScreenWdith() / 2;
        inflate.setLayoutParams(lp);
        return new Vh(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {
        vh.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mHotImg;
        TextView mTvHot;
        TextView mTvMoneyHot;
        TextView mTvCreditHot;
        RelativeLayout mHotCreditRl;

        public Vh(View itemView) {
            super(itemView);
            mHotImg = (ImageView) itemView.findViewById(R.id.hot_img);
            mTvHot = (TextView) itemView.findViewById(R.id.hot_tv);
            mTvMoneyHot = (TextView) itemView.findViewById(R.id.hot_money_tv);
            mTvCreditHot = (TextView) itemView.findViewById(R.id.hot_credit_tv);
            mHotCreditRl = (RelativeLayout) itemView.findViewById(R.id.hot_credit_rl);
        }

        void setData(final Hot_list bean) {
            ImgLoader.display(mContext, bean.getGoods_pic(), mHotImg);
            mTvHot.setText(bean.getGoods_name());
            mTvMoneyHot.setText(bean.getAmount() + "元");
            mHotCreditRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
                        LoginActivity.forward(mContext);
                    } else {
//                        ChanneInfoActivity.forward(mContext, bean.getProduct_id());
//                        WebViewActivity.forward(mContext, bean.getGoods_url());
                        if (mProductDetailOff == 0) {
                            apple(bean.getProduct_id(), bean.getGoods_url(),bean.getGoods_name());
                        } else {
                            ChanneInfoActivity.forward(mContext, bean.getProduct_id());
                        }
//                        apple(bean.getProduct_id(), bean.getGoods_url());
                    }
                }
            });
        }
    }

    private void apple(int id, final String url, final String title) {
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
