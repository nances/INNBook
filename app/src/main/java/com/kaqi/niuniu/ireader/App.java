package com.kaqi.niuniu.ireader;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.igexin.sdk.PushManager;
import com.kaqi.niuniu.ireader.service.DownloadService;
import com.kaqi.niuniu.ireader.utils.AppUtils;
import com.kaqi.niuniu.ireader.utils.push.ReaderIntentService;
import com.kaqi.niuniu.ireader.utils.push.ReaderPushService;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;

/**
 * Created by niqiao on 17-4-15.
 */

public class App extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobSDK.init(this);
        PushManager.getInstance().initialize(this, ReaderPushService.class);
        PushManager.getInstance().registerPushIntentService(this, ReaderIntentService.class);
        sInstance = this;
        AppUtils.init(this);
        // 初始化内存分析工具
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        startService(new Intent(getContext(), DownloadService.class));


    }

    public static Context getContext(){
        return sInstance;
    }

    public static void sendMessage(Message msg) {

    }
}