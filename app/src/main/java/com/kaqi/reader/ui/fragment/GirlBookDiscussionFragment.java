package com.kaqi.reader.ui.fragment;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseRVFragment;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.bean.DiscussionList;
import com.kaqi.reader.bean.support.SelectionEvent;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerCommunityComponent;
import com.kaqi.reader.ui.activity.BookDiscussionDetailActivity;
import com.kaqi.reader.ui.contract.GirlBookDiscussionContract;
import com.kaqi.reader.ui.easyadapter.BookDiscussionAdapter;
import com.kaqi.reader.ui.presenter.GirlBookDiscussionPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 女生区Fragment
 *
 * @author lfh.
 * @date 16/9/8.
 */
public class GirlBookDiscussionFragment extends BaseRVFragment<GirlBookDiscussionPresenter, DiscussionList.PostsBean> implements GirlBookDiscussionContract.View {

    private String sort = Constant.SortType.DEFAULT;
    private String distillate = Constant.Distillate.ALL;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        initAdapter(BookDiscussionAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void showGirlBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            start = 0;
        }
        mAdapter.addAll(list);
        start = start + list.size();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SelectionEvent event) {
        mRecyclerView.setRefreshing(true);
        sort = event.sort;
        distillate = event.distillate;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getGirlBookDisscussionList(sort, distillate, 0, limit);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGirlBookDisscussionList(sort, distillate, start, limit);
    }

    @Override
    public void onItemClick(int position) {
        DiscussionList.PostsBean data = mAdapter.getItem(position);
        BookDiscussionDetailActivity.startActivity(activity, data._id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
