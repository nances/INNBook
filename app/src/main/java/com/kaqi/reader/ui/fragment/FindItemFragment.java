package com.kaqi.reader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.adapter.FindItemAdapter;

import butterknife.Bind;

public class FindItemFragment extends BaseFragment {
    public static FindItemFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        FindItemFragment fragment = new FindItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private FindItemAdapter mAdapter;

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
        mAdapter = new FindItemAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 12);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getSpanSize(position);
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
