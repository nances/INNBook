package com.kaqi.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.utils.NormalTitleBar;
import com.kaqi.reader.utils.ToastUtils;
import com.kaqi.reader.view.CleanableEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by niqiao on 2019年03月25日21:48:39
 * niqiao1111@163.com
 * <p/>
 * 登录界面
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.user_txt)
    CleanableEditText userTxt;
    @Bind(R.id.password_txt)
    CleanableEditText passwordTxt;
    @Bind(R.id.find_password_tx)
    TextView findPasswordTx;
    @Bind(R.id.sms_login_tx)
    TextView smsLoginTx;
    @Bind(R.id.login_tx)
    TextView loginTx;
    @Bind(R.id.login_lr)
    LinearLayout loginLr;
    @Bind(R.id.rootView)
    LinearLayout rootView;
    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    String loadingTip = "加载中";

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        commonToolbar.setTitleText("登陆");
        commonToolbar.setBackVisibility(true);
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    /**
     * 登陆
     */
    private void login() {

        String name = userTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showSingleToast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showSingleToast("密码不能为空");
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
