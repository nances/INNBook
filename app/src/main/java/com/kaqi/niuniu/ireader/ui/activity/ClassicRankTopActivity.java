package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.ui.fragment.ClassRankTopItemFragment;
import com.kaqi.niuniu.ireader.view.NoScrollViewPager;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.ComFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ClassicRankTopActivity extends BaseActivity {

    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    @BindView(R.id.viewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.normaltitle)
    NormalTitleBar common_toolbar;

    private String[] titles = new String[]{"男生", "女生"};

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private long exitTime;
    //记录当前TAB的标题
    private String curTabTile;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ClassicRankTopActivity.class));
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_fragment_classfication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(ClassRankTopItemFragment.newInstance(titles[i].toString()));
        }
        mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), fragments));

        tabs.setViewPager(mViewPager, titles);
        mViewPager.setOffscreenPageLimit(titles.length);
        tabs.setCurrentTab(0);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        common_toolbar.setTitleText("排行榜");
        common_toolbar.setTitleColor(getResources().getColor(R.color.common_h1));
        common_toolbar.setBackVisibility(true);
        initViewPager();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

}
