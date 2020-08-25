package com.cjqb.caijiqianbao.utils;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.cjqb.caijiqianbao.R;

import java.lang.reflect.Method;

/**
 * Created by cxf on 2017/8/8.
 */
public class DialogUtil {

    public static Dialog commonDialog(Context context, int layout) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(layout);
//        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static PopupWindow showPopuWindow(final View anchor, View popuView) {
        // 显示下拉选择框（放入的控件，宽，高）
        PopupWindow mPopupWindow = new PopupWindow(popuView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        // 设置点击外部区域, 自动隐藏
        mPopupWindow.setOutsideTouchable(true); // 外部可触摸
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置空的背景, 响应点击事件

        mPopupWindow.setFocusable(true); //设置可获取焦点

//        showPopu(mPopupWindow,anchor);
        return mPopupWindow;
    }
    public static PopupWindow showPopuWindow( View popuView) {
        // 显示下拉选择框（放入的控件，宽，高）
        PopupWindow mPopupWindow = new PopupWindow(popuView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        // 设置点击外部区域, 自动隐藏
        mPopupWindow.setOutsideTouchable(true); // 外部可触摸
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置空的背景, 响应点击事件

        mPopupWindow.setFocusable(true); //设置可获取焦点

//        showPopu(mPopupWindow,anchor);
        return mPopupWindow;
    }
    public  static void showPopu(PopupWindow mPopupWindow,View anchor){
        if (Build.VERSION.SDK_INT < 24) {
            mPopupWindow.showAsDropDown(anchor);
        } else {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            mPopupWindow.setHeight(height);
//            mPopupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
//            mPopupWindow.
            mPopupWindow.showAsDropDown(anchor);
        }
    }



    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }
}