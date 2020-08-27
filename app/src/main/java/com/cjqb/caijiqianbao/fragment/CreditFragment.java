package com.cjqb.caijiqianbao.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.adapter.ChanneAdapter;
import com.cjqb.caijiqianbao.bean.homeBean.Product;
import com.cjqb.caijiqianbao.bean.productListBean.ProductListJsonData;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.cjqb.caijiqianbao.R;
import com.lzy.okgo.OkGo;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CreditFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mAllRVchanne;
    private LinearLayout mLlMoney, mLlsort, mLlscreen;
    private ImageView mImgMoney, mImgSort, mImgScreen;
    private PopupWindow mPopupWindow;
    private SwipeRefreshLayout mRefresh;
    private ArrayList<String> moneyData, sortData, screenData;
    private int page = 1;
    private List<Product> mProduct = new ArrayList<>();
    private ChanneAdapter mAdapter;
    private String amount_id = "";
    private String sort = "";
    private String screen = "";
    private LinkedHashMap<String, String> moneyMap;
    private LinkedHashMap<String, String> sortMap;
    private LinkedHashMap<String, String> screenMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit, null);
        mRefresh = view.findViewById(R.id.refresh);
        mAllRVchanne = view.findViewById(R.id.all_channe_rv);

        mLlMoney = view.findViewById(R.id.ll_money);
        mLlsort = view.findViewById(R.id.ll_sort);
        mLlscreen = view.findViewById(R.id.ll_screen);

        mImgMoney = view.findViewById(R.id.img_money);
        mImgScreen = view.findViewById(R.id.img_screen);
        mImgSort = view.findViewById(R.id.img_sort);

        mLlMoney.setOnClickListener(this);
        mLlsort.setOnClickListener(this);
        mLlscreen.setOnClickListener(this);
        mRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getList(page);
        moneyMap = new LinkedHashMap<String, String>();
        moneyMap.put("default", "");
        moneyMap.put("500-5000", "1");
        moneyMap.put("5001-20000", "2");
        moneyMap.put("20001-50000", "3");
        moneyMap.put("50001-100000", "4");
        moneyMap.put("10w+", "5");

        sortMap = new LinkedHashMap<String, String>();
        sortMap.put("default", "");
        sortMap.put("amount", "amount");
        sortMap.put("term", "term");

        screenMap = new LinkedHashMap<String, String>();
        screenMap.put("All", "");
        screenMap.put("hot", "is_hot");
        screenMap.put("new", "is_new");
        screenMap.put("fast", "is_fast");
        screenMap.put("recomment", "is_recommend");
        screenMap.put("monthly", "pay_monthly");
        screenMap.put("is_low", "is_low");

        moneyData = new ArrayList<>();
        sortData = new ArrayList<>();
        screenData = new ArrayList<>();

        for (String key : moneyMap.keySet()) {
            moneyData.add(key);
        }
        for (String key : sortMap.keySet()) {
            sortData.add(key);
        }
        for (String key : screenMap.keySet()) {
            screenData.add(key);
        }
        initChanne();
    }

    public void getList(final int page) {
        OkGo.<ProductListJsonData>get(HttpUrl.PRODUCT_LIST)
                .params("p", page)
                .params("amount_id", amount_id.equals("") ? "" : moneyMap.get(amount_id))
                .params("sort", sort.equals("") ? "" : sortMap.get(sort))
                .params("screen", screen.equals("") ? "" : screenMap.get(screen))
                .execute(new CommonCallback<ProductListJsonData>() {
                    @Override
                    public ProductListJsonData convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), ProductListJsonData.class);
                    }

                    @Override
                    public void onSuccess(Response<ProductListJsonData> response) {
                        super.onSuccess(response);
                        mRefresh.setRefreshing(false);
                        ProductListJsonData bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                if (mProduct == null || mProduct.size() == 0 || page == 1) {
                                    mProduct = bean.getData().getProduct();
                                } else {
                                    mProduct.addAll(bean.getData().getProduct());
                                }
                                initChanne();
                            }
                        }

                    }
                });
    }

    private void initChanne() {
        if (mAdapter == null) {
            mAdapter = new ChanneAdapter(getActivity(), mProduct);
            mAdapter.setType(mAdapter.CREDIT_CHANNE);
            mAllRVchanne.setHasFixedSize(true);
            mAllRVchanne.setFocusableInTouchMode(false);
            mAllRVchanne.setFocusable(false);
            mAllRVchanne.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mAllRVchanne.setAdapter(mAdapter);
        }
        mAdapter.setList(mProduct);
        mAdapter.notifyDataSetChanged();

        if (mProduct.size() >= 10) {
            mAllRVchanne.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            page = page + 1;
                            getList(page);
                        }
                    }
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        //获取最后一个完全显示的ItemPosition
                        int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();

                        // 判断是否滚动到底部，并且是向右滚动
                        if (lastVisibleItem == (totalItemCount - 1)) {
                            //加载更多功能的代码
                            page = page + 1;
                            getList(page);
                        }
                    }
                }
            });
        }

    }


    public void setImageViewRotation(View v) {
        if (v.getRotation() == 270) {
            v.setRotation(90);
        } else {
            v.setRotation(270);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_money) {
            init(moneyData,mImgMoney);
        } else if (i == R.id.ll_sort) {
            init(sortData,mImgSort);
        } else if (i == R.id.ll_screen) {
            init(screenData,mImgScreen);
        }
    }

    private void init(final List<String> data,ImageView img) {
        LinearLayout ll = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundDrawable(getActivity().getResources().getDrawable(R.color.white));

        tvLp.height = DpUtil.dp2px(getActivity(), 50);
        lp.height = DpUtil.dp2px(getActivity(), 1);
        for (int i = 0; i < data.size(); i++) {//遍历图片资源
            TextView tv = new TextView(getActivity());
            tv.setText(data.get(i));
            tv.setLayoutParams(tvLp);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setPadding(20, 0, 0, 0);
            tv.setBackgroundDrawable(getActivity().getResources().getDrawable(R.color.white));
            ll.addView(tv);
            View view = new View(getActivity());
            view.setLayoutParams(lp);
            view.setBackgroundDrawable(getActivity().getResources().getDrawable(R.color.home_line));
            ll.addView(view);
            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (data == moneyData) {
                        amount_id = ((TextView) v).getText().toString();
                    } else if (data == sortData) {
                        sort = ((TextView) v).getText().toString();
                    } else if (data == screenData) {
                        screen = ((TextView) v).getText().toString();
                    }
                    mPopupWindow.dismiss();
                    page = 1;
                    getList(page);
                }
            });
        }
        showPopuWindow(mLlMoney, ll, img);
    }

    //创建showPopupWindow方法
    private void showPopuWindow(final View anchor, View popuView, final ImageView img) {
        // 显示下拉选择框（放入的控件，宽，高）
        mPopupWindow = new PopupWindow(popuView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        // 设置点击外部区域, 自动隐藏
        mPopupWindow.setOutsideTouchable(true); // 外部可触摸
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置空的背景, 响应点击事件

        mPopupWindow.setFocusable(true); //设置可获取焦点

        // 显示在指定控件下
//        popupWindow.showAsDropDown(anchor, 0, 0);
        if (Build.VERSION.SDK_INT < 24) {
            mPopupWindow.showAsDropDown(anchor);
        } else {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            int y = location[1];
            mPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, y + anchor.getHeight());
        }
        setImageViewRotation(img);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setImageViewRotation(img);
            }
        });
    }


    @Override
    public void onRefresh() {
        page = 1;
        amount_id = "";
        sort = "";
        screen = "";
        getList(page);
    }
}
