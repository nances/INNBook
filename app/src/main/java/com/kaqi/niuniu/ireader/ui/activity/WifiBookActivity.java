package com.kaqi.niuniu.ireader.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.utils.NetworkUtils;
import com.kaqi.niuniu.ireader.utils.wifitransfer.Defaults;
import com.kaqi.niuniu.ireader.utils.wifitransfer.ServerRunner;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.dialog.CommomAddShuJiaDialog;

import butterknife.BindView;

/**
 * Created by Niqiao on 2019年06月29日11:55:49
 */
public class WifiBookActivity extends BaseActivity {
    public static final int EXIT_WIFI_LOCAL = 0x02;
    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, WifiBookActivity.class));
    }

    @BindView(R.id.mTvWifiName)
    TextView mTvWifiName;
    @BindView(R.id.mTvWifiIp)
    TextView mTvWifiIp;

    @BindView(R.id.tvRetry)
    TextView tvRetry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        commonToolbar.setTitleText("WiFi传书");
        commonToolbar.setBackVisibility(true);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_wifi_book;
    }

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case EXIT_WIFI_LOCAL:
                exitDialog();
                break;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        startSendBook();
    }

    /**
     * 开始传输数据
     */
    public void startSendBook(){
        String wifiname = NetworkUtils.getConnectWifiSsid(this);
        if (!TextUtils.isEmpty(wifiname)) {
            mTvWifiName.setText(wifiname.replace("\"", ""));
        } else {
            mTvWifiName.setText("Unknow");
        }

        String wifiIp = NetworkUtils.getConnectWifiIp(this);
        if (!TextUtils.isEmpty(wifiIp)) {
            tvRetry.setVisibility(View.GONE);
            mTvWifiIp.setText("http://" + NetworkUtils.getConnectWifiIp(this) + ":" + Defaults.getPort());
            // 启动wifi传书服务器
            ServerRunner.startServer();
        } else {
            mTvWifiIp.setText("请开启Wifi并重试");
            tvRetry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initClick() {
        super.initClick();

        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSendBook();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (ServerRunner.serverIsRunning) {
            mHandler.sendEmptyMessageDelayed(EXIT_WIFI_LOCAL, 50);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServerRunner.stopServer();
    }

    /**
     * 退出WIFI传输
     */
    public void exitDialog() {
        new CommomAddShuJiaDialog(this, R.style.dialog, getString(R.string.exit_wifi_local), new CommomAddShuJiaDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    finish();
                }
            }
        }).setTitle("提示").setNegativeButton("取消").setPositiveButton("确定").show();
    }

}
