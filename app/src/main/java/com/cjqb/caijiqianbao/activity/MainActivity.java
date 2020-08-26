package com.cjqb.caijiqianbao.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjqb.caijiqianbao.AlarmReceiver;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.loginBean.loginJsonData;
import com.cjqb.caijiqianbao.bean.vestBean.VestBean;
import com.cjqb.caijiqianbao.bean.vestBean.VestData;
import com.cjqb.caijiqianbao.fragment.FirstHomeFragment;
import com.cjqb.caijiqianbao.fragment.HomeFragment;
import com.cjqb.caijiqianbao.fragment.CreditFragment;
import com.cjqb.caijiqianbao.fragment.HomeNewFragment;
import com.cjqb.caijiqianbao.fragment.SelfFragment;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpClient;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.DialogUtil;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.cjqb.caijiqianbao.utils.LocationUtils;
import com.cjqb.caijiqianbao.utils.ProcessResultUtil;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import java.util.HashMap;
import java.util.Map;

import static com.cjqb.caijiqianbao.activity.AppleActivityStepTwo.isSetting;

public class MainActivity extends BaseActivity {

    private Fragment mHomeFragment;
    private Fragment mHomeNewFragment;
    private Fragment mCreditFragment;
    private Fragment mSelfFragment;
    private FragmentManager supportFragmentManager;
    private long firstPressedTime;
    private TextView mCreditTv, mSelfTv, mHomeTv, mVipTv;
    private ImageView mImgHome, mImgCredit, mImgSelf, mImgVip;
    private static final int HOME = 0;
    private static final int CREDIT = 1;
    private static final int SELF = 2;
    private static final int VIP = 3;
    private int mCurrentFragment;
    String[] mPremissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private ProcessResultUtil mProcessResultUtil;
    private View home_rb2;
    public View fl;
    private View home_rb_new;
    private Dialog dialog;
    private String url;

