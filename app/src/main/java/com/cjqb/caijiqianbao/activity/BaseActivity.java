package com.cjqb.caijiqianbao.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends AppCompatActivity {
    private String mTag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = this.getClass().getSimpleName();
        setStatusBar();
    }

    protected void setTime(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int intValue = SpUtil.getInstance().getIntValue(SpUtil.COUNT_DIALOG);
                if (intValue == 1) {
                    DialogActivity.forward(BaseActivity.this.getApplication());
                    SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, intValue + 1);

                }
            }
        },10 * 1000);
    }
    /**
     * 设置透明状态栏
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (isStatusBarWhite()) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0);
        }
    }

    public void backClick(View v) {
        if (v.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }

    protected void setTitle(String titile) {
        TextView title = findViewById(R.id.titleView);
        title.setText(titile);
    }


    protected boolean isStatusBarWhite() {
        return false;
    }

    // Activity页面onResume函数重载
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏

    }

    // Activity页面onResume函数重载
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }
}
