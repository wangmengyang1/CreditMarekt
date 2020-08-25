package com.cjqb.caijiqianbao.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cjqb.caijiqianbao.R;

public class PictureDialog extends Dialog {
    private TextView pictureDialogPhoto;
    private TextView pictureDialogCamera;
    private PictureViewListener pictureViewListener;

    public interface PictureViewListener{
        void getPhotoListener();
        void getCameraListener();
    }

    public PictureDialog(@NonNull Context context , PictureViewListener pictureViewListener) {
        super(context);
        this.pictureViewListener = pictureViewListener;
    }

    public PictureDialog(@NonNull Context context, int themeResId , PictureViewListener pictureViewListener) {
        super(context, themeResId);
        this.pictureViewListener = pictureViewListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_dialog);
        initView();
        initDialog();
    }


    private void initDialog() {
        setCanceledOnTouchOutside(true);
        //设置宽度为屏幕宽度
        Window window = getWindow();
        //设置dialog位置
        window.setGravity(Gravity.BOTTOM);
        //消除边距
        window.getDecorView().setPadding(0, 0, 0, 0);
//        window.setWindowAnimations(R.style.dialog_anmia);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        layoutParams.horizontalMargin = 0;
        window.setAttributes(layoutParams);
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
    }

    private void initView() {
        pictureDialogPhoto = (TextView) findViewById(R.id.picture_dialog_photo);
        pictureDialogCamera = (TextView) findViewById(R.id.picture_dialog_camera);

        pictureDialogPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureViewListener.getPhotoListener();
                dismiss();
            }
        });

        pictureDialogCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureViewListener.getCameraListener();
                dismiss();
            }
        });
    }
}