    public static void forward(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mHomeTv = findViewById(R.id.home_tv);
        mSelfTv = findViewById(R.id.self_tv);
        mCreditTv = findViewById(R.id.credit_tv);
        mImgHome = findViewById(R.id.img_home);
        mImgCredit = findViewById(R.id.img_loan);
        mImgSelf = findViewById(R.id.img_self);
        mImgVip = findViewById(R.id.img_vip);
        mVipTv = findViewById(R.id.vip_tv);

        home_rb2 = findViewById(R.id.home_rb2);
        home_rb_new = findViewById(R.id.home_rb_new);

        fl = findViewById(R.id.framelayout);
        //获取管理者
        supportFragmentManager = getSupportFragmentManager();
        changeTextColor(HOME);
//        changetoHomeFragment();
        mProcessResultUtil = new ProcessResultUtil(this);
        getVest();


        int intValue = SpUtil.getInstance().getIntValue(SpUtil.COUNT_DIALOG);

        if (intValue == 1 || intValue == 2) {
            SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 0);
            DialogActivity.forward(this);

//                SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, intValue + 1);
//            } else if (intValue == 2) {
//                DialogActivity.forward(this.getApplication());
//                SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, intValue + 1);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    private void getUser() {
        OkGo.<JsonBean>post(HttpUrl.user)
                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
//                        super.onSuccess(response);
                        JsonBean bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                SpUtil.getInstance().setBooleanValue(SpUtil.IS_LOGIN, true);
                            }
                            changetoHomeFragment();
//                            if (mHomeNewFragment != null)
//                                ((HomeNewFragment) mHomeNewFragment).init();
                            //TODO
//                            startPayment();
                        }

                    }
                });
    }


    private void getVest() {
        OkGo.<VestBean>post(HttpUrl.VEST)
                .execute(new CommonCallback<VestBean>() {
                                 @Override
                    public VestBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), VestBean.class);
                    }

                    @Override
                    public void onSuccess(Response<VestBean> response) {
                        super.onSuccess(response);
                        VestBean bean = response.body();
                        if (bean != null) {
                            //"vest_value":开启马甲包：开启１ 关闭０
                            //"vest_temp":APP模板 ：模板一 １，模板二 ２
                            //"vest_early":是否前期：开启１ 关闭０
                            VestData data = bean.getData();
//                            url = "Asdadsadsasd";
//                            showUpdata();

                            String vest_early = data.getVest_early();
                            String vest_temp = data.getVest_temp();
                            String vest_value = data.getVest_value();
                            SpUtil.getInstance().setStringValue(SpUtil.VEST_VALUE, vest_value);
                            SpUtil.getInstance().setStringValue(SpUtil.VEST_TEMP, vest_temp);
                            SpUtil.getInstance().setStringValue(SpUtil.VEST_EARLY, vest_early);
                            getUser();

                        }

                    }
                });
    }
    //258077

    public void showUpdata() {
        dialog = DialogUtil.commonDialog(this, R.layout.dialog_updata);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
//        mEtCode = dialog.findViewById(R.id.et_code);
        dialog.setCancelable(false);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (ScreenDimenUtil.getInstance().getScreenWdith()) - DpUtil.dp2px(this, 100);
        dialog.getWindow().setAttributes(p);
        dialog.show();

    }

    public void submit(View v) {
        if (url.contains("apk")) {
            if (url.endsWith(".apk")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
            if (url.contains("?")) {
                String substring = url.substring(0, url.indexOf("?"));
                if (substring.endsWith("apk")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            }
        }

    }

    private void initTextColre() {
        mHomeTv.setTextColor(getResources().getColor(R.color.gray1));
        mCreditTv.setTextColor(getResources().getColor(R.color.gray1));
        mSelfTv.setTextColor(getResources().getColor(R.color.gray1));
        mVipTv.setTextColor(getResources().getColor(R.color.gray1));

        mImgHome.setImageDrawable(getResources().getDrawable(R.mipmap.nav_home_nor));
        mImgCredit.setImageDrawable(getResources().getDrawable(R.mipmap.nav_loan_nor));
        mImgSelf.setImageDrawable(getResources().getDrawable(R.mipmap.nav_mine_nor));
        mImgVip.setImageDrawable(getResources().getDrawable(R.mipmap.ic_vip));
    }

    private void hideFrag() {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (mHomeFragment != null && mHomeFragment.isAdded()) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mHomeNewFragment != null && mHomeNewFragment.isAdded()) {
            fragmentTransaction.hide(mHomeNewFragment);
        }
        if (mCreditFragment != null && mCreditFragment.isAdded()) {
            fragmentTransaction.hide(mCreditFragment);
        }
        if (mSelfFragment != null && mSelfFragment.isAdded()) {
            fragmentTransaction.hide(mSelfFragment);
        }
        fragmentTransaction.commit();
    }

    //切换到HomeFragment
    public void changetoHomeFragment() {
        hideFrag();
        changeTextColor(0);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();


//        if (SpUtil.getInstance().getStringValue(SpUtil.IS_VIP).equals("1") && (SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN))){
//            home_rb2.setVisibility(View.VISIBLE);
//        } else{
//            home_rb2.setVisibility(View.GONE);
//        }
//        if (SpUtil.getInstance().getStringValue(SpUtil.IS_VIP).equals("1")&&(SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN))) {
//            if (mHomeFragment == null) {
//                mHomeFragment = new HomeFragment();
//                fragmentTransaction.add(R.id.framelayout, mHomeFragment).commit();
//            } else {
//                fragmentTransaction.show(mHomeFragment).commit();
//                mCurrentFragment = 1;
//            }
//        } else {
//        if (mHomeNewFragment == null) {
//            mHomeNewFragment = new HomeNewFragment();
//            fragmentTransaction.add(R.id.framelayout, mHomeNewFragment).commit();
//        } else {
//            fragmentTransaction.show(mHomeNewFragment).commit();
//            mCurrentFragment = 1;
//        }
//        }
        //"vest_value":开启马甲包：开启１ 关闭０
        //"vest_temp":APP模板 ：模板一 １，模板二 ２
        //"vest_early":是否前期：开启１ 关闭０
        if (SpUtil.getInstance().getStringValue(SpUtil.VEST_EARLY).equals("0")) {
            setGoHomeFragment(fragmentTransaction);
            return;
        }
        if (SpUtil.getInstance().getStringValue(SpUtil.VEST_VALUE).equals("0")) {
            setNextHomeFragment(fragmentTransaction);
        } else {
            setNewHomeFragment(fragmentTransaction);
        }
    }

    //TODO 先走认证然后打开贷款
    public void setNextHomeFragment(FragmentTransaction fragmentTransaction) {
        if (SpUtil.getInstance().getStringValue(SpUtil.IS_VIP).equals("1") && (SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN))) {
            home_rb2.setVisibility(View.VISIBLE);
        } else {
            home_rb2.setVisibility(View.GONE);
        }
        if (SpUtil.getInstance().getStringValue(SpUtil.IS_VIP).equals("1") && (SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN))) {
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.framelayout, mHomeFragment).commit();
            } else {
                fragmentTransaction.show(mHomeFragment).commit();
                mCurrentFragment = 1;
            }
        } else {
            if (mHomeNewFragment == null) {
                mHomeNewFragment = new FirstHomeFragment();
                fragmentTransaction.add(R.id.framelayout, mHomeNewFragment).commit();
            } else {
                fragmentTransaction.show(mHomeNewFragment).commit();
                mCurrentFragment = 1;
            }
        }
    }

    //TODO 直接打开贷款
    public void setGoHomeFragment(FragmentTransaction fragmentTransaction) {
        home_rb2.setVisibility(View.VISIBLE);
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.framelayout, mHomeFragment).commit();
        } else {
            fragmentTransaction.show(mHomeFragment).commit();
            mCurrentFragment = 1;
        }
    }

    //TODO 直接认证不开贷款
    public void setNewHomeFragment(FragmentTransaction fragmentTransaction) {
        home_rb2.setVisibility(View.GONE);
        if (mHomeNewFragment == null) {
            mHomeNewFragment = new FirstHomeFragment();
            fragmentTransaction.add(R.id.framelayout, mHomeNewFragment).commit();
        } else {
            fragmentTransaction.show(mHomeNewFragment).commit();
            mCurrentFragment = 1;
        }
    }

    //切换到creditFragment
    public void changetoCreditFragment() {
        hideFrag();
        changeTextColor(1);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (mCreditFragment == null) {
            mCreditFragment = new CreditFragment();
            fragmentTransaction.add(R.id.framelayout, mCreditFragment).commit();
        } else {
            fragmentTransaction.show(mCreditFragment).commit();
            mCurrentFragment = 2;
        }
    }


    //切换到SelfFragment
    public void changetoSelfFragment() {
        if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
            LoginActivity.forward(this);
        } else {
            hideFrag();
            changeTextColor(2);
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            if (mSelfFragment == null) {
                mSelfFragment = new SelfFragment();
                fragmentTransaction.add(R.id.framelayout, mSelfFragment).commit();
            } else {
                fragmentTransaction.show(mSelfFragment).commit();
                mCurrentFragment = 3;
            }

        }
    }

    //tab点击事件 切换Fragment
    public void changeFragment(View v) {
        switch (v.getId()) {
            case R.id.home_rb1:
                changetoHomeFragment();
                break;
            case R.id.home_rb2:
                changetoCreditFragment();
                break;
            case R.id.home_rb_new:
                VipActivity.forward(this);
                break;
            case R.id.home_rb3:
//                if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
//                    LoginActivity.forward(this);
//                } else {
                changetoSelfFragment();
//                }
                break;
        }
    }

    private void changeTextColor(int id) {
        initTextColre();
        switch (id) {
            case HOME:
                mHomeTv.setTextColor(getResources().getColor(R.color.orange));
                mImgHome.setImageDrawable(getResources().getDrawable(R.mipmap.nav_home_sel));
                break;
            case CREDIT:
                mCreditTv.setTextColor(getResources().getColor(R.color.orange));
                mImgCredit.setImageDrawable(getResources().getDrawable(R.mipmap.nav_loan_sel));
                break;
            case SELF:
                mSelfTv.setTextColor(getResources().getColor(R.color.orange));
                mImgSelf.setImageDrawable(getResources().getDrawable(R.mipmap.nav_mine_sel));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed();
            System.exit(0);
        } else {
            ToastUtil.show("再按一次退出");
            firstPressedTime = System.currentTimeMillis();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSetting == 1) {
            isSetting = isSetting + 1;
            check();
        }
        if (mCurrentFragment == 3) return;
        changetoHomeFragment();
    }

    public void check() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            showContacts();
            LocationUtils.initLocation(this);
