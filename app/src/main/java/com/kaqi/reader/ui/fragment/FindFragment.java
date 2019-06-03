package com.kaqi.reader.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.adapter.ComFragmentAdapter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 发现
 *
 * @author Niqiao.
 * @date 2019年05月31日10:59:50
 */
public class FindFragment extends BaseFragment implements OnTabSelectListener {

    @Bind(R.id.tabs)
    SlidingTabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"推荐", "男主", "女主"};

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_find;
    }

    public static FindFragment newInstance() {
        return new FindFragment();
    }


    @Override
    public void initDatas() {
    }

    @Override
    public void configViews() {
        tabs.setOnTabSelectListener(this);
        initViewPager();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    private void initViewPager() {

        fragments.add(FindItemFragment.newInstance(1));
        fragments.add(FindItemFragment.newInstance(2));
        fragments.add(FindItemFragment.newInstance(3));
        mViewPager.setAdapter(new ComFragmentAdapter(getChildFragmentManager(), fragments));

        tabs.setViewPager(mViewPager, titles);
        mViewPager.setOffscreenPageLimit(titles.length);
        tabs.setCurrentTab(0);
        tabs.getTitleView(0).setTextSize(26);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTextSize(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void updateTextSize(int position) {
        for (int i = 0; i < fragments.size(); i++) {
            final boolean isSelect = i == position;
            if (isSelect) {
                tabs.getTitleView(i).setTextSize(26);
            } else {
                tabs.getTitleView(i).setTextSize(16);
            }
        }
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }
}
