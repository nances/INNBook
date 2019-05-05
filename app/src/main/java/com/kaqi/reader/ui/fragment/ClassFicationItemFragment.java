package com.kaqi.reader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.bean.BooksByCats;
import com.kaqi.reader.bean.CategoryList;
import com.kaqi.reader.bean.RankingList;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerCommunityComponent;
import com.kaqi.reader.ui.adapter.TopCategoryFemaleListAdapter;
import com.kaqi.reader.ui.adapter.TopCategoryMaleListAdapter;
import com.kaqi.reader.ui.adapter.TopCategoryPressListAdapter;
import com.kaqi.reader.ui.contract.TopCategoryListContract;
import com.kaqi.reader.ui.presenter.TopCategoryListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class ClassFicationItemFragment extends BaseFragment implements TopCategoryListContract.View {

    private String Type_Channle;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Inject
    TopCategoryListPresenter mPresenter;

    private TopCategoryMaleListAdapter mMaleCategoryListAdapter;
    private TopCategoryFemaleListAdapter mFemaleCategoryListAdapter;
    private TopCategoryPressListAdapter mPressCategoryListAdapter;
    private List<CategoryList.MaleBean> mMaleCategoryList = new ArrayList<>();
    private List<CategoryList.FemaleBean> mFemaleCategoryList = new ArrayList<>();
    private List<CategoryList.PressBean> mPressCategoryList = new ArrayList<>();

    public static ClassFicationItemFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        ClassFicationItemFragment fragment = new ClassFicationItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        if (getArguments() != null) {
            Type_Channle = getArguments().getString("type");
        }
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (!"".equals(Type_Channle)) {
            if (Type_Channle.equals("男生")) {
                mMaleCategoryListAdapter = new TopCategoryMaleListAdapter(mContext, mMaleCategoryList);
                recyclerview.setAdapter(mMaleCategoryListAdapter);

            } else if (Type_Channle.equals("女生")) {
                mFemaleCategoryListAdapter = new TopCategoryFemaleListAdapter(mContext, mFemaleCategoryList);
                recyclerview.setAdapter(mFemaleCategoryListAdapter);
            } else if (Type_Channle.equals("文学")) {
                mPressCategoryListAdapter = new TopCategoryPressListAdapter(mContext, mPressCategoryList);
                recyclerview.setAdapter(mPressCategoryListAdapter);
            }
        }
        mPresenter.attachView(this);
        mPresenter.getCategoryList();
    }

    @Override
    public void configViews() {

    }

    @Override
    public void showCategoryList(CategoryList data) {
        mMaleCategoryList.clear();
        mFemaleCategoryList.clear();
        mMaleCategoryList.addAll(data.getMale());
        mFemaleCategoryList.addAll(data.getFemale());
        mPressCategoryList.addAll(data.getPress());
        mMaleCategoryListAdapter.notifyDataSetChanged();
        mFemaleCategoryListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRankList(RankingList rankingList) {

    }

    @Override
    public void showRankList(BooksByCats data) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

}
