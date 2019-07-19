package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.CleanableEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by niqiao on 2019年03月25日21:48:39
 * niqiao1111@163.com
 * <p/>
 * 登录界面
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.user_txt)
    CleanableEditText userTxt;
    @BindView(R.id.password_txt)
    CleanableEditText passwordTxt;
    @BindView(R.id.find_password_tx)
    TextView findPasswordTx;
    @BindView(R.id.sms_login_tx)
    TextView smsLoginTx;
    @BindView(R.id.login_tx)
    TextView loginTx;
    @BindView(R.id.login_lr)
    LinearLayout loginLr;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    String loadingTip = "加载中";

    @Override
    protected int getContentId() {
        return R.layout.activity_login;
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonToolbar.setTitleText("登陆");
        commonToolbar.setBackVisibility(true);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    /**
     * 登陆
     */
    private void login() {

        String name = userTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show("密码不能为空");
            return;
        }

    }

    @OnClick({R.id.sms_login_tx, R.id.login_tx, R.id.find_password_tx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sms_login_tx:

                break;
            case R.id.login_tx:
                //登录
                login();
                break;
            case R.id.find_password_tx:
                break;
        }
    }


}
