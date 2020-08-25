package com.cjqb.caijiqianbao.utils;

import android.content.Context;


/**
 * Created by cxf on 2017/8/9.
 * dp转px工具类
 */

public class DpUtil {

    public static int dp2px(Context context, int dpVal) {
        return (int) (context.getResources().getDisplayMetrics().density * dpVal + 0.5f);
    }
}
