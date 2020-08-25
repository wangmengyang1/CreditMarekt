package com.cjqb.caijiqianbao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;

public class UniformScaleTransformation extends ImageViewTarget<Bitmap> {

    private final Context mContext;
    private ImageView target;

    public UniformScaleTransformation(ImageView target, Context context) {
        super(target);
        this.target = target;
        mContext = context;
    }

    @Override
    protected void setResource(Bitmap resource) {

        if(resource == null){
            return;
        }

        view.setImageBitmap(resource);

        //获取原图的宽高
        int width = resource.getWidth();
        int height = resource.getHeight();

        //获取imageView的宽
        int imageViewWidth = target.getWidth();

        //计算缩放比例
        float sy = (float) (imageViewWidth * 0.1) / (float) (width * 0.1);

        //计算图片等比例放大后的高
        int imageViewHeight = (int) (height * sy);
        ViewGroup.LayoutParams params = target.getLayoutParams();
//        params.height = imageViewHeight;
        params.width = DpUtil.dp2px(mContext, (width / 5) * 2);
        params.height = DpUtil.dp2px(mContext, (height / 5) * 2);
        target.setLayoutParams(params);
    }
}