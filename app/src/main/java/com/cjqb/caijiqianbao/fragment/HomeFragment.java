package com.cjqb.caijiqianbao.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.ChanneInfoActivity;
import com.cjqb.caijiqianbao.activity.LoginActivity;
import com.cjqb.caijiqianbao.activity.MainActivity;
import com.cjqb.caijiqianbao.activity.StrategyActivity;
import com.cjqb.caijiqianbao.activity.WebViewActivity;
import com.cjqb.caijiqianbao.adapter.ChanneAdapter;
import com.cjqb.caijiqianbao.adapter.MainHotAdapter;
import com.cjqb.caijiqianbao.adapter.MainNewAdapter;
import com.cjqb.caijiqianbao.adapter.StrategyAdapter;
import com.cjqb.caijiqianbao.bean.homeBean.Article;
import com.cjqb.caijiqianbao.bean.homeBean.HomeData;
import com.cjqb.caijiqianbao.bean.homeBean.HomeJsonData;
import com.cjqb.caijiqianbao.bean.homeBean.Hot_list;
import com.cjqb.caijiqianbao.bean.homeBean.Product;
import com.cjqb.caijiqianbao.bean.homeBean.Product_class;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRvStrategy;
    private RecyclerView mRvHot;
    private Context mContext;
    private RecyclerView mRvChanne;
    private SwipeRefreshLayout mRefresh;
    private View mHeadView;
    private ArrayList<String> mBannerPath = new ArrayList<>();
    private ArrayList<String> mBannerClickUrl = new ArrayList<>();
    private Banner mBanner;
    private boolean mBannerShowed;
    private RecyclerView mRvNew;
    List<Hot_list> hotList = new ArrayList<>();
    List<Product> product = new ArrayList<>();
    List<Article> article = new ArrayList<>();
    List<Product_class> product_class = new ArrayList<>();
    List<com.cjqb.caijiqianbao.bean.homeBean.Banner> banner = new ArrayList<>();
    private ProgressDialog progressDialog;
    public static int mProductDetailOff;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        mRvStrategy = (RecyclerView) view.findViewById(R.id.strategy_rv);
        mRefresh = view.findViewById(R.id.refresh);
        mRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.head_home_news, null);
        mHeadView.findViewById(R.id.home_more_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).changetoCreditFragment();
            }
        });
        mHeadView.findViewById(R.id.home_strategy_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrategyActivity.forward(mContext);
            }
        });
        initFlipper();
        initRvHot(hotList);
        initRvChanne(product);
        initRvStrategy(article);
        initRvNew(product_class);
        getHomeData();
    }

    private void showWaiting() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("加载dialog");
        progressDialog.setMessage("加载中...");
        progressDialog.setIndeterminate(true);// 是否形成一个加载动画  true表示不明确加载进度形成转圈动画  false 表示明确加载进度
        progressDialog.setCancelable(false);//点击返回键或者dialog四周是否关闭dialog  true表示可以关闭 false表示不可关闭
        progressDialog.show();

    }

    private void getHomeData() {
//        showWaiting();
        OkGo.<HomeJsonData>get(HttpUrl.INDX)
                .headers("Connection", "keep-alive")
                .execute(new CommonCallback<HomeJsonData>() {
                    @Override
                    public HomeJsonData convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), HomeJsonData.class);
                    }

                    @Override
                    public void onSuccess(Response<HomeJsonData> response) {
                        super.onSuccess(response);
//                        progressDialog.dismiss();
                        mRefresh.setRefreshing(false);
                        HomeJsonData bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                HomeData homeData = bean.getData();
                                banner = homeData.getBanner();
                                mProductDetailOff = homeData.getProduct_detail_off();
                                List<Hot_list> hotList = homeData.getHot_list();
                                List<Product> product = homeData.getRecommend_list();
//                                List<Product> product = homeData.getProduct();
                                List<Article> article = homeData.getArticle();
                                List<Product_class> product_class = homeData.getProduct_class();
                                mBannerPath.clear();
                                mBannerClickUrl.clear();
                                for (com.cjqb.caijiqianbao.bean.homeBean.Banner banner1 : banner) {
                                    mBannerPath.add(banner1.getPic());
                                    mBannerClickUrl.add(banner1.getTag_url());
                                }
                                initBanner();
//                                showBanner();
                                initRvStrategy(article);
                                initRvHot(hotList);
                                initRvChanne(product);
                                initRvNew(product_class);
                            }
                        }

                    }

                });
    }

    private void initRvNew(List<Product_class> product_class) {
        mRvNew = mHeadView.findViewById(R.id.new_rv);
        MainNewAdapter newAdapter = new MainNewAdapter(mContext, product_class);
        mRvNew.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvNew.setHasFixedSize(true);
        mRvNew.setAdapter(newAdapter);
    }

    private void initRvHot(List<Hot_list> hotList) {
        mRvHot = mHeadView.findViewById(R.id.hot_rv);
        View view = mHeadView.findViewById(R.id.home_hot_rl);
        if (hotList != null) {
            if (hotList.size() == 0) {
                mRvHot.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                return;
            } else {
                mRvHot.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
            }
        }
        MainHotAdapter hotAdapter = new MainHotAdapter(mContext, hotList);
        if (hotList.size() <= 2) {
            mRvHot.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false);
            mRvHot.setLayoutManager(gridLayoutManager);
        }
        mRvHot.setHasFixedSize(true);
        mRvHot.setAdapter(hotAdapter);
    }

    private void initRvChanne(List<Product> product) {
        mRvChanne = mHeadView.findViewById(R.id.channe_rv);
        ChanneAdapter channeAdapter = new ChanneAdapter(mContext, product);
        mRvChanne.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvChanne.setHasFixedSize(true);
        mRvChanne.setNestedScrollingEnabled(false);
        mRvChanne.setAdapter(channeAdapter);
    }

    private void initRvStrategy(List<Article> article) {
        StrategyAdapter Adapter = new StrategyAdapter(getActivity(), article);
        Adapter.setHeadView(mHeadView);
        Adapter.setisHead(true);
        mRvStrategy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvStrategy.setAdapter(Adapter);
        mRvStrategy.setHasFixedSize(true);
    }

    private void initFlipper() {
        ViewFlipper mViewFlipper = mHeadView.findViewById(R.id.messsage_viewflipper);
        Animation anim_in_left = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left);//左进
        Animation anim_out_right = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right);//右出
        mViewFlipper.setInAnimation(anim_in_left);//添加进入动画效果
        mViewFlipper.setOutAnimation(anim_out_right);//添加退出动画效果
        List<String> data = new ArrayList<>();
        data.add("用户137****6546已成功借款");
        data.add("用户139****7905已成功借款");
        data.add("用户150****6437已成功借款");
        data.add("用户182****2628已成功借款");
        data.add("用户134****3105已成功借款");
        data.add("用户178****6849已成功借款");
        data.add("用户147****9871已成功借款");
        data.add("用户187****9134已成功借款");
        data.add("用户157****6129已成功借款");
        data.add("用户158****3452已成功借款");
        data.add("用户138****5781已成功借款");
        for (int i = 0; i < data.size(); i++) {//遍历图片资源
            TextView textView = new TextView(mContext);//创建ImageView对象
            textView.setText(data.get(i));//将遍历的图片保存在ImageView中
            mViewFlipper.addView(textView);//  //加载图片
        }
    }

    public void initBanner() {
        //放标题的集合
        mBanner = mHeadView.findViewById(R.id.banner);
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                setViewSize(mBanner, ScreenDimenUtil.getInstance().getScreenWdith(), (int) (ScreenDimenUtil.getInstance().getScreenWdith() * 0.48));
                ImgLoader.display(mContext, path.toString(), imageView);
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int p) {

//                if (mProductDetailOff == 0) {
//                    apple(bean.getProduct_id(), bean.getGoods_url());
//                } else {
//                    ChanneInfoActivity.forward(mContext, bean.getProduct_id());

                    if (banner.get(p).getProduct_id() > 0) {
                        if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
                            LoginActivity.forward(mContext);
                        } else {
                            if (0 == banner.get(p).getProduct_id()) return;
                            ChanneInfoActivity.forward(getActivity(), banner.get(p).getProduct_id());
                        }
                        return;
                    }
//                }
                if (!TextUtils.isEmpty(mBannerClickUrl.get(p)))
                    WebViewActivity.forward(getActivity(), mBannerClickUrl.get(p),"");
            }
        });
        showBanner();
    }

    private void showBanner() {
        if (mBannerPath == null || mBannerPath.size() == 0 || mBanner == null) {
            return;
        }
//        if (mBannerShowed) {
//            return;
//        }
//        mBannerShowed = true;
        mBanner.setImages(mBannerPath);
        mBanner.start();
    }

    /**
     * 设置控件大小
     */
    public void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    @Override
    public void onRefresh() {
        getHomeData();
    }
}
