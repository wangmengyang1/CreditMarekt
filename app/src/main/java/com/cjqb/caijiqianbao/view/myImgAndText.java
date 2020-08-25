package com.cjqb.caijiqianbao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cjqb.caijiqianbao.R;

public class myImgAndText extends LinearLayout {

    public myImgAndText(Context context) {
        this(context,null);
    }

    public myImgAndText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public myImgAndText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myImgAndText);
        boolean isImgLeft = typedArray.getBoolean(R.styleable.myImgAndText_img_isLeft, false);
        boolean isImgRight = typedArray.getBoolean(R.styleable.myImgAndText_img_isRight, false);
        boolean isImgTop = typedArray.getBoolean(R.styleable.myImgAndText_img_isTop, false);
        boolean isImgBottom = typedArray.getBoolean(R.styleable.myImgAndText_img_isBottom, false);

        boolean isTextLeft = typedArray.getBoolean(R.styleable.myImgAndText_text_isLeft, false);
        boolean isTextRight = typedArray.getBoolean(R.styleable.myImgAndText_text_isRight, false);
        boolean isTextTop = typedArray.getBoolean(R.styleable.myImgAndText_text_isTop, false);
        boolean isTextBottom = typedArray.getBoolean(R.styleable.myImgAndText_text_isBottom, false);

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.img_text, null);
        ImageView img = linearLayout.findViewById(R.id.img);
        TextView text = linearLayout.findViewById(R.id.text);
    }
}
