package com.kaqi.reader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.bean.BooksByCats;
import com.kaqi.reader.bean.CategoryList;
import com.kaqi.reader.bean.RankingList;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerCommunityComponent;
import com.kaqi.reader.ui.adapter.TopCategoryPressListAdapter;
import com.kaqi.reader.ui.adapter.TopRankListAdapter;
import com.kaqi.reader.ui.contract.TopCategoryListContract;
import com.kaqi.reader.ui.presenter.TopCategoryListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class BookCompleteUpdateFragment extends BaseFragment implements TopCategoryListContract.View {

    private String Type_Channle;
    private int type_complete_update;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Inject
    TopCategoryListPresenter mPresenter;
    private TopRankListAdapter topRankListAdapter;
    private TopCategoryPressListAdapter mPressCategoryListAdapter;


    private List<RankingList.MaleBean> mMaleCategoryList = new ArrayList<>();
    private List<RankingList.FemaleBean> mFemaleCategoryList = new ArrayList<>();
    private List<BooksByCats.BooksBean> booksBeans = new ArrayList<>();

    public static BookCompleteUpdateFragment newInstance(String type, int type_complete_update_type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putInt("type_complete_update", type_complete_update_type);
        BookCompleteUpdateFragment fragment = new BookCompleteUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_book_complete_update_frament;
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

    /**
     * 初始化tab
     */
    private void initTab() {
    }

    @Override
    public void initDatas() {
        if (getArguments() != null) {
            Type_Channle = getArguments().getString("type");
            type_complete_update = getArguments().getInt("type_complete_update");
        }
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        topRankListAdapter = new TopRankListAdapter(mContext, booksBeans);
        recyclerview.setAdapter(topRankListAdapter);
        mPresenter.attachView(this);
        mPresenter.getRankList();
    }

    @Override
    public void configViews() {
    }

    @Override
    public void showCategoryList(CategoryList data) {
    }

    @Override
    public void showRankList(RankingList rankingList) {

        if (Type_Channle.equals("男生")) {
            mMaleCategoryList.clear();
            mMaleCategoryList.addAll(rankingList.getMale());
            if(type_complete_update == 1){
                mPresenter.getRankList("564eb878efe5b8e745508fde");
            }else if(type_complete_update == 2){
                mPresenter.getRankList("5a6844f8fc84c2b8efaa8bc5");
            }
        } else {
            mFemaleCategoryList.clear();
            mFemaleCategoryList.addAll(rankingList.getFemale());
            if(type_complete_update == 1){
                mPresenter.getRankList("564eb8a9cf77e9b25056162d");
            }else if(type_complete_update == 2){
                mPresenter.getRankList("5a684515fc84c2b8efaa9875");
            }

        }

    }

    @Override
    public void showRankList(BooksByCats data) {
        booksBeans.clear();
        booksBeans.addAll(data.books);
        topRankListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

}
