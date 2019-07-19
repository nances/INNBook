package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.utils.Utils;
import com.kaqi.niuniu.ireader.view.HourglassView;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.CleanableEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * des:register
 * 2019年03月07日19:28:07
 *
 * @author niqiao
 */
public class RegisterActivity extends BaseActivity implements Handler.Callback {
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
    EventHandler eventHandler;

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
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        sendCode();
        get_code.OnAttch(phone_num, area_txt);
        get_code.setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area = area_txt.getText().toString();
                phone = phone_num.getText().toString();
                SMSSDK.getVerificationCode(area, phone);
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
//          验证验证码是否成功


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
        SMSSDK.submitVerificationCode(area, phone, code_txt.getText().toString());
    }

    /**
     * 校验验证码
     */
    public void sendCode() {
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                ToastUtils.show("发送成功...");
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                                ToastUtils.show("发送失败，请重启验证");
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                ToastUtils.show("验证码验证成功 EVENT_SUBMIT_VERIFICATION_CODE");
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }


    @Override
    protected void onDestroy() {
        get_code = null;
        SMSSDK.unregisterEventHandler(eventHandler);
        super.onDestroy();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_register;
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }
}
