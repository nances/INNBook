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
import com.kaqi.reader.manager.SettingManager;

import butterknife.Bind;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

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
    private Runnable runnable;
    public static final int SHOW_TIME = 0x01;

    private synchronized void goHome() {
        if (!flag) {
            flag = true;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                if (!SettingManager.getInstance().isUserChooseSex()) {
                    gone(tvSkip, splashImg);
                    visible(chooseSexRl);
                } else {
                    goHome();
                }
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
        mHandler.sendEmptyMessageDelayed(SHOW_TIME, 2000);
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
                goHome();
                break;
            case R.id.tvSkip:
                if (!SettingManager.getInstance().isUserChooseSex()) {
                    gone(tvSkip, splashImg);
                    visible(chooseSexRl);
                } else {
                    goHome();
                }
                break;
        }
    }
}
