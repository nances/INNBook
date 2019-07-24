package com.kaqi.niuniu.ireader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 个人中心顶部波浪背景
 * Created by niqiao on 2017/7/21.
 */

public class MineWaveView extends View {
    private int mWaveLength1;
    private int mWaveLength2;
    private int mWaveLength3;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mCenter1Y;
    private int mCenter2Y;
    private int mCenter3Y;
    private int mWave1Count;
    private int mWave2Count;
    private int mWave3Count;

    //不全为0，起始错位
    private int offset1 = 200;
    private int offset2 = 400;
    private int offset3;

    private Path mPath;
    private Paint mPaint;
    private ValueAnimator mAnim1;
    private ValueAnimator mAnim2;
    private ValueAnimator mAnim3;

    public MineWaveView(Context context) {
        super(context);
    }

    public MineWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(8);
        mWaveLength1 = 650;
        mWaveLength2 = 800;
        mWaveLength3 = 950;
    }

    public MineWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 需要计算出屏幕能容纳多少个波形
        mPath = new Path();
        mScreenHeight = h;
        mScreenWidth = w;
        mCenter1Y = h * 7 / 10;
        mCenter2Y = h * 4 / 5;
        mCenter3Y = h * 9 / 10;
        mWave1Count = (int) Math.round(mScreenWidth / mWaveLength1 + 1.5); // 计算波形的个数
        mWave2Count = (int) Math.round(mScreenWidth / mWaveLength2 + 1.5);
        mWave3Count = (int) Math.round(mScreenWidth / mWaveLength3 + 1.5);
//        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setWave(canvas, mWaveLength1, offset1, mCenter1Y, 70, "#20FFFFFF", mWave1Count);
        setWave(canvas, mWaveLength2, offset2, mCenter2Y, 60, "#50FFFFFF", mWave2Count);
        setWave(canvas, mWaveLength3, offset3, mCenter3Y, 30, "#FFFFFF", mWave3Count);
    }

    private void setWave(Canvas canvas, int waveLength, int offset,
                         int centerY, int yWaveHeight, String color, int count) {
        mPath.reset();
        mPaint.setColor(Color.parseColor(color));
        mPath.moveTo(-waveLength + offset, centerY);
        for (int i = 0; i < count; i++) {
            mPath.quadTo(-waveLength * 3 / 4 + i * waveLength + offset, centerY + yWaveHeight,
                    -waveLength / 2 + i * waveLength + offset, centerY);
            mPath.quadTo(-waveLength / 4 + i * waveLength + offset, centerY - yWaveHeight,
                    i * waveLength + offset, centerY);
        }
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }


    public void startAnim() {
        mAnim1 = getAnim(mWaveLength1, 4000, offset1);
        mAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset1 = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnim1.start();

        mAnim2 = getAnim(mWaveLength2, 3500, offset2);
        mAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset2 = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnim2.start();

        mAnim3 = getAnim(mWaveLength3, 4500, offset3);
        mAnim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset3 = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnim3.start();
    }

    private ValueAnimator getAnim(int waveLength, int duration, int offset) {
        ValueAnimator animator = ValueAnimator.ofInt(0, waveLength);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        return animator;
    }

    public void stopAnim() {
        stopTheAnim(mAnim1);
        stopTheAnim(mAnim2);
        stopTheAnim(mAnim3);
    }

    private void stopTheAnim(ValueAnimator anim) {
        if (null != anim) {
            anim.cancel();
        }
    }
}
