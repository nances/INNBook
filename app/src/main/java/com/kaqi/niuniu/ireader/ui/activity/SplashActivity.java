package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by newbiechen on 17-4-14.
 */

public class SplashActivity extends BaseActivity {
    public static final int SHOW_TIME = 0x01;
    public static final int GO_HOME = 0x02;
    private int countDownTime = 4 * 1000;
    @BindView(R.id.tvSkip)
    TextView tvSkip;
    @BindView(R.id.mIvGender)
    ImageView mIvGender;
    @BindView(R.id.mTvContent)
    TextView mTvContent;
    @BindView(R.id.mTvSelect)
    TextView mTvSelect;
    @BindView(R.id.mBtnMale)
    Button mBtnMale;
    @BindView(R.id.mBtnFemale)
    Button mBtnFemale;
    @BindView(R.id.choose_sex_rl)
    RelativeLayout chooseSexRl;
    @BindView(R.id.splash_ad_fl)
    FrameLayout splashAdFl;

    private boolean flag = false;
    private SplashCountDownTimer mCountDownTimer;


    private synchronized void goHome() {
        if (!flag) {
            flag = true;
            mCountDownTimer = null;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_splash;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreUtils.getInstance()
                .putBoolean(Constant.FITST_APP, false);
        mCountDownTimer = new SplashCountDownTimer(countDownTime, 1000);
        mCountDownTimer.start();
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mHandler.removeCallbacksAndMessages(null);
        flag = true;
    }


    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case SHOW_TIME:
                mCountDownTimer = new SplashCountDownTimer(countDownTime, 1000);
                mCountDownTimer.start();
                break;
            case GO_HOME:
                goHome();
                break;
        }
    }


    @OnClick({R.id.mTvContent, R.id.mTvSelect, R.id.mBtnMale, R.id.mBtnFemale, R.id.tvSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvContent:
                break;
            case R.id.mTvSelect:
                break;
            case R.id.mBtnMale:
                SharedPreUtils.getInstance()
                        .putString(Constant.SHARED_SEX, Constant.SEX_BOY);
                SharedPreUtils.getInstance()
                        .putBoolean(Constant.FITST_APP, true);
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }

                mHandler.sendEmptyMessageDelayed(GO_HOME, 100);
                break;
            case R.id.mBtnFemale:
                SharedPreUtils.getInstance()
                        .putBoolean(Constant.FITST_APP, true);
                SharedPreUtils.getInstance()
                        .putString(Constant.SHARED_SEX, Constant.SEX_GIRL);
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                mHandler.sendEmptyMessageDelayed(GO_HOME, 100);
                break;
            case R.id.tvSkip:
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                if ("".equals(SharedPreUtils.getInstance()
                        .getString(Constant.SHARED_SEX))) {
                    tvSkip.setVisibility(View.GONE);
                    splashAdFl.setVisibility(View.GONE);
                    chooseSexRl.setVisibility(View.VISIBLE);
                } else {
                    mHandler.sendEmptyMessageDelayed(GO_HOME, 100);
                }
                break;
        }
    }

    /**
     * 倒计时
     */
    private class SplashCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */

        public SplashCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            this.cancel();
            if (tvSkip == null) {
                tvSkip = (TextView) findViewById(R.id.tvSkip);
            }
            tvSkip.setText("0s 跳过");
            if ("".equals(SharedPreUtils.getInstance()
                    .getString(Constant.SHARED_SEX))) {
                tvSkip.setVisibility(View.GONE);
                splashAdFl.setVisibility(View.GONE);
                chooseSexRl.setVisibility(View.VISIBLE);
            } else {
                mHandler.sendEmptyMessageDelayed(GO_HOME, 100);
            }
        }

        public void onTick(final long millisUntilFinished) {
            try {
                tvSkip.setText(millisUntilFinished / 1000 + "s 跳过");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}