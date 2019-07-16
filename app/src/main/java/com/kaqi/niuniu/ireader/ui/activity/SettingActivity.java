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
package com.kaqi.niuniu.ireader.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.utils.BookManager;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.FileUtils;
import com.kaqi.niuniu.ireader.utils.LogUtils;
import com.kaqi.niuniu.ireader.utils.PermissionsChecker;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.textview.SuperTextView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.kaqi.niuniu.ireader.ui.activity.MainActivity.PERMISSIONS;

/**
 * Created by niqiao
 * 2019年04月23日18:09:56
 */
public class SettingActivity extends BaseActivity {


    private static final int PERMISSIONS_REQUEST_STORAGE = 1;
    public static final int EXIT = 0x02;
    public static final int UPDATE_SIZE = 0x03;


    @BindView(R.id.action_synchronization_book)
    SuperTextView actionSynchronizationBook;
    @BindView(R.id.scan_local_book)
    SuperTextView scanLocalBook;
    @BindView(R.id.wifi_book)
    SuperTextView wifiBook;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.cleanCache)
    SuperTextView mTvCacheSize;
    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    private String cacheSizeSt;
    PermissionsChecker mPermissionsChecker;
    Class<?> activityCls = null;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case EXIT:
                finish();
                break;
            case UPDATE_SIZE:
                mTvCacheSize.setRightString(BookManager.getInstance().getCacheSize());
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonToolbar.setTitleText("设置");
        commonToolbar.setBackVisibility(true);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mHandler.sendEmptyMessageDelayed(UPDATE_SIZE, 200);
    }


    @OnClick({R.id.action_synchronization_book, R.id.scan_local_book, R.id.wifi_book, R.id.cleanCache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_synchronization_book:
//                mPresenter.syncBookShelf();
                break;
            case R.id.scan_local_book:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    if (mPermissionsChecker == null) {
                        mPermissionsChecker = new PermissionsChecker(this);
                    }

                    //获取读取和写入SD卡的权限
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                        //请求权限
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_STORAGE);
                    }
                }

                activityCls = FileSystemActivity.class;
                if (activityCls != null) {
                    Intent intent = new Intent(this, activityCls);
                    startActivity(intent);
                }
                break;
            case R.id.wifi_book:
                WifiBookActivity.startActivity(this);
                break;
            case R.id.cleanCache:
                clearCache();
                break;
        }
    }


    /**
     * 清除缓存
     *
     * @param clearReadPos 是否删除阅读记录
     */
    public synchronized void clearCache() {
        try {
            // 删除内存缓存
            String cacheDir = Constant.BOOK_CACHE_PATH;
            FileUtils.deleteFileOrDirectory(new File(cacheDir));
            if (FileUtils.isSdCardAvailable()) {
                // 删除SD书籍缓存
                FileUtils.deleteFileOrDirectory(new File(Constant.PATH_DATA));
            }
            // 删除阅读记录（SharePreference）
            //防止再次弹出性别选择框，sp要重写入保存的性别

            String chooseSex = SharedPreUtils.getInstance().getString(Constant.SHARED_SEX);
            SharedPreUtils.getInstance()
                    .putString(Constant.SHARED_SEX, chooseSex);
            mHandler.sendEmptyMessageDelayed(UPDATE_SIZE,1000);
        } catch (Exception e) {

            LogUtils.e(e.toString());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_STORAGE: {
                // 如果取消权限，则返回的值为0
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //跳转到 FileSystemActivity
                    Intent intent = new Intent(this, FileSystemActivity.class);
                    startActivity(intent);

                } else {
                    ToastUtils.show("用户拒绝开启读写权限");
                }
                return;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}
