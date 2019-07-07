package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.utils.Utils;
import com.kaqi.niuniu.ireader.view.HourglassView;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.CleanableEditText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * des:register
 * 2019年03月07日19:28:07
 *
 * @author niqiao
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.pass_txt_1)
    CleanableEditText pass_txt_1;
    @BindView(R.id.pass_txt_2)
    CleanableEditText pass_txt_2;
    @BindView(R.id.code_txt)
    CleanableEditText code_txt;
    @BindView(R.id.phone_num)
    CleanableEditText phone_num;
    @BindView(R.id.name_txt)
    CleanableEditText name_txt;
    @BindView(R.id.area_txt)
    TextView area_txt;
    @BindView(R.id.register_txt)
    TextView register_txt;

    @BindView(R.id.area_ll)
    LinearLayout area_ll;
    String area, phone, code, userName, pass_1, pass_2;
    @BindView(R.id.get_code)
    HourglassView get_code;

    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    /**
     * 入口
     *
     * @param mContext
     */
    public static void startAction(Context mContext) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonToolbar.setTitleText("注册");
        commonToolbar.setBackVisibility(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        get_code.OnAttch(phone_num, area_txt);
        get_code.setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area = area_txt.getText().toString();
                phone = phone_num.getText().toString();
            }
        });
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Utils.hideKeyboard(RegisterActivity.this);
                return false;
            }
        });
    }

    @OnClick({R.id.register_txt, R.id.area_ll})
    public void onClick(View view) {
        if (view.getId() == R.id.register_txt) {
            area = area_txt.getText().toString();
            code = code_txt.getText().toString();
            phone = phone_num.getText().toString();
            userName = name_txt.getText().toString();
            pass_1 = pass_txt_1.getText().toString();
            pass_2 = pass_txt_2.getText().toString();
            requestRegister();
        } else if (view.getId() == R.id.area_ll) {

        }
    }


    private void requestRegister() {
        if (!Utils.isPhone(this, phone, area)) {
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Utils.showToast(this, "验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Utils.showToast(this, "昵称不能为空");
            return;
        }
        if (TextUtils.isEmpty(pass_1) || TextUtils.isEmpty(pass_2)) {
            Utils.showToast(this, "密码不能为空");
            return;
        }
        if (!pass_1.equals(pass_2)) {
            Utils.showToast(this, "两次输入的密码必须一致");
            return;
        }
        if (!(pass_1.length() > 7 && pass_1.length() < 15
                && pass_2.length() > 7 && pass_2.length() < 15) || Utils.isJustNum(pass_1)) {
            Utils.showToast(this, "密码必须在8-14位之间,且不能为纯数字");
            return;
        }
    }


    @Override
    protected void onDestroy() {
        get_code = null;
        super.onDestroy();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_register;
    }

}
