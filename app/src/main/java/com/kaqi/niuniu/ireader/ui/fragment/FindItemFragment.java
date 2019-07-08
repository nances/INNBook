package com.kaqi.niuniu.ireader.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.RecommendListBean;
import com.kaqi.niuniu.ireader.model.flag.RecommendBookSelfType;
import com.kaqi.niuniu.ireader.presenter.RecommendBookPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.RecommendBookContract;
import com.kaqi.niuniu.ireader.ui.adapter.section.HomeRecommendActivityCenterSection;
import com.kaqi.niuniu.ireader.ui.adapter.section.HomeRecommendBannerSection;
import com.kaqi.niuniu.ireader.ui.adapter.section.HomeRecommendedChannelSection;
import com.kaqi.niuniu.ireader.ui.adapter.section.HomeRecommendedSection;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPFragment;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.view.banner.BannerEntity;
import com.kaqi.niuniu.ireader.view.sectioned.SectionedRecyclerViewAdapter;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FindItemFragment extends BaseMVPFragment<RecommendBookContract.Presenter> implements RecommendBookContract.View {

    @BindView(R.id.recyclerview)
    ScrollRefreshRecyclerView mRecyclerView;

    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private List<BannerEntity> banners = new ArrayList<>();
    private List<RecommendListBean.DataBean.ResultBean> results = new ArrayList<>();

    HomeRecommendBannerSection homeRecommendBannerSection;
    private RecommendBookSelfType recommendBookSelfType;
    private static final String EXTRA_BOOK_SELF_TYPE = "extra_book_self_type";

    public static FindItemFragment newInstance(RecommendBookSelfType recommendBookSelfType) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK_SELF_TYPE, recommendBookSelfType);
        FindItemFragment fragment = new FindItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected RecommendBookContract.Presenter bindPresenter() {
        return new RecommendBookPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            recommendBookSelfType = (RecommendBookSelfType) savedInstanceState.getSerializable(EXTRA_BOOK_SELF_TYPE);
        } else {
            recommendBookSelfType = (RecommendBookSelfType) getArguments().getSerializable(EXTRA_BOOK_SELF_TYPE);
        }

        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mSectionedAdapter.getItemCount() > 0) {
                    processLogic();
                } else {
                    mRecyclerView.finishRefresh();
                }
            }
        });
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);

        initRecyclerView();

    }

    @Override
    protected void processLogic() {
        super.processLogic();
        setModeBannerList();
        mRecyclerView.startRefresh();
        mPresenter.getRecommendList();
    }

    public void initRecyclerView() {
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mSectionedAdapter);
    }


    @Override
    public void showRecommendList(RecommendListBean rankingList) {
        if (rankingList != null) {
            if (mRecyclerView.isRefreshing()) {
                mRecyclerView.finishRefresh();
            }

            results.clear();
            results.addAll(rankingList.getData().getResult());
            setDataTask(rankingList);
        }
    }

    @Override
    public void showErrorTip(String error) {

    }


    /**
     * Banner Data
     */

    public void setModeBannerList() {
        banners.clear();
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/f2e14575271f3194daa26592ca76f68cc075d7f2.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/01f5de4cca62444dae00917c6db484220ca35944.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/f2e14575271f3194daa26592ca76f68cc075d7f2.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/a35eef8e17acbd6b2591e6c700c8b1c26f851376.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/463f860b9659e2a136f616b9d63e72fe375145b6s.jpg", 1));

    }

    /**
     * 设置数据
     *
     * @param rankingList
     */
    public void setDataTask(RecommendListBean rankingList) {
        homeRecommendBannerSection = new HomeRecommendBannerSection(banners);
        mSectionedAdapter.addSection(homeRecommendBannerSection);
        if (recommendBookSelfType != null && recommendBookSelfType.getNetName().equals("recommend_hots")) {
            mSectionedAdapter.addSection(new HomeRecommendedChannelSection(getActivity()));
        }

        int size = results.size();
        String type = "";
        for (int i = 0; i < size; i++) {
            type = results.get(i).getHead().getType();
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case Constant.TYPE_ACTIVITY_CENTER:
                        mSectionedAdapter.addSection(new HomeRecommendActivityCenterSection(
                                getActivity(),
                                results.get(i).getBody()));
                        break;
                    default:
                        mSectionedAdapter.addSection(new HomeRecommendedSection(
                                getActivity(),
                                results.get(i).getHead().getTitle(),
                                results.get(i).getHead().getType(),
                                results.get(i).getBody()));
                        break;
                }
            }
        }
        mSectionedAdapter.notifyDataSetChanged();
    }


    @Override
    protected int getContentId() {
        return R.layout.fragment_community;
    }


    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            homeRecommendBannerSection.onMZbannerPause();
        } else {
            homeRecommendBannerSection.onMZbannerStart();
        }
    }

}
