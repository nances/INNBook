package com.kaqi.niuniu.ireader.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.RxBus;
import com.kaqi.niuniu.ireader.event.TaskShareEvent;
import com.kaqi.niuniu.ireader.model.bean.TabEntity;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.ui.fragment.FindFragment;
import com.kaqi.niuniu.ireader.ui.fragment.MineFragment;
import com.kaqi.niuniu.ireader.ui.fragment.RecommendFragment;
import com.kaqi.niuniu.ireader.ui.fragment.TaskFragment;
import com.kaqi.niuniu.ireader.utils.AppVersionManager;
import com.kaqi.niuniu.ireader.utils.PermissionsChecker;
import com.kaqi.niuniu.ireader.utils.ShareUtils;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.widget.dialog.CommomAddShuJiaDialog;
import com.kaqi.niuniu.ireader.widget.dialog.ShareDialog;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {
    /*************Constant**********/
    private static final int WAIT_INTERVAL = 2000;
    private static final int PERMISSIONS_REQUEST_STORAGE = 1;

    static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /***************Object*********************/
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private PermissionsChecker mPermissionsChecker;
    /*****************Params*********************/
    private boolean isPrepareFinish = false;

    @BindView(R.id.sliding_tabs)
    CommonTabLayout slidingTabLayout;

    private String[] mTitles = {"首页", "书架", "福利", "个人"};
    private int[] mIconUnselectIds = {
            R.drawable.home_icon_nor, R.drawable.bookshelf_normal_icon, R.drawable.forum_icon_nor,
            R.drawable.mine_icon_nor};
    private int[] mIconSelectIds = {
            R.drawable.home_icon_sel, R.drawable.bookshelf_sel_icon, R.drawable.forum_icon_sel,
            R.drawable.mine_icon_sel};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MineFragment mineFragment;
    private TaskFragment taskFragment;
    private RecommendFragment homeFragment;
    private FindFragment findFragment;

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private long exitTime;
    //记录当前TAB的标题
    private String curTabTile;

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case 1:
                finish();
                break;
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initTab();
        initFragment(savedInstanceState);
        shareEvent();
        slidingTabLayout.measure(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        slidingTabLayout.setTabData(mTabEntities);
        //点击监听
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);

            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            homeFragment = (RecommendFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            findFragment = (FindFragment) getSupportFragmentManager().findFragmentByTag("findFragment");
            taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentByTag("taskFragment");
            mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mineFragment");
            currentTabPosition = savedInstanceState.getInt("HOME_CURRENT_TAB_POSITION");
        } else {
            homeFragment = new RecommendFragment();
            findFragment = new FindFragment();
            taskFragment = new TaskFragment();
            mineFragment = new MineFragment();

            transaction.add(R.id.container, homeFragment, "homeFragment");
            transaction.add(R.id.container, findFragment, "findFragment");
            transaction.add(R.id.container, taskFragment, "taskFragment");
            transaction.add(R.id.container, mineFragment, "mineFragment");
        }
        transaction.commit();
        setCurrentItem(currentTabPosition);
    }

    /**
     * 切换底部位置
     *
     * @param position
     */
    public void setCurrentItem(int position) {
        SwitchTo(position);
        slidingTabLayout.setCurrentTab(position);

    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.show(homeFragment);
                transaction.hide(taskFragment);
                transaction.hide(mineFragment);
                transaction.hide(findFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.hide(homeFragment);
                transaction.show(findFragment);
                transaction.hide(taskFragment);
                transaction.hide(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(homeFragment);
                transaction.hide(findFragment);
                transaction.show(taskFragment);
                transaction.hide(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction.hide(homeFragment);
                transaction.hide(taskFragment);
                transaction.hide(findFragment);
                transaction.show(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
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
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exitDialog();
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 退出提示
     */
    public void exitDialog() {
        new CommomAddShuJiaDialog(this, R.style.dialog, getString(R.string.exit_app_tip), new CommomAddShuJiaDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    mHandler.sendEmptyMessageDelayed(1, 100);
                }
            }
        }).setTitle("提示").setNegativeButton("取消").setPositiveButton("确定").show();
    }

    /**
     * 更新提示
     */
    private void checkAppVersion() {
        boolean isNewVersion = false;
        if (isNewVersion) {
            AppVersionManager.upgradeApp(this, "牛牛阅读", "更新内容", "12M", "https://sj.qq.com/myapp/detail.htm?apkName=com.xunmeng.pinduoduo");
        }
    }


    /**
     * 分享 书本加入书架
     */
    public void shareEvent() {
        Disposable donwloadDisp = RxBus.getInstance()
                .toObservable(TaskShareEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            if (event.getType() == 1) {
                                getShare();
                            } else if (event.getType() == 2) {
                                setCurrentItem(1);
                            }
                        }
                );
        addDisposable(donwloadDisp);
    }


    /**
     * 分享
     */
    public void getShare() {

        ShareUtils.ShareContentBean shareContentBean = new ShareUtils.ShareContentBean(
                "牛牛阅读",
                "快来下载吧～～～～～～～～",
                "",
                ""
        );

        ShareDialog.showDialog(this, shareContentBean, new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
    }
}
