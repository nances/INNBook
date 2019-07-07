package com.kaqi.niuniu.ireader.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.kaqi.niuniu.ireader.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created by newbiechen on 17-4-24.
 */

public abstract class BaseTabFragment extends BaseFragment {
    /**************View***************/
    @BindView(R.id.viewPager)
    ViewPager mVp;
    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    /************Params*******************/
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    /**************abstract***********/
    protected abstract List<Fragment> createTabFragments();

    protected abstract List<String> createTabTitles();

    /*****************rewrite method***************************/
    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpTabLayout();
    }

    private void setUpTabLayout() {
        mFragmentList = createTabFragments();
        mTitleList = createTabTitles();

        checkParamsIsRight();

        TabFragmentPageAdapter adapter = new TabFragmentPageAdapter(getChildFragmentManager());
        mVp.setAdapter(adapter);
        mVp.setOffscreenPageLimit(3);
        tabs.setViewPager(mVp);
        updateTextSize(0);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        if (mFragmentList == null) {
            return;
        }
        for (int i = 0; i < mFragmentList.size(); i++) {
            final boolean isSelect = i == position;
            if (isSelect) {
                tabs.getTitleView(i).setTextSize(26);
            } else {
                tabs.getTitleView(i).setTextSize(16);
            }
        }
    }

    /**
     * 检查输入的参数是否正确。即Fragment和title是成对的。
     */
    private void checkParamsIsRight() {
        if (mFragmentList == null || mTitleList == null) {
            throw new IllegalArgumentException("fragmentList or titleList doesn't have null");
        }

        if (mFragmentList.size() != mTitleList.size())
            throw new IllegalArgumentException("fragment and title size must equal");
    }

    /******************inner class*****************/
    class TabFragmentPageAdapter extends FragmentPagerAdapter {

        public TabFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
