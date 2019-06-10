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


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerMainComponent;
import com.kaqi.reader.manager.CacheManager;
import com.kaqi.reader.manager.EventManager;
import com.kaqi.reader.manager.SettingManager;
import com.kaqi.reader.ui.presenter.MainActivityPresenter;
import com.kaqi.reader.utils.NormalTitleBar;
import com.kaqi.reader.utils.SharedPreferencesUtil;
import com.kaqi.reader.view.textview.SuperTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by niqiao
 * 2019年04月23日18:09:56
 */
public class SettingActivity extends BaseActivity {
    public static final int EXIT = 0x02;
    public static final int UPDATE_SIZE = 0x03;
    @Bind(R.id.action_synchronization_book)
    SuperTextView actionSynchronizationBook;
    @Bind(R.id.scan_local_book)
    SuperTextView scanLocalBook;
    @Bind(R.id.wifi_book)
    SuperTextView wifiBook;
    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.bookshelfSort)
    SuperTextView mTvSort;
    @Bind(R.id.rlFlipStyle)
    SuperTextView mTvFlipStyle;
    @Bind(R.id.cleanCache)
    SuperTextView mTvCacheSize;
    @Bind(R.id.provincialTraffic)
    SuperTextView noneCoverCompat;
    @Bind(R.id.nightMode)
    SuperTextView nightMode;

    @Inject
    MainActivityPresenter mPresenter;
    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    private String cacheSizeSt;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case EXIT:
                finish();
                break;
            case UPDATE_SIZE:
                mTvCacheSize.setRightString(cacheSizeSt);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        commonToolbar.setTitleText("设置");
        commonToolbar.setBackVisibility(true);
    }

    @Override
    public void initDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String cachesize = CacheManager.getInstance().getCacheSize();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvCacheSize.setRightString(cachesize);
                    }
                });

            }
        }).start();
        mTvSort.setRightString(getResources().getStringArray(R.array.setting_dialog_sort_choice)[
                SharedPreferencesUtil.getInstance().getBoolean(Constant.ISBYUPDATESORT, true) ? 0 : 1]);
        mTvFlipStyle.setRightString(getResources().getStringArray(R.array.setting_dialog_style_choice)[
                SharedPreferencesUtil.getInstance().getInt(Constant.FLIP_STYLE, 0)]);

        nightMode.setSwitchIsChecked(SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false));
        nightMode.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
                    SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    mHandler.sendEmptyMessageDelayed(EXIT,500);
                } else {
                    SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
            }
        });

    }

    @Override
    public void configViews() {

    }

    public void pullSyncBookShelf() {
        mPresenter.syncBookShelf();
    }


    @OnClick(R.id.provincialTraffic)
    public void onClickNoImage() {


        new AlertDialog.Builder(mContext)
                .setTitle("省流量模式")
                .setSingleChoiceItems(getResources().getStringArray(R.array.setting_dialog_no_image_choice),
                        SettingManager.getInstance().isNoneCover() ? 0 : 1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SettingManager.getInstance().saveNoneCover(which == 0 ? true : false);
                                noneCoverCompat.setRightString(getResources().getStringArray(R.array.setting_dialog_no_image_choice)[which]);
                                dialog.dismiss();
                            }
                        })
                .create().show();
    }


    @OnClick(R.id.bookshelfSort)
    public void onClickBookShelfSort() {
        new AlertDialog.Builder(mContext)
                .setTitle("书架排序方式")
                .setSingleChoiceItems(getResources().getStringArray(R.array.setting_dialog_sort_choice),
                        SharedPreferencesUtil.getInstance().getBoolean(Constant.ISBYUPDATESORT, true) ? 0 : 1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTvSort.setRightString(getResources().getStringArray(R.array.setting_dialog_sort_choice)[which]);
                                SharedPreferencesUtil.getInstance().putBoolean(Constant.ISBYUPDATESORT, which == 0);
                                EventManager.refreshCollectionList();
                                dialog.dismiss();
                            }
                        })
                .create().show();
    }

    @OnClick(R.id.rlFlipStyle)
    public void onClickFlipStyle() {
        new AlertDialog.Builder(mContext)
                .setTitle("阅读页翻页效果")
                .setSingleChoiceItems(getResources().getStringArray(R.array.setting_dialog_style_choice),
                        SharedPreferencesUtil.getInstance().getInt(Constant.FLIP_STYLE, 0),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTvFlipStyle.setRightString(getResources().getStringArray(R.array.setting_dialog_style_choice)[which]);
                                SharedPreferencesUtil.getInstance().putInt(Constant.FLIP_STYLE, which);
                                dialog.dismiss();
                            }
                        })
                .create().show();
    }

    @OnClick(R.id.cleanCache)
    public void onClickCleanCache() {
        //默认不勾选清空书架列表，防手抖！！
        final boolean selected[] = {true, false};
        new AlertDialog.Builder(mContext)
                .setTitle("清除缓存")
                .setCancelable(true)
                .setMultiChoiceItems(new String[]{"删除阅读记录", "清空书架列表"}, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selected[which] = isChecked;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CacheManager.getInstance().clearCache(selected[0], selected[1]);
                                final String cacheSize = CacheManager.getInstance().getCacheSize();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cacheSizeSt = cacheSize;
                                        mHandler.sendEmptyMessageDelayed(UPDATE_SIZE, 100);
                                        EventManager.refreshCollectionList();
                                    }
                                });
                            }
                        }).start();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    @OnClick({R.id.action_synchronization_book, R.id.scan_local_book, R.id.wifi_book})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_synchronization_book:
                showDialog();
//                mPresenter.syncBookShelf();
                break;
            case R.id.scan_local_book:
                ScanLocalBookActivity.startActivity(this);
                break;
            case R.id.wifi_book:
                WifiBookActivity.startActivity(this);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
