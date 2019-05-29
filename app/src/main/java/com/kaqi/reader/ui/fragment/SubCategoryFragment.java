package com.kaqi.reader.ui.fragment;

import android.os.Bundle;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseRVFragment;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.bean.BooksByCats;
import com.kaqi.reader.bean.support.SubEvent;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerFindComponent;
import com.kaqi.reader.ui.activity.BookDetailActivity;
import com.kaqi.reader.ui.contract.SubCategoryFragmentContract;
import com.kaqi.reader.ui.easyadapter.SubCategoryAdapter;
import com.kaqi.reader.ui.presenter.SubCategoryFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 二级分类
 *
 * @author yuyh.
 * @date 16/9/1.
 */
public class SubCategoryFragment extends BaseRVFragment<SubCategoryFragmentPresenter, BooksByCats.BooksBean> implements SubCategoryFragmentContract.View {

    public final static String BUNDLE_MAJOR = "major";
    public final static String BUNDLE_MINOR = "minor";
    public final static String BUNDLE_GENDER = "gender";
    public final static String BUNDLE_TYPE = "type";

    public static SubCategoryFragment newInstance(String major, String minor, String gender,
                                                  @Constant.CateType String type) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MAJOR, major);
        bundle.putString(BUNDLE_GENDER, gender);
        bundle.putString(BUNDLE_MINOR, minor);
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String major = "";
    private String minor = "";
    private String gender = "";
    private String type = "";

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        major = getArguments().getString(BUNDLE_MAJOR);
        gender = getArguments().getString(BUNDLE_GENDER);
        minor = getArguments().getString(BUNDLE_MINOR);
        type = getArguments().getString(BUNDLE_TYPE);
    }

    @Override
    public void configViews() {
        initAdapter(SubCategoryAdapter.class, true, true);
        onRefresh();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showCategoryList(BooksByCats data, boolean isRefresh) {
        if (isRefresh) {
            start = 0;
            mAdapter.clear();
        }
        mAdapter.addAll(data.books);
        mAdapter.notifyDataSetChanged();
        start += data.books.size();
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
    public void initCategoryList(SubEvent event) {
        minor = event.minor;
        String type = event.type;
        if (this.type.equals(type)) {
            onRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(int position) {
        BooksByCats.BooksBean data = mAdapter.getItem(position);
        BookDetailActivity.startActivity(activity, data._id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getCategoryList(gender, major, minor, this.type, 0, limit);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getCategoryList(gender, major, minor, this.type, start, limit);
    }
}
