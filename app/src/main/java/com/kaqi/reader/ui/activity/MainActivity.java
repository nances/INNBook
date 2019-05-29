package com.kaqi.reader.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.bean.TabEntity;
import com.kaqi.reader.bean.support.ShareEvent;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.service.DownloadBookService;
import com.kaqi.reader.ui.fragment.CommunityFragment;
import com.kaqi.reader.ui.fragment.FindFragment;
import com.kaqi.reader.ui.fragment.MineFragment;
import com.kaqi.reader.ui.fragment.RecommendFragment;
import com.kaqi.reader.ui.fragment.TaskFragment;
import com.kaqi.reader.utils.AppVersionManager;
import com.kaqi.reader.utils.ShareUtils;
import com.kaqi.reader.view.dialog.CommomAddShuJiaDialog;
import com.kaqi.reader.view.dialog.ShareDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class MainActivity extends BaseActivity {
    @Bind(R.id.sliding_tabs)
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
    private CommunityFragment circleMainFragment;
    private TaskFragment taskFragment;
    //    private HomeFragment homeFragment;
    private RecommendFragment homeFragment;
    private FindFragment findFragment;


    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private long exitTime;
    //记录当前TAB的标题
    private String curTabTile;

    @Override
    public int getLayoutId() {
        return R.layout.activity_maint;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTab();
        EventBus.getDefault().register(this);
        //初始化Fragment
        initFragment(savedInstanceState);
        slidingTabLayout.measure(0, 0);
        checkAppVersion();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("Nancy", "in onRestoreInstanceState >> this:" + this +
                " savedInstanceState:" + savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);

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

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case 1:
                finish();
                break;
        }
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mineFragment");
            homeFragment = (RecommendFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            findFragment = (FindFragment) getSupportFragmentManager().findFragmentByTag("findFragment");
//            circleMainFragment = (CommunityFragment) getSupportFragmentManager().findFragmentByTag("circleMainFragment");
            taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentByTag("taskFragment");
            currentTabPosition = savedInstanceState.getInt("HOME_CURRENT_TAB_POSITION");
            Log.v("Nancy", "HOME_CURRENT_TAB_POSITION " + currentTabPosition);
        } else {
            mineFragment = new MineFragment();
            homeFragment = new RecommendFragment();
            taskFragment = new TaskFragment();
            findFragment = new FindFragment();

            transaction.add(R.id.container, mineFragment, "mineFragment");
            transaction.add(R.id.container, homeFragment, "homeFragment");
            transaction.add(R.id.container, findFragment, "findFragment");
            transaction.add(R.id.container, taskFragment, "taskFragment");
        }
        Log.v("Nancy", "currentTabPosition" + currentTabPosition);
        transaction.commit();
        setCurrentItem(currentTabPosition);
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
                transaction.hide(mineFragment);
                transaction.show(findFragment);
                transaction.hide(taskFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(homeFragment);
                transaction.hide(mineFragment);
                transaction.hide(findFragment);
                transaction.show(taskFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction.hide(homeFragment);
                transaction.hide(taskFragment);
                transaction.show(mineFragment);
                transaction.hide(findFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
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
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    @Override
    public void initDatas() {
        startService(new Intent(this, DownloadBookService.class));
    }

    @Override
    public void configViews() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        DownloadBookService.cancel();
        stopService(new Intent(this, DownloadBookService.class));
        mHandler.removeCallbacksAndMessages(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareUtils(final ShareEvent msg) {
        if (msg.share_type == 1) {
            getShare();
        }
    }

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

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
        new CommomAddShuJiaDialog(mContext, R.style.dialog, getString(R.string.exit_app_tip), new CommomAddShuJiaDialog.OnCloseListener() {
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
