package com.kaqi.reader.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaqi.reader.R;


public class NormalTitleBar extends RelativeLayout {

    private ImageView ivRight, ivBack;
    private TextView tvBack, tvTitle, tvRight;
    private RelativeLayout rlCommonTitle;
    private Context context;

    public NormalTitleBar(Context context) {
        super(context, null);
        this.context = context;
    }

    public NormalTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.bar_normal, this);
        tvBack = (TextView) findViewById(R.id.tv_back);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.image_right);
        rlCommonTitle = (RelativeLayout) findViewById(R.id.common_title);
        tvTitle.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        tvTitle.getPaint().setStrokeWidth(1.1f);
        //setHeaderHeight();
        tvBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setBackground() {
        this.setBackgroundColor(Color.WHITE);
    }

    /**
     * 管理返回按钮
     */
    public void setBackVisibility(boolean visible) {
        if (visible) {
            tvBack.setVisibility(View.VISIBLE);
        } else {
            tvBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     *
     * @param visiable
     */
    public void setTvLeftVisiable(boolean visiable) {
        if (visiable) {
            tvBack.setVisibility(View.VISIBLE);
        } else {
            tvBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     *
     * @param tvLeftText
     */
    public void setTvLeft(String tvLeftText) {
        tvBack.setText(tvLeftText);
    }


    /**
     * 管理标题
     */
    public void setTitleVisibility(boolean visible) {
        if (visible) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String string) {
        tvTitle.setText(string);
    }

    public void setTitleText(int string) {
        tvTitle.setText(string);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * 右图标
     */
    public void setRightImagVisibility(boolean visible) {
        ivRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightImagSrc(Drawable id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setBackgroundDrawable(id);
    }

    /**
     * 获取右按钮
     *
     * @return
     */
    public View getRightImage() {
        return ivRight;
    }

    /**
     * 左图标
     *
     * @param id
     */
    public void setLeftImagSrc(int id) {
        tvBack.setCompoundDrawables(getResources().getDrawable(id), null, null, null);
    }
    public void setIvBackImage(int id){
        ivBack.setBackgroundResource(id);
    }
    /**
     * 左文字
     *
     * @param str
     */
    public void setLeftTitle(String str) {
        tvBack.setText(str);
    }

    /**
     * 右标题
     */
    public void setRightTitleVisibility(boolean visible) {
        tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightTitle(String text) {
        tvRight.setText(text);
    }

    public void setRightTitle(CharSequence text) {
        tvRight.setText(text);
    }

    public void setRightTitle(@StringRes int text) {
        tvRight.setText(text);
    }

    public void setRightTitleColor(int color) {
        tvRight.setTextColor(color);
    }

    public void setRightTitleSize(float color) {
        tvRight.setTextSize(color);
    }


    /*
     * 点击事件
     */
    public void setOnBackListener(OnClickListener listener) {
//        tvBack.setOnClickListener(listener);
        ivBack.setOnClickListener(listener);
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        rlCommonTitle.setBackgroundColor(color);
    }

    public Drawable getBackGroundDrawable() {
        return rlCommonTitle.getBackground();
    }


}
