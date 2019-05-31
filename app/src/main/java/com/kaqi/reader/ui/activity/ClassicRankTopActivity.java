package com.kaqi.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.adapter.ComFragmentAdapter;
import com.kaqi.reader.ui.fragment.ClassRankTopItemFragment;
import com.kaqi.reader.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.Bind;

public class ClassicRankTopActivity extends BaseActivity {

    @Bind(R.id.tabs)
    SlidingTabLayout tabs;
    @Bind(R.id.viewPager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.common_toolbar)
    Toolbar common_toolbar;

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
    public int getLayoutId() {
        return R.layout.activity_fragment_classfication;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        common_toolbar.setTitle("排行榜");
        common_toolbar.setNavigationIcon(R.drawable.black_back_icon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void initDatas() {

    }

    @Override
    public void configViews() {
        initViewPager();
    }

}
