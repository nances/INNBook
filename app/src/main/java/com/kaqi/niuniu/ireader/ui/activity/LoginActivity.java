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
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.CleanableEditText;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

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
        commonToolbar.setRightTitle("注册");
        commonToolbar.setRightTitleVisibility(true);
        commonToolbar.setBackVisibility(true);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        commonToolbar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.startAction(LoginActivity.this);
            }
        });
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

    @OnClick({R.id.sms_login_tx, R.id.login_tx, R.id.find_password_tx, R.id.wechat_login})
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
            case R.id.wechat_login:
                weChatLogin();
                break;
        }
    }

    /**
     * 第三方登陆
     */
    public void weChatLogin() {


        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        ShareSDK.setActivity(this);//抖音登录适配安卓9.0
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        wechat.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
                wechat.removeAccount(true);
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                if (arg0 != null) {
                    setLoginUserinfo(arg0);
                }
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                wechat.removeAccount(true);

            }
        });
        //authorize
        wechat.authorize();//要功能不要数据，在监听oncomplete中不会返回用户数据
    }

    /**
     * 微信登陆
     */
    public void setLoginUserinfo(Platform platform) {
        SharedPreUtils.getInstance().putString(Constant.UID, platform.getDb().getUserId());
        SharedPreUtils.getInstance().putString(Constant.NICK_NAME, platform.getDb().getUserName());
        SharedPreUtils.getInstance().putString(Constant.SHARED_SEX, platform.getDb().getUserGender().equals(0) ? Constant.SEX_BOY : Constant.SEX_GIRL);
        SharedPreUtils.getInstance().putString(Constant.USER_ICON, platform.getDb().getUserIcon());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
