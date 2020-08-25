package com.cjqb.caijiqianbao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.MainActivity;
import com.cjqb.caijiqianbao.adapter.MainSelfAdapter;
import com.cjqb.caijiqianbao.bean.userBean.UserData;
import com.cjqb.caijiqianbao.bean.userBean.UserJsonBean;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.FullyLinearLayoutManager;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

public class SelfFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mTvIphoneNum;
    private TextView mTvIsVip;
    private View mPopuView;
    private PopupWindow popupWindow;
    private ImageView mBg;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mTvIphoneNum = (TextView) view.findViewById(R.id.self_iphone_rv);
        mTvIsVip = (TextView) view.findViewById(R.id.tv_is_vip);
        mBg = (ImageView) view.findViewById(R.id.bg);
        view.findViewById(R.id.rl_Logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        String stringValue = SpUtil.getInstance().getStringValue(SpUtil.VEST_TEMP);
        if ("1".equals(stringValue)) {
            mBg.setImageDrawable(getResources().getDrawable(R.mipmap.ic_me_bg));
        } else {
            mBg.setImageDrawable(getResources().getDrawable(R.mipmap.ic_me_new_bg));
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
    }

    private void logout() {
        OkGo.<JsonBean>post(HttpUrl.LOGOUT)
                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        JsonBean bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                Map<String, String> map = new HashMap<>();
                                map.put(SpUtil.UID, "");
                                map.put(SpUtil.TOKEN, "");
                                SpUtil.getInstance().setMultiStringValue(map);
                                SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 0);
                                SpUtil.getInstance().setBooleanValue(SpUtil.SHOW_DIALOG,false);
                                SpUtil.getInstance().setBooleanValue(SpUtil.IS_LOGIN, false);
//                                LoginActivity.forward(getActivity());
                                if (getActivity() instanceof MainActivity) {
                                    SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "0");
                                    ((MainActivity) getActivity()).changetoHomeFragment();
                                }
                            } else if (("90000").equals(bean.getCode())) {
//                                ToastUtil.show(bean.getMsg());
                            }
                        }

                    }
                });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getUser();
        }
    }

    private void getUser() {
        OkGo.<UserJsonBean>post(HttpUrl.user)
                .execute(new CommonCallback<UserJsonBean>() {
                    @Override
                    public UserJsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), UserJsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<UserJsonBean> response) {
                        super.onSuccess(response);
                        UserJsonBean bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                UserData data = bean.getData();
                                mTvIphoneNum.setText(data.getUser_info().getMobile());
                                if ("0".equals(data.getUser_info().getIs_vip())) {
                                    mTvIsVip.setText("未开通会员");
                                } else {
                                    mTvIsVip.setText("会员");
                                }
                                MainSelfAdapter selfAdapter = new MainSelfAdapter(getActivity(), data.getService_list());
                                mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                mRecyclerView.setHasFixedSize(true);
                                mRecyclerView.setNestedScrollingEnabled(false);
                                mRecyclerView.setAdapter(selfAdapter);


                            } else if (("90000").equals(bean.getCode())) {
//                                ToastUtil.show(bean.getMsg());
                            } else if (("403").equals(bean.getCode())) {
                                SpUtil.getInstance().setBooleanValue(SpUtil.IS_LOGIN, false);
//                                LoginActivity.forward(getActivity());

                            }
                        }

                    }
                });
    }
}
