package com.cjqb.caijiqianbao.activity;

import android.content.Context;
import android.content.Intent;
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

import com.cjqb.caijiqianbao.Constants;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.adapter.ChanneAdapter;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;

public class VipActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private TextView mTvIphoneNum;
    private TextView mTvIsVip;
    private View mPopuView;
    private PopupWindow popupWindow;
    private ImageView mBg;

    public static void forward(Context context) {
        Intent intent = new Intent(context, VipActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vip);
        setTitle("VIP");
        ImageView img = findViewById(R.id.img);

        ImgLoader.display2(this, "http://47.114.53.124/static/images/1.png", img);
        findViewById(R.id.tv_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil.getInstance().setBooleanValue(SpUtil.SHOW_DIALOG, true);
                AppleActivityStepThree.forward(VipActivity.this);
                finish();
//                if (SpUtil.getInstance().getStringValue(SpUtil.IS_VIP).equals("1") && (SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN))) {
//                    ToastUtil.show("你已成为会员，请进入我的权益进行操作");
//                    return;
//                }
//                if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
//                    LoginActivity.forward(VipActivity.this);
//                } else {
//                    String stage = SpUtil.getInstance().getStringValue(SpUtil.STAGE);
//                    if ("BasicInfo".equals(stage)) {
//                        AppleActivityStepFirst.forward(VipActivity.this);
//                    } else if ("ContactInfo".equals(stage)) {
//                        AppleActivityStepTwo.forward(VipActivity.this);
//                    } else if ("BankInfo".equals(stage)) {
//                        AppleActivityStepThree.forward(VipActivity.this);
//                    } else if ("OrderInfo".equals(stage)) {
//                        AppleActivityStepEnd.forward(VipActivity.this, SpUtil.getInstance().getStringValue(SpUtil.BANK_CARD_NUMBER));
//                    }
//                }
            }
        });
    }
}
