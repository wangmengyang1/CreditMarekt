package com.cjqb.caijiqianbao;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cjqb.caijiqianbao.activity.DialogActivity;
import com.cjqb.caijiqianbao.utils.SpUtil;

import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 这里收到定时广播，处理定时任务
        Log.d("方法六", "6-------6");
//        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 1);
        int intValue = SpUtil.getInstance().getIntValue(SpUtil.COUNT_DIALOG);
        if (intValue == 1) {
            DialogActivity.forward(context);
            SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, intValue + 1);

        }
    }
}
