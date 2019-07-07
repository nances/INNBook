package com.kaqi.niuniu.ireader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BooksByCats;
import com.kaqi.niuniu.ireader.model.bean.CategoryList;
import com.kaqi.niuniu.ireader.model.bean.RankingList;
import com.kaqi.niuniu.ireader.presenter.BookChannelPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.BookChannelContract;
import com.kaqi.niuniu.ireader.ui.adapter.TopCategoryPressListAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.TopRankListAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPFragment;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BookCompleteUpdateFragment extends BaseMVPFragment<BookChannelContract.Presenter> implements BookChannelContract.View {

    private String Type_Channle;
    private int type_complete_update;
    @BindView(R.id.recyclerview)
    ScrollRefreshRecyclerView recyclerview;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (getArguments() != null) {
            Type_Channle = getArguments().getString("type");
            type_complete_update = getArguments().getInt("type_complete_update");
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        topRankListAdapter = new TopRankListAdapter(getActivity(), booksBeans);
        recyclerview.setAdapter(topRankListAdapter);
        recyclerview.setEnabled(false);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        recyclerview.startRefresh();
        mPresenter.getRankList();

    }

    @Override
    public void showCategoryList(CategoryList data) {
    }

    @Override
    public void showRankList(RankingList rankingList) {

        if (Type_Channle.equals("男生")) {
            mMaleCategoryList.clear();
            mMaleCategoryList.addAll(rankingList.getMale());
            if (type_complete_update == 1) {
                mPresenter.getRankList("564eb878efe5b8e745508fde");
            } else if (type_complete_update == 2) {
                mPresenter.getRankList("5a6844f8fc84c2b8efaa8bc5");
            }
        } else {
            mFemaleCategoryList.clear();
            mFemaleCategoryList.addAll(rankingList.getFemale());
            if (type_complete_update == 1) {
                mPresenter.getRankList("564eb8a9cf77e9b25056162d");
            } else if (type_complete_update == 2) {
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
    public void showErrorTip(String error) {

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_book_complete_update_frament;
    }

    @Override
    protected BookChannelContract.Presenter bindPresenter() {
        return new BookChannelPresenter();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        if (recyclerview.isRefreshing()) {
            recyclerview.finishRefresh();
        }

    }
}
