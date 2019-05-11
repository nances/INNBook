package com.kaqi.reader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.bean.BooksByCats;
import com.kaqi.reader.bean.CategoryList;
import com.kaqi.reader.bean.RankingList;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerCommunityComponent;
import com.kaqi.reader.ui.adapter.TopCategoryPressListAdapter;
import com.kaqi.reader.ui.adapter.TopRankChannleFemaleListAdapter;
import com.kaqi.reader.ui.adapter.TopRankChannleMaleListAdapter;
import com.kaqi.reader.ui.adapter.TopRankListAdapter;
import com.kaqi.reader.ui.contract.TopCategoryListContract;
import com.kaqi.reader.ui.presenter.TopCategoryListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class ClassRankTopItemFragment extends BaseFragment implements TopCategoryListContract.View {

    private String Type_Channle;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.rank_recyclerview)
    RecyclerView rank_recyclerview;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Inject
    TopCategoryListPresenter mPresenter;
    private TopRankListAdapter topRankListAdapter;
    private TopRankChannleMaleListAdapter topRankChannleListAdapter;
    private TopRankChannleFemaleListAdapter topRankChannleFemaleListAdapter;
    private TopCategoryPressListAdapter mPressCategoryListAdapter;


    private List<RankingList.MaleBean> mMaleCategoryList = new ArrayList<>();
    private List<RankingList.FemaleBean> mFemaleCategoryList = new ArrayList<>();
    private List<BooksByCats.BooksBean> booksBeans = new ArrayList<>();

    public static ClassRankTopItemFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        ClassRankTopItemFragment fragment = new ClassRankTopItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_rank_top_frament;
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
        }
        rank_recyclerview.setHasFixedSize(true);
        rank_recyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        topRankListAdapter = new TopRankListAdapter(mContext, booksBeans);
        recyclerview.setAdapter(topRankListAdapter);

        if (!"".equals(Type_Channle)) {
            if (Type_Channle.equals("男生")) {
                topRankChannleListAdapter = new TopRankChannleMaleListAdapter(mContext, mMaleCategoryList);
                rank_recyclerview.setAdapter(topRankChannleListAdapter);
                topRankChannleListAdapter.setItemChecked(0);
                topRankChannleListAdapter.setItemClickListener(new ClickListener());
            } else if (Type_Channle.equals("女生")) {
                topRankChannleFemaleListAdapter = new TopRankChannleFemaleListAdapter(mContext, mFemaleCategoryList);
                rank_recyclerview.setAdapter(topRankChannleFemaleListAdapter);
                topRankChannleFemaleListAdapter.setItemChecked(0);
                topRankChannleFemaleListAdapter.setItemClickListener(new ClickFamelListener());
            }
        }
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
            topRankChannleListAdapter.notifyDataSetChanged();
            mPresenter.getRankList(mMaleCategoryList.get(0).get_id());

        } else {
            mFemaleCategoryList.clear();
            mFemaleCategoryList.addAll(rankingList.getFemale());
            topRankChannleFemaleListAdapter.notifyDataSetChanged();
            mPresenter.getRankList(mFemaleCategoryList.get(0).get_id());
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

    class ClickListener implements OnRvItemClickListener<RankingList.MaleBean> {

        @Override
        public void onItemClick(View view, int position, RankingList.MaleBean data) {
            mPresenter.getRankList(mMaleCategoryList.get(position).get_id());
        }
    }


    class ClickFamelListener implements OnRvItemClickListener<RankingList.FemaleBean> {

        @Override
        public void onItemClick(View view, int position, RankingList.FemaleBean data) {
            mPresenter.getRankList(mFemaleCategoryList.get(position).get_id());
        }
    }
}
