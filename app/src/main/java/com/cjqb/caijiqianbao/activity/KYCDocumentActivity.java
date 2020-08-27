package com.cjqb.caijiqianbao.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import com.bumptech.glide.Glide;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;

import com.cjqb.caijiqianbao.utils.ObjectEntry;
import com.cjqb.caijiqianbao.utils.PictureDialog;
import com.cjqb.caijiqianbao.utils.PictureSelectorUtils;

import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

/**
 * kyc提交信息页面
 * wmy
 */
public class KYCDocumentActivity extends BaseActivity {

    private TextView titleView;
    private ImageView btnBack;
    private ImageView kycDecumentFront;
    private ImageView kycDecumentBack;
    private ImageView kycDecumentPan;
    private TextView submitBtn;

    private String frontUrl;
    private String backUrl;
    private String panUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_decument_activity);
        initView();
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.titleView);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        kycDecumentFront = (ImageView) findViewById(R.id.kyc_decument_front);
        kycDecumentBack = (ImageView) findViewById(R.id.kyc_decument_back);
        kycDecumentPan = (ImageView) findViewById(R.id.kyc_decument_pan);
        submitBtn = (TextView) findViewById(R.id.submit_btn);


        SharedPreferences sharedPreferences= getSharedPreferences("test",
                Context.MODE_PRIVATE);
// 使用getString方法获得value，注意第2个参数是value的默认值
        String frontUrlImage =sharedPreferences.getString("frontUrl", "");
        String backUrlImage =sharedPreferences.getString("backUrl", "");
        String panUrlImage =sharedPreferences.getString("panUrl", "");

        if (!frontUrlImage.isEmpty()){
            Glide.with(this)
                    .load(frontUrlImage)
                    .into(kycDecumentFront);
        }

        if (!backUrlImage.isEmpty()){
            Glide.with(this)
                    .load(backUrlImage)
                    .into(kycDecumentBack);
        }

        if (!panUrlImage.isEmpty()){
            Glide.with(this)
                    .load(panUrlImage)
                    .into(kycDecumentPan);
        }




        kycDecumentFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureDialog pictureDialog = new PictureDialog(KYCDocumentActivity.this, new PictureDialog.PictureViewListener() {
                    @Override
                    public void getPhotoListener() {
                        PictureSelectorUtils.startPicSingleSelect(KYCDocumentActivity.this, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                String path = PictureSelectorUtils.getUploadPicPath(result.get(0));
                                getUpLoad(path , 1);
                                Glide.with(KYCDocumentActivity.this)
                                        .load(path)
                                        .into(kycDecumentFront);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }

                    @Override
                    public void getCameraListener() {
                        PictureSelectorUtils.startCameraSelect(KYCDocumentActivity.this, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                String path = PictureSelectorUtils.getUploadPicPath(result.get(0));
                                getUpLoad(path , 1);
                                Glide.with(KYCDocumentActivity.this)
                                        .load(path)
                                        .into(kycDecumentFront);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }
                });
                pictureDialog.show();
            }
        });

        kycDecumentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureDialog pictureDialog = new PictureDialog(KYCDocumentActivity.this, new PictureDialog.PictureViewListener() {
                    @Override
                    public void getPhotoListener() {
                        PictureSelectorUtils.startPicSingleSelect(KYCDocumentActivity.this, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                String path = PictureSelectorUtils.getUploadPicPath(result.get(0));
                                getUpLoad(path , 2);
                                Glide.with(KYCDocumentActivity.this)
                                        .load(path)
                                        .into(kycDecumentBack);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }

                    @Override
                    public void getCameraListener() {
                        PictureSelectorUtils.startCameraSelect(KYCDocumentActivity.this, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                String path = PictureSelectorUtils.getUploadPicPath(result.get(0));
                                getUpLoad(path , 2);
                                Glide.with(KYCDocumentActivity.this)
                                        .load(path)
                                        .into(kycDecumentBack);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }
                });
                pictureDialog.show();
            }
        });

        kycDecumentPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureDialog pictureDialog = new PictureDialog(KYCDocumentActivity.this, new PictureDialog.PictureViewListener() {
                    @Override
                    public void getPhotoListener() {
                        PictureSelectorUtils.startPicSingleSelect(KYCDocumentActivity.this, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                String path = PictureSelectorUtils.getUploadPicPath(result.get(0));
                                getUpLoad(path , 3);
                                Glide.with(KYCDocumentActivity.this)
                                        .load(path)
                                        .into(kycDecumentPan);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }

                    @Override
                    public void getCameraListener() {
                        PictureSelectorUtils.startCameraSelect(KYCDocumentActivity.this, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                String path = PictureSelectorUtils.getUploadPicPath(result.get(0));
                                getUpLoad(path , 3);
                                Glide.with(KYCDocumentActivity.this)
                                        .load(path)
                                        .into(kycDecumentPan);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }
                });

                pictureDialog.show();
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frontUrl != null && backUrl != null){
                    getSubmit();
                }else {
                    ToastUtil.show("Front and Back is not null!");
                }
            }
        });
    }


    private void getUpLoad(String path , int showType){
        File file = new File(path);
        OkGo.<ObjectEntry>post(HttpUrl.UPLOAD)
                .params("img", file)
                .execute(new CommonCallback<ObjectEntry>() {
                    @Override
                    public ObjectEntry convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), ObjectEntry.class);
                    }

                    @Override
                    public void onSuccess(Response<ObjectEntry> response) {
                        super.onSuccess(response);
                        ObjectEntry body = response.body();
                        if (body != null) {
                            if (("200").equals(body.getCode())) {
                                switch (showType){
                                    case 1:
                                        SharedPreferences sharedPreferences = getSharedPreferences("image" , Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                        editor.putString("frontUrl", response.body().getData().getImg());
                                        editor.commit();//提交修改
                                        frontUrl = response.body().getData().getImg();
                                        break;
                                    case 2:
                                        SharedPreferences sharedPreferencesSEcond = getSharedPreferences("image" , Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editorSecond = sharedPreferencesSEcond.edit();//获取编辑器
                                        editorSecond.putString("backUrl", response.body().getData().getImg());
                                        editorSecond.commit();
                                        //提交修改
                                        backUrl = response.body().getData().getImg();
                                        break;
                                    case 3:
                                        SharedPreferences sharedPreferencesThread = getSharedPreferences("image" , Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editorThread = sharedPreferencesThread.edit();//获取编辑器
                                        editorThread.putString("panUrl", response.body().getData().getImg());
                                        editorThread.commit();
                                        panUrl = response.body().getData().getImg();
                                        break;
                                }

                            }
                        }
                    }
                });
    }

    private void getSubmit(){
        OkGo.<JsonBean>post(HttpUrl.UPLOAD_IMAGE)
                .params("front_aadhaar_card", frontUrl)
                .params("back_aadhaar_card", backUrl)
                .params("pan_card", panUrl != null? panUrl :"")
                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        JsonBean body = response.body();
                        if (body != null) {
                            if (("200").equals(body.getCode())) {
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "BankInfo");
                                ToastUtil.show("Submit Success!");
                                finish();
                            }
                        }
                    }
                });
    }


}
