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
package com.kaqi.reader.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.adapter.ComFragmentAdapter;
import com.kaqi.reader.utils.MagicIndicatorUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;

/**
 * 发现
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class FindFragment extends BaseFragment {

    @Bind(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

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
        initViewPager();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FindItemFragment.newInstance(1));
        fragments.add(FindItemFragment.newInstance(2));
        fragments.add(FindItemFragment.newInstance(3));
        mViewPager.setAdapter(new ComFragmentAdapter(getChildFragmentManager(), fragments));
        MagicIndicatorUtil.init(getContext(), magicIndicator, mViewPager, Arrays.asList(titles));
    }
}
