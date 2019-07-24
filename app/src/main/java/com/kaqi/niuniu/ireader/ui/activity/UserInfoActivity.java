package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.textview.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by niqiao on 2019年03月25日21:48:39
 * niqiao1111@163.com
 * <p/>
 * 登录界面
 */
public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    @BindView(R.id.user_txt)
    TextView userTxt;
    @BindView(R.id.nick_txt)
    TextView nick_txt;
    @BindView(R.id.gender_btn)
    TextView genderBtn;
    @BindView(R.id.location_btn)
    TextView locationBtn;
    @BindView(R.id.avatar_image)
    CircleImageView avatar_image;

    @Override
    protected int getContentId() {
        return R.layout.userinfo_activity;
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonToolbar.setTitleText("个人信息");
        commonToolbar.setBackVisibility(true);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        commonToolbar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.startAction(UserInfoActivity.this);
            }
        });
    }


    public void setUserInfo() {
        nick_txt.setText(SharedPreUtils.getInstance().getString(Constant.UID));
        userTxt.setText(SharedPreUtils.getInstance().getString(Constant.NICK_NAME));
        genderBtn.setText(SharedPreUtils.getInstance().getString(Constant.SHARED_SEX).equals(Constant.SEX_BOY) ? "男主" : "女主");
        Glide.with(this)
                .load(SharedPreUtils.getInstance().getString(Constant.USER_ICON))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(avatar_image);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setUserInfo();
    }

    @OnClick({R.id.gender_btn, R.id.exit_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gender_btn:
                break;
            case R.id.exit_out:
                ExitCleanUserinfo();
                finish();
                break;
        }
    }

    /**
     * 退出，清理账户信息
     */
    public void ExitCleanUserinfo() {
        SharedPreUtils.getInstance().putString(Constant.UID, "");
        SharedPreUtils.getInstance().putString(Constant.NICK_NAME, "");
        SharedPreUtils.getInstance().putString(Constant.SHARED_SEX, "");
        SharedPreUtils.getInstance().putString(Constant.USER_ICON, "");
    }
}
