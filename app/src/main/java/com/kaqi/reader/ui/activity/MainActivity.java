package com.kaqi.reader.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.bean.TabEntity;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.fragment.CommunityFragment;
import com.kaqi.reader.ui.fragment.FindFragment;
import com.kaqi.reader.ui.fragment.MineFragment;
import com.kaqi.reader.ui.fragment.RecommendFragment;
import com.kaqi.reader.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.sliding_tabs)
    CommonTabLayout slidingTabLayout;

    private String[] mTitles = {"首页", "书架", "论坛", "个人"};
    private int[] mIconUnselectIds = {
            R.drawable.home_icon_nor, R.drawable.bookshelf_normal_icon, R.drawable.forum_icon_nor,
            R.drawable.mine_icon_nor};
    private int[] mIconSelectIds = {
            R.drawable.home_icon_sel, R.drawable.bookshelf_sel_icon, R.drawable.forum_icon_sel,
            R.drawable.mine_icon_sel};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private MineFragment mineFragment;
    private CommunityFragment circleMainFragment;
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
        //初始化Fragment
        initFragment(savedInstanceState);
        slidingTabLayout.measure(0, 0);
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
                switch (position) {
//                    case 0:
//                        mRxManager.post(BundleKey.HOME_REFRESH_INFO, "");
//                        break;
//                    case 1:
//                        mRxManager.post(BundleKey.COOMEND_REFRESH_INFO, "");
//                        break;
//                    case 2:
//                        mRxManager.post(BundleKey.MATCH_REFRESH_INFO, "");
//                        break;
//                    case 3:
//                        break;
                }
            }
        });
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
            circleMainFragment = (CommunityFragment) getSupportFragmentManager().findFragmentByTag("circleMainFragment");
            currentTabPosition = savedInstanceState.getInt("HOME_CURRENT_TAB_POSITION");
        } else {
            mineFragment = new MineFragment();
            homeFragment = new RecommendFragment();
            circleMainFragment = new CommunityFragment();
            findFragment = new FindFragment();

            transaction.add(R.id.container, mineFragment, "mineFragment");
            transaction.add(R.id.container, homeFragment, "homeFragment");
            transaction.add(R.id.container, findFragment, "findFragment");
            transaction.add(R.id.container, circleMainFragment, "circleMainFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        slidingTabLayout.setCurrentTab(currentTabPosition);
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
                transaction.hide(circleMainFragment);
                transaction.hide(mineFragment);
                transaction.hide(findFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.hide(homeFragment);
                transaction.hide(mineFragment);
                transaction.show(findFragment);
                transaction.hide(circleMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.hide(homeFragment);
                transaction.hide(mineFragment);
                transaction.hide(findFragment);
                transaction.show(circleMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction.hide(homeFragment);
                transaction.hide(circleMainFragment);
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

    }

    @Override
    public void configViews() {

    }


    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtils.showToast(getString(R.string.exit_tips));
                return true;
            } else {
                finish(); // 退出
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}
