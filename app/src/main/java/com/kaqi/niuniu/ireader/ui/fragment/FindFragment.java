package com.kaqi.niuniu.ireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.SlidingTabLayout;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.flag.RecommendBookSelfType;
import com.kaqi.niuniu.ireader.ui.base.BaseTabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by newbiechen on 17-4-15.
 */

public class FindFragment extends BaseTabFragment {

    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"精选", "男主", "女主"};

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }


    @Override
    protected void processLogic() {
        super.processLogic();
    }


    @Override
    protected int getContentId() {
        return R.layout.fragment_find;
    }

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>(RecommendBookSelfType.values().length);
        for (RecommendBookSelfType type : RecommendBookSelfType.values()) {
            fragments.add(FindItemFragment.newInstance(type));
        }
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        List<String> titles = new ArrayList<>(RecommendBookSelfType.values().length);
        for (RecommendBookSelfType type : RecommendBookSelfType.values()) {
            titles.add(type.getTypeName());
        }
        return titles;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}