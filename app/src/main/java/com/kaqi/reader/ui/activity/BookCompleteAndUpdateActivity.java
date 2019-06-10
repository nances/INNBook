package com.kaqi.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.SlidingTabLayout;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.adapter.ComFragmentAdapter;
import com.kaqi.reader.ui.fragment.BookCompleteUpdateFragment;
import com.kaqi.reader.utils.NormalTitleBar;
import com.kaqi.reader.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.Bind;

public class BookCompleteAndUpdateActivity extends BaseActivity {

    @Bind(R.id.tabs)
    SlidingTabLayout tabs;
    @Bind(R.id.viewPager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.normaltitle)
    NormalTitleBar common_toolbar;

    private String[] titles = new String[]{"男生", "女生"};
    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private long exitTime;
    private String curTabTile;
    public static final String CHANNEL_TYPE = "channel_type";
    public int channel_type;

    public static void startActivity(Context context, int channle_top) {
        context.startActivity(new Intent(context, BookCompleteAndUpdateActivity.class)
                .putExtra(CHANNEL_TYPE, channle_top));
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

//        common_toolbar.setNavigationIcon(R.drawable.black_back_icon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(BookCompleteUpdateFragment.newInstance(titles[i].toString(), channel_type));
        }
        mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), fragments));

        tabs.setViewPager(mViewPager, titles);
        mViewPager.setOffscreenPageLimit(titles.length);
        tabs.setCurrentTab(0);
    }

    @Override
    public void initDatas() {
        channel_type = getIntent().getIntExtra(CHANNEL_TYPE, 0);
        if (channel_type == 1) {
            common_toolbar.setTitleText("完本");
        } else if (channel_type == 2) {
            common_toolbar.setTitleText("热搜");
        }
        common_toolbar.setBackVisibility(true);
    }

    @Override
    public void configViews() {
        initViewPager();
    }

}
