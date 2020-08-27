package com.cjqb.caijiqianbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.utils.SpUtil;

/**
 * 借款额度页面
 * wmy
 * */
public class BorrowingActivity extends BaseActivity{

    private TextView shop_vip_act_btn;
    private TextView amount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowing_activity);
        amount = findViewById(R.id.borrowing_act_amount);
        amount.setText(SpUtil.getInstance().getStringValue(SpUtil.LOAN_AMOUNT) + "");
        shop_vip_act_btn = findViewById(R.id.shop_vip_act_btn);
        shop_vip_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowingActivity.this , ShopVIPActivity.class);
                startActivity(intent);
                BorrowingActivity.this.finish();
            }
        });
    }
}
