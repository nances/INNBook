package com.kaqi.reader.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kaqi.reader.ui.fragment.RecommendFragment;

/**
 * @author yuyh.
 * @date 16/8/6.
 */
public class HomePager extends FragmentPagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;

    public HomePager(FragmentManager fm, Context context, String[] channle) {
        super(fm);
        TITLES = channle;
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {

                case 0:
                    fragments[position] = RecommendFragment.newInstance(); // 推荐
                    break;
                case 1:
//                    fragments[position] = FindFragment.newInstance();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }


    @Override
    public int getCount() {
        return TITLES.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
