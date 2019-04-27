package com.kaqi.reader.ui.fragment;

import android.os.Bundle;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.component.AppComponent;

public class FindItemFragment extends BaseFragment {
    public static FindItemFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        FindItemFragment fragment = new FindItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }
}
