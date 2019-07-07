package com.kaqi.niuniu.ireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BooksByCats;
import com.kaqi.niuniu.ireader.model.bean.CategoryList;
import com.kaqi.niuniu.ireader.model.bean.RankingList;
import com.kaqi.niuniu.ireader.presenter.BookChannelPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.BookChannelContract;
import com.kaqi.niuniu.ireader.ui.adapter.TopCategoryFemaleListAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.TopCategoryMaleListAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.TopCategoryPressListAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPFragment;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClassFicationItemFragment extends BaseMVPFragment<BookChannelContract.Presenter> implements BookChannelContract.View {

    private String Type_Channle;
    @BindView(R.id.recyclerview)
    ScrollRefreshRecyclerView recyclerview;

    private TopCategoryMaleListAdapter mMaleCategoryListAdapter;
    private TopCategoryFemaleListAdapter mFemaleCategoryListAdapter;
    private TopCategoryPressListAdapter mPressCategoryListAdapter;
    private List<CategoryList.MaleBean> mMaleCategoryList = new ArrayList<>();
    private List<CategoryList.FemaleBean> mFemaleCategoryList = new ArrayList<>();
    private List<CategoryList.PressBean> mPressCategoryList = new ArrayList<>();


    @Override
    protected int getContentId() {
        return R.layout.fragment_community;
    }


    public static ClassFicationItemFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        ClassFicationItemFragment fragment = new ClassFicationItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        recyclerview.startRefresh();
        recyclerview.setEnabled(false);
        mPresenter.getCategoryList();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (getArguments() != null) {
            Type_Channle = getArguments().getString("type");
        }

    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void showCategoryList(CategoryList data) {
        if (data != null) {
            mMaleCategoryList.clear();
            mFemaleCategoryList.clear();
            mMaleCategoryList.addAll(data.getMale());
            mFemaleCategoryList.addAll(data.getFemale());
            mPressCategoryList.addAll(data.getPress());
            if (!"".equals(Type_Channle)) {
                if (Type_Channle.equals(getString(R.string.nb_fragment_book_list_boys))) {
                    mMaleCategoryListAdapter = new TopCategoryMaleListAdapter(getActivity(), mMaleCategoryList);
                    recyclerview.setAdapter(mMaleCategoryListAdapter);
                    mMaleCategoryListAdapter.notifyDataSetChanged();
                } else if (Type_Channle.equals(getString(R.string.nb_fragment_book_list_grils))) {
                    mFemaleCategoryListAdapter = new TopCategoryFemaleListAdapter(getActivity(), mFemaleCategoryList);
                    recyclerview.setAdapter(mFemaleCategoryListAdapter);
                    mFemaleCategoryListAdapter.notifyDataSetChanged();
                } else if (Type_Channle.equals("文学")) {
                    mPressCategoryListAdapter = new TopCategoryPressListAdapter(getActivity(), mPressCategoryList);
                    recyclerview.setAdapter(mPressCategoryListAdapter);
                    mPressCategoryListAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void showRankList(RankingList rankingList) {

    }

    @Override
    public void showRankList(BooksByCats data) {

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
}
