package com.kaqi.reader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.bean.RecommendListBean;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerFindComponent;
import com.kaqi.reader.ui.adapter.FindItemAdapter;
import com.kaqi.reader.ui.adapter.section.HomeRecommendActivityCenterSection;
import com.kaqi.reader.ui.adapter.section.HomeRecommendBannerSection;
import com.kaqi.reader.ui.adapter.section.HomeRecommendedChannelSection;
import com.kaqi.reader.ui.adapter.section.HomeRecommendedSection;
import com.kaqi.reader.ui.contract.RecommendListContract;
import com.kaqi.reader.ui.presenter.RecommendListPresenter;
import com.kaqi.reader.utils.ConstantUtil;
import com.kaqi.reader.view.banner.BannerEntity;
import com.kaqi.reader.view.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class FindItemFragment extends BaseFragment implements RecommendListContract.View {

    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private List<BannerEntity> banners = new ArrayList<>();
    private List<RecommendListBean.DataBean.ResultBean> results = new ArrayList<>();

    private int type = 0;

    public static FindItemFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        FindItemFragment fragment = new FindItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    RecommendListPresenter mPresenter;

    private FindItemAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        type = getArguments().getInt("type");
        setModeBannerList();
        mPresenter.attachView(this);
        mPresenter.getRecommendList();

    }

    @Override
    public void configViews() {
//        mAdapter = new FindItemAdapter();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mAdapter.getSpanSize(position);
//            }
//        });
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);

//        mPresenter.attachView(this);
//        mPresenter.getRecommendList();

    }

    protected void initRecyclerView() {
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSectionedAdapter);
    }


    @Override
    public void showRecommendList(RecommendListBean rankingList) {
        if (rankingList != null) {
            results.clear();
            results.addAll(rankingList.getData().getResult());
            setDataTask(rankingList);
        }
    }

    public void setModeBannerList() {
        banners.clear();
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/f2e14575271f3194daa26592ca76f68cc075d7f2.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/01f5de4cca62444dae00917c6db484220ca35944.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/f2e14575271f3194daa26592ca76f68cc075d7f2.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/a35eef8e17acbd6b2591e6c700c8b1c26f851376.jpg", 1));
        banners.add(new BannerEntity("", "", "http://i0.hdslb.com/bfs/archive/463f860b9659e2a136f616b9d63e72fe375145b6.jpg", 1));

    }


    public void setDataTask(RecommendListBean rankingList) {
        initRecyclerView();
        mSectionedAdapter.addSection(new HomeRecommendBannerSection(banners));
        if (type == 1) {
            mSectionedAdapter.addSection(new HomeRecommendedChannelSection(getActivity()));
        }
        int size = results.size();
        String type = "";
        for (int i = 0; i < size; i++) {
            type = results.get(i).getHead().getType();

            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case ConstantUtil.TYPE_ACTIVITY_CENTER:
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
    public void showError() {

    }

    @Override
    public void complete() {

    }
}
