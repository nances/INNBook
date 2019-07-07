package com.kaqi.niuniu.ireader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BooksByCats;
import com.kaqi.niuniu.ireader.model.bean.CategoryList;
import com.kaqi.niuniu.ireader.model.bean.RankingList;
import com.kaqi.niuniu.ireader.presenter.BookChannelPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.BookChannelContract;
import com.kaqi.niuniu.ireader.ui.adapter.TopCategoryPressListAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.TopRankChannleFemaleListAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.TopRankChannleMaleListAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.TopRankListAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPFragment;
import com.kaqi.niuniu.ireader.utils.OnRvItemClickListener;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClassRankTopItemFragment extends BaseMVPFragment<BookChannelContract.Presenter> implements BookChannelContract.View  {

    private String Type_Channle;
    @BindView(R.id.recyclerview)
    ScrollRefreshRecyclerView recyclerview;
    @BindView(R.id.rank_recyclerview)
    RecyclerView rank_recyclerview;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

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
    protected int getContentId() {
        return R.layout.activity_rank_top_frament;
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        recyclerview.startRefresh();
        mPresenter.getRankList();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (getArguments() != null) {
            Type_Channle = getArguments().getString("type");
        }
        rank_recyclerview.setHasFixedSize(true);
        rank_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setEnabled(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        topRankListAdapter = new TopRankListAdapter(getContext(), booksBeans);
        recyclerview.setAdapter(topRankListAdapter);

        if (!"".equals(Type_Channle)) {
            if (Type_Channle.equals("男生")) {
                topRankChannleListAdapter = new TopRankChannleMaleListAdapter(getContext(), mMaleCategoryList);
                rank_recyclerview.setAdapter(topRankChannleListAdapter);
                topRankChannleListAdapter.setItemChecked(0);
                topRankChannleListAdapter.setItemClickListener(new ClickListener());
            } else if (Type_Channle.equals("女生")) {
                topRankChannleFemaleListAdapter = new TopRankChannleFemaleListAdapter(getActivity(), mFemaleCategoryList);
                rank_recyclerview.setAdapter(topRankChannleFemaleListAdapter);
                topRankChannleFemaleListAdapter.setItemChecked(0);
                topRankChannleFemaleListAdapter.setItemClickListener(new ClickFamelListener());
            }
        }

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
    public void showErrorTip(String error) {

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
        if(recyclerview.isRefreshing()){
            recyclerview.finishRefresh();
        }
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
