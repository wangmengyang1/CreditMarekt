package com.cjqb.caijiqianbao.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cjqb.caijiqianbao.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.permissions.PermissionChecker;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 信息收集
 * wmy
 * */
public class ProFileInformationActivity extends BaseActivity{


    private RelativeLayout userLayout;
    private RelativeLayout doucumentsLayout;
    private RelativeLayout bankLayout;


    private ImageView basicImage;
    private ImageView doucumentsImage;
    private ImageView bankImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_information_activity);


        // 验证相机权限和麦克风权限
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }

        userLayout = findViewById(R.id.basic_layout);
        doucumentsLayout = findViewById(R.id.doucuments_layout);
        bankLayout = findViewById(R.id.bank_layout);
        basicImage = findViewById(R.id.basic_image);
        doucumentsImage = findViewById(R.id.doucuments_image);
        bankImage = findViewById(R.id.bank_image);
        initIntent();

    }

    private void initIntent() {
        if (getIntent() != null){
            String stage = getIntent().getStringExtra("STAGE");
//            switch (stage){
//                case "BasicInfo":
//                    //基础信息
//                    //基础信息
//                    userLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(ProFileInformationActivity.this , BasicInitActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//                    //文档信息
//                    doucumentsLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(ProFileInformationActivity.this , KYCDocumentActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//                    //银行卡资料
//                    bankLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(ProFileInformationActivity.this , BankActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    break;
//                case "ContactInfo":
//                    //联系方式
//                    basicImage.setImageResource(R.drawable.icon_success);
//
//                    //文档信息
//                    userLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(ProFileInformationActivity.this , KYCDocumentActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//                    //银行卡资料
//                    doucumentsLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(ProFileInformationActivity.this , BankActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    break;
//                case "BankInfo":
//                    //银行卡信息
//                    basicImage.setImageResource(R.drawable.icon_success);
//                    doucumentsImage.setImageResource(R.drawable.icon_success);
//                    //文档信息
//                    userLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(ProFileInformationActivity.this , KYCDocumentActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//                    //银行卡资料
//                    bankLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(ProFileInformationActivity.this , BankActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    break;
//            }


            userLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProFileInformationActivity.this , BasicInitActivity.class);
                    startActivity(intent);
                }
            });

            //文档信息
            doucumentsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ProFileInformationActivity.this , KYCDocumentActivity.class);
                    startActivity(intent);
                }
            });

            //银行卡资料
            bankLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProFileInformationActivity.this , BankActivity.class);
                    startActivity(intent);
                }
            });

        }
    }



    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onThreadMain(MessageEvent messageEvent){
        if (messageEvent.equals(MessageEvent.BASIC_INIT)){
            basicImage.setImageResource(R.drawable.icon_success);
        }
    }

}
