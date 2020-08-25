package com.cjqb.caijiqianbao.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.cjqb.caijiqianbao.AlarmReceiver;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;

public class DialogActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private TextView mTvIphoneNum;
    private TextView mTvIsVip;
    private View mPopuView;
    private PopupWindow popupWindow;
    private ImageView mBg;
    private View mRl;

    public static void forward(Context context) {
        Intent intent = new Intent(context, DialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mRl = findViewById(R.id.rl);
        ViewGroup.LayoutParams layoutParams = mRl.getLayoutParams();
        if (layoutParams != null) {
            int width = (int) (ScreenDimenUtil.getInstance().getScreenWdith() * 0.8);
            layoutParams.width = width;
            layoutParams.height = width * 602 / 531;
            mRl.setLayoutParams(layoutParams);
        }
//        Intent intent = new Intent(this,
//                AlarmReceiver.class);
//        PendingIntent sender = PendingIntent.getBroadcast(
//                this, 0, intent, 0);
//
//        // And cancel the alarm.
//        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am.cancel(sender);

    }

    public void back(View v) {
//        int intValue = SpUtil.getInstance().getIntValue(SpUtil.COUNT_DIALOG);
//        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, intValue + 1);
        onBackPressed();

    }

    public void next(View v) {
        VipActivity.forward(this);
        finish();

//        int intValue = SpUtil.getInstance().getIntValue(SpUtil.COUNT_DIALOG);
//        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, intValue + 1);
//        onBackPressed();
//        SpUtil.getInstance().setBooleanValue(SpUtil.SHOW_DIALOG,true);
//        AppleActivityStepThree.forward(DialogActivity.this);

    }
}
