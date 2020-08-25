package com.cjqb.caijiqianbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.WebViewActivity;
import com.cjqb.caijiqianbao.bean.homeBean.Article;
import com.cjqb.caijiqianbao.glide.ImgLoader;

import java.util.List;

public class StrategyAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    public View mHeadView;
    private List<Article> mList;
    private static final int HEAD = -1;
    private static final int FOOT = 0;
    private boolean mIsAddHead = false;
    private int mType;

    public List<Article> getList() {
        return mList;
    }

    public void setList(List<Article> mList) {
        this.mList = mList;
    }

    public StrategyAdapter(Context context, List<Article> list) {
        mList = list;
        mContext = context;

    }

    public void setHeadView(View view) {
        mHeadView = view;
    }

    public void setType(int type) {
        mType = type;
    }

    public void setisHead(boolean isAddHead) {
        mIsAddHead = isAddHead;
        if (mIsAddHead) mList.add(0, new Article());
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
        return new channerHolder(LayoutInflater.from(mContext).inflate(R.layout.strategy_adapter, parent, false));
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

        void setData(Article bean, int position) {
//            mName.setText(bean);
        }
    }

    class channerHolder extends RecyclerView.ViewHolder {
        ImageView mImgStrategy;
        TextView mTvStrategyTitle;
        TextView mTvStrategyTime;
        TextView mTvStrategyNum;
        View view;


        public channerHolder(View itemView) {
            super(itemView);
            mImgStrategy = itemView.findViewById(R.id.strategy_img);
            mTvStrategyTime = itemView.findViewById(R.id.strategy_time_tv);
            mTvStrategyTitle = itemView.findViewById(R.id.strategy_title_tv);
            mTvStrategyNum = itemView.findViewById(R.id.strategy_num_tv);
            view = itemView.findViewById(R.id.view);
        }

        void setData(Article bean, final int position) {
            ImgLoader.display(mContext, bean.getThumbnail(), mImgStrategy);
            mTvStrategyTitle.setText(bean.getPost_title());
            mTvStrategyTime.setText(bean.getPublished_time());
            mTvStrategyNum.setText(bean.getPeople_num()+"阅读");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.forward(mContext, mList.get(position).getArticle_url(),"贷款攻略");
                }
            });
            if (position == getList().size()-1){
                view.setVisibility(View.GONE);
            }else{
                view.setVisibility(View.VISIBLE);
            }

//            mTvMoney.setText(bean.getAmount());
//            mTvDay.setText(bean.getGoods_feature_term());
        }
    }
}