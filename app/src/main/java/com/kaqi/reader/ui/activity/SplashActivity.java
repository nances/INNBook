/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaqi.reader.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.manager.EventManager;
import com.kaqi.reader.manager.SettingManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 启动页
 * 1、无网络状态下，3秒后进入首页
 * 2、有网络状态下，请求数据
 * 3、请求的网络数据时间小于1.5秒，在1.5秒后展示，大于3秒，直接进入首页
 * 4、请求网络数据返回 展示服务器广告，则根据服务器的广告显示逻辑处理
 * 5、请求网络数据返回 展示第三方SDK广告，则显示第三方SDK广告
 */
public class SplashActivity extends BaseActivity {

    public static final int SHOW_TIME = 0x01;
    public static final int GO_HOME = 0x02;
    @Bind(R.id.tvSkip)
    TextView tvSkip;
    @Bind(R.id.mIvGender)
    ImageView mIvGender;
    @Bind(R.id.mTvContent)
    TextView mTvContent;
    @Bind(R.id.mTvSelect)
    TextView mTvSelect;
    @Bind(R.id.mBtnMale)
    Button mBtnMale;
    @Bind(R.id.mBtnFemale)
    Button mBtnFemale;
    @Bind(R.id.choose_sex_rl)
    RelativeLayout chooseSexRl;
    @Bind(R.id.splash_img)
    ImageView splashImg;

    private boolean flag = false;
    private SplashCountDownTimer mCountDownTimer;
    private int countDownTime = 4 * 1000;

    private synchronized void goHome() {
        if (!flag) {
            flag = true;
            mCountDownTimer = null;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
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
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

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

    @Override
    public void initToolBar() {
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void configViews() {

        mCountDownTimer = new SplashCountDownTimer(countDownTime, 1000);
        mCountDownTimer.start();
    }


    @OnClick({R.id.mTvContent, R.id.mTvSelect, R.id.mBtnMale, R.id.mBtnFemale, R.id.tvSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mTvContent:
                break;
            case R.id.mTvSelect:
                break;
            case R.id.mBtnMale:
                SettingManager.getInstance().saveUserChooseSex(Constant.Gender.MALE);
            case R.id.mBtnFemale:
                SettingManager.getInstance().saveUserChooseSex(Constant.Gender.FEMALE);
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                mHandler.sendEmptyMessageDelayed(GO_HOME, 100);
                break;
            case R.id.tvSkip:
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                if (!SettingManager.getInstance().isUserChooseSex()) {
                    gone(tvSkip, splashImg);
                    visible(chooseSexRl);
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
            if (!SettingManager.getInstance().isUserChooseSex()) {
                gone(tvSkip, splashImg);
                visible(chooseSexRl);
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
