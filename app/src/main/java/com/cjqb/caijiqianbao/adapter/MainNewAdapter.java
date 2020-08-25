package com.cjqb.caijiqianbao.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.ChanneActivity;
import com.cjqb.caijiqianbao.activity.WebViewActivity;
import com.cjqb.caijiqianbao.bean.homeBean.Product_class;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;

import java.util.List;


public class MainNewAdapter extends RecyclerView.Adapter<MainNewAdapter.Vh> {

    private final List<Product_class> mList;

    private Context mContext;
    private LayoutInflater mInflater;

    public MainNewAdapter(Context context, List<Product_class> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.new_adapter, parent, false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.width = ScreenDimenUtil.getInstance().getScreenWdith() / getItemCount();
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

        ImageView mNewImg;
        TextView mTvNew;

        public Vh(View itemView) {
            super(itemView);
            mNewImg = (ImageView) itemView.findViewById(R.id.new_img);
            mTvNew = (TextView) itemView.findViewById(R.id.new_tv);

        }

        void setData(final Product_class bean) {
            ImgLoader.display(mContext, bean.getClass_pic(), mNewImg);
            mTvNew.setText(bean.getClass_name());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(bean.getTag_url())) {
                        WebViewActivity.forward(mContext, bean.getTag_url(),bean.getClass_name());
                    } else {
//                        ((MainActivity) mContext).changetoCreditFragment();
                        ChanneActivity.forward(mContext,bean.getProduct_class_id(),bean.getClass_name());
                    }
                }
            });

        }
    }
}
