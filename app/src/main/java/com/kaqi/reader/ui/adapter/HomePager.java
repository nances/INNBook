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
