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