//            showSms();
        } else {
            init();
        }
    }

    private void init() {
        mProcessResultUtil.requestPermissions(mPremissions, new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasAllGranted = true;
        int pos = 0;
        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                pos = i;
                break;
            }
        }
        if (hasAllGranted) { //同意权限做的处理,开启服务提交通讯录
//            showContacts();
//            showSms();
            LocationUtils.initLocation(this);
        } else {    //拒绝授权做的处理，弹出弹框提示用户授权
            dealwithPermiss(this, permissions[pos]);
        }
    }

    public void dealwithPermiss(final Activity context, final String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("操作提示")
                    .setCancelable(false)
                    .setMessage("注意：当前缺少必要权限！\n请点击“设置”-“权限”-打开所需权限")
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isSetting = 1;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dealwithPermiss(MainActivity.this, permission);
                        }
                    })
                    .show();

        } else {
            check();
        }
    }




//    public void startPayment() {
//        /**
//         * Instantiate Checkout
//         */
//        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_test_VKD76XDXkPW0UY");
//        /**
//         * Set your logo here
//         */
//        checkout.setImage(R.drawable.icon_login_ff);
//
//        /**
//         * Reference to current activity
//         */
//        final Activity activity = this;
//
//        /**
//         * Pass your payment options to the Razorpay Checkout as a JSONObject
//         */
//        try {
//            org.json.JSONObject options = new org.json.JSONObject();
//
//            options.put("data-name", "Leding Hub");
//            options.put("data-key", "FRme9fm6S9vxY1");
//            options.put("data-description", "Reference No. #123456");
//            options.put("data-image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("data-order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
//            options.put("theme.color", "#3399cc");
//            options.put("data-currency", "INR");
//            options.put("data-amount", "50000");//pass amount in currency subunits
//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","+919977665544");
//            checkout.open(activity, options);
//        } catch(Exception e) {
//            Log.e("show", "Error in starting Razorpay Checkout", e);
//        }
//    }
//
//
//    @Override
//    public void onPaymentSuccess(String s) {
//        Log.e("show" , s  + "成功");
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Log.e("show" , s  + "失败");
//    }

}
