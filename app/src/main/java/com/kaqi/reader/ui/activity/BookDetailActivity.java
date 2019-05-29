/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaqi.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.bean.BookDetail;
import com.kaqi.reader.bean.BookLists;
import com.kaqi.reader.bean.HotReview;
import com.kaqi.reader.bean.Recommend;
import com.kaqi.reader.bean.RecommendBookList;
import com.kaqi.reader.bean.support.RefreshCollectionIconEvent;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerBookComponent;
import com.kaqi.reader.manager.CollectionsManager;
import com.kaqi.reader.ui.adapter.HotReviewAdapter;
import com.kaqi.reader.ui.adapter.RecommendBookListAdapter;
import com.kaqi.reader.ui.contract.BookDetailContract;
import com.kaqi.reader.ui.presenter.BookDetailPresenter;
import com.kaqi.reader.utils.FormatUtils;
import com.kaqi.reader.utils.StatusBarUtil;
import com.kaqi.reader.utils.ToastUtils;
import com.kaqi.reader.view.AppBarLayoutOverScrollViewBehavior;
import com.kaqi.reader.view.DrawableCenterButton;
import com.kaqi.reader.view.TagColor;
import com.kaqi.reader.view.TagGroup;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class BookDetailActivity extends BaseActivity implements BookDetailContract.View, OnRvItemClickListener<Object> {

    public static String INTENT_BOOK_ID = "bookId";
    @Bind(R.id.book_catalog_rl)
    RelativeLayout bookCatalogRl;

    public static void startActivity(Context context, String bookId) {
        context.startActivity(new Intent(context, BookDetailActivity.class)
                .putExtra(INTENT_BOOK_ID, bookId));
    }

    @Bind(R.id.ivBookCover)
    ImageView mIvBookCover;
    @Bind(R.id.tvBookListTitle)
    TextView mTvBookTitle;
    @Bind(R.id.tvBookListAuthor)
    TextView mTvAuthor;
    @Bind(R.id.tvCatgory)
    TextView mTvCatgory;
    @Bind(R.id.tvWordCount)
    TextView mTvWordCount;
    @Bind(R.id.tvLatelyUpdate)
    TextView mTvLatelyUpdate;
    @Bind(R.id.btnRead)
    DrawableCenterButton mBtnRead;
    @Bind(R.id.btnJoinCollection)
    DrawableCenterButton mBtnJoinCollection;
    @Bind(R.id.tvLatelyFollower)
    TextView mTvLatelyFollower;
    @Bind(R.id.tvRetentionRatio)
    TextView mTvRetentionRatio;
    @Bind(R.id.tvSerializeWordCount)
    TextView mTvSerializeWordCount;
    @Bind(R.id.tag_group)
    TagGroup mTagGroup;
    @Bind(R.id.tvlongIntro)
    TextView mTvlongIntro;
    @Bind(R.id.tvMoreReview)
    TextView mTvMoreReview;
    @Bind(R.id.rvHotReview)
    RecyclerView mRvHotReview;
    @Bind(R.id.rlCommunity)
    RelativeLayout mRlCommunity;
    @Bind(R.id.tvCommunity)
    TextView mTvCommunity;
    @Bind(R.id.tvHelpfulYes)
    TextView mTvPostCount;
    @Bind(R.id.tvRecommendBookList)
    TextView mTvRecommendBookList;

    @Bind(R.id.rvRecommendBoookList)
    RecyclerView mRvRecommendBoookList;


    @Bind(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @Bind(R.id.container)
    CoordinatorLayout container;
    @Bind(R.id.title_center_layout)
    LinearLayout titleCenterLayout;
    @Bind(R.id.tv_back_info)
    ImageView tv_back_info;


    @Inject
    BookDetailPresenter mPresenter;

    private List<String> tagList = new ArrayList<>();
    private int times = 0;

    private HotReviewAdapter mHotReviewAdapter;
    private List<HotReview.Reviews> mHotReviewList = new ArrayList<>();
    private RecommendBookListAdapter mRecommendBookListAdapter;
    private List<RecommendBookList.RecommendBook> mRecommendBookList = new ArrayList<>();
    private String bookId;

    private boolean collapseLongIntro = true;
    private Recommend.RecommendBooks recommendBooks;
    private boolean isJoinedCollections = false;

    /**
     * @param alpha
     * @param state 0-正在变化 1展开 2 关闭
     */
    @Bind(R.id.tv_back)
    TextView tvBack;

    @Bind(R.id.title_uc_title)
    TextView title_uc_title;
    @Bind(R.id.mulu_tv)
    TextView muluTv;
    private int lastState = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
        mCommonToolbar.setTitle(R.string.book_detail);
    }

    @Override
    public void initDatas() {
        bookId = getIntent().getStringExtra(INTENT_BOOK_ID);
        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        initListener();
        mRvHotReview.setHasFixedSize(true);
        mRvHotReview.setLayoutManager(new LinearLayoutManager(this));
        mHotReviewAdapter = new HotReviewAdapter(mContext, mHotReviewList, this);
        mRvHotReview.setAdapter(mHotReviewAdapter);

        mRvRecommendBoookList.setHasFixedSize(true);
        mRvRecommendBoookList.setLayoutManager(new LinearLayoutManager(this));
        mRecommendBookListAdapter = new RecommendBookListAdapter(mContext, mRecommendBookList, this);
        mRvRecommendBoookList.setAdapter(mRecommendBookListAdapter);

        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                startActivity(new Intent(BookDetailActivity.this, BooksByTagActivity.class)
                        .putExtra("tag", tag));
            }
        });

        mPresenter.attachView(this);
        mPresenter.getBookDetail(bookId);
        mPresenter.getHotReview(bookId);
        mPresenter.getRecommendBookList(bookId, "3");
    }

    @Override
    public void showBookDetail(BookDetail data) {
        Glide.with(mContext)
                .load(Constant.IMG_BASE_URL + data.cover)
                .placeholder(R.drawable.cover_default)
                .transform(new GlideRoundTransform(mContext))
                .into(mIvBookCover);

        mTvBookTitle.setText(data.title);
        title_uc_title.setText(data.title);
        muluTv.setText(data.lastChapter);
        mTvAuthor.setText(String.format(getString(R.string.book_detail_author), data.author));
        mTvCatgory.setText(String.format(getString(R.string.book_detail_category), data.cat));
        mTvWordCount.setText(FormatUtils.formatWordCount(data.wordCount));
        mTvLatelyUpdate.setText(FormatUtils.getDescriptionTimeFromDateString(data.updated));
        mTvLatelyFollower.setText(String.valueOf(data.latelyFollower));
        mTvRetentionRatio.setText(TextUtils.isEmpty(data.retentionRatio) ?
                "-" : String.format(getString(R.string.book_detail_retention_ratio),
                data.retentionRatio));
        mTvSerializeWordCount.setText(data.serializeWordCount < 0 ? "-" :
                String.valueOf(data.serializeWordCount));

        tagList.clear();
        tagList.addAll(data.tags);
        times = 0;
        showHotWord();

        mTvlongIntro.setText(data.longIntro);
        mTvCommunity.setText(String.format(getString(R.string.book_detail_community), data.title));
        mTvPostCount.setText(String.format(getString(R.string.book_detail_post_count), data.postCount));

        recommendBooks = new Recommend.RecommendBooks();
        recommendBooks.title = data.title;
        recommendBooks._id = data._id;
        recommendBooks.cover = data.cover;
        recommendBooks.author = data.author;
        recommendBooks.lastChapter = data.lastChapter;
        recommendBooks.updated = data.updated;

        refreshCollectionIcon();
    }

    /**
     * 刷新收藏图标
     */
    private void refreshCollectionIcon() {
        if (CollectionsManager.getInstance().isCollected(recommendBooks._id)) {
            initCollection(false);
        } else {
            initCollection(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCollectionIcon(RefreshCollectionIconEvent event) {
        refreshCollectionIcon();
    }

    /**
     * 绑定事件
     */
    private void initListener() {
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                if (titleCenterLayout != null) {
                    titleCenterLayout.setAlpha(percent);
                    StatusBarUtil.setTranslucentForImageView(BookDetailActivity.this, (int) (255f * percent), null);
                    if (percent == 0) {
                        groupChange(1f, 1);
                    } else if (percent == 1) {
                        groupChange(1f, 2);
                    } else {
                        groupChange(percent, 0);
                    }

                }
            }
        });
        AppBarLayoutOverScrollViewBehavior myAppBarLayoutBehavoir = (AppBarLayoutOverScrollViewBehavior)
                ((CoordinatorLayout.LayoutParams) appbarLayout.getLayoutParams()).getBehavior();
        myAppBarLayoutBehavoir.setOnProgressChangeListener(new AppBarLayoutOverScrollViewBehavior.onProgressChangeListener() {
            @Override
            public void onProgressChange(float progress, boolean isRelease) {
            }
        });

    }


    public void groupChange(float alpha, int state) {
        lastState = state;

        switch (state) {
            case 1://完全展开 显示白色
                tvBack.setVisibility(View.GONE);
                title_uc_title.setVisibility(View.GONE);
                tv_back_info.setVisibility(View.VISIBLE);
                break;
            case 2://完全关闭 显示黑色
                title_uc_title.setVisibility(View.VISIBLE);
                tvBack.setVisibility(View.VISIBLE);
                tv_back_info.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 每次显示8个
     */
    private void showHotWord() {
        int start, end;
        if (times < tagList.size() && times + 8 <= tagList.size()) {
            start = times;
            end = times + 8;
        } else if (times < tagList.size() - 1 && times + 8 > tagList.size()) {
            start = times;
            end = tagList.size() - 1;
        } else {
            start = 0;
            end = tagList.size() > 8 ? 8 : tagList.size();
        }
        times = end;
        if (end - start > 0) {
            List<String> batch = tagList.subList(start, end);
            List<TagColor> colors = TagColor.getRandomColors(batch.size());
            mTagGroup.setTags(colors, (String[]) batch.toArray(new String[batch.size()]));
        }
    }

    @Override
    public void showHotReview(List<HotReview.Reviews> list) {
        mHotReviewList.clear();
        mHotReviewList.addAll(list);
        mHotReviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRecommendBookList(List<RecommendBookList.RecommendBook> list) {
        if (!list.isEmpty()) {
            mTvRecommendBookList.setVisibility(View.VISIBLE);
            mRecommendBookList.clear();
            mRecommendBookList.addAll(list);
            mRecommendBookListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        if (data instanceof HotReview.Reviews) {
            BookDiscussionDetailActivity.startActivity(this, ((HotReview.Reviews) data)._id);
        } else if (data instanceof RecommendBookList.RecommendBook) {
            RecommendBookList.RecommendBook recommendBook = (RecommendBookList.RecommendBook) data;

            BookLists bookLists = new BookLists();
            BookLists.BookListsBean bookListsBean = bookLists.new BookListsBean();
            bookListsBean._id = recommendBook.id;
            bookListsBean.author = recommendBook.author;
            bookListsBean.bookCount = recommendBook.bookCount;
            bookListsBean.collectorCount = recommendBook.collectorCount;
            bookListsBean.cover = recommendBook.cover;
            bookListsBean.desc = recommendBook.desc;
            bookListsBean.title = recommendBook.title;

            SubjectBookListDetailActivity.startActivity(this, bookListsBean);
        }
    }

    private void initCollection(boolean coll) {
        if (coll) {
            mBtnJoinCollection.setText(R.string.book_detail_join_collection);
            isJoinedCollections = false;
        } else {
            mBtnJoinCollection.setText(R.string.book_detail_remove_collection);
            mBtnJoinCollection.postInvalidate();
            isJoinedCollections = true;
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @OnClick({R.id.tv_back_info, R.id.tv_back, R.id.book_catalog_rl, R.id.tvMoreReview, R.id.btnJoinCollection, R.id.btnRead, R.id.tvBookListAuthor, R.id.tvlongIntro, R.id.rlCommunity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_info:
                Log.v("Nancy","tv_back_info ");
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.book_catalog_rl:
                BookCatalogActivity.startActivity(this, recommendBooks);
                break;
            case R.id.tvMoreReview:
                BookDetailCommunityActivity.startActivity(this, bookId, mTvBookTitle.getText().toString(), 1);
                break;
            case R.id.btnJoinCollection:
                if (!isJoinedCollections) {
                    if (recommendBooks != null) {
                        CollectionsManager.getInstance().add(recommendBooks);
                        ToastUtils.showToast(String.format(getString(
                                R.string.book_detail_has_joined_the_book_shelf), recommendBooks.title));
                        initCollection(false);
                    }
                } else {
                    CollectionsManager.getInstance().remove(recommendBooks._id);
                    ToastUtils.showToast(String.format(getString(
                            R.string.book_detail_has_remove_the_book_shelf), recommendBooks.title));
                    initCollection(true);
                }
                break;
            case R.id.btnRead:
                if (recommendBooks == null) return;
                ReadActivity.startActivity(this, recommendBooks);
                break;
            case R.id.tvBookListAuthor:
                String author = mTvAuthor.getText().toString().replaceAll(" ", "");
                SearchByAuthorActivity.startActivity(this, author);
                break;
            case R.id.tvlongIntro:
                if (collapseLongIntro) {
                    mTvlongIntro.setMaxLines(20);
                    collapseLongIntro = false;
                } else {
                    mTvlongIntro.setMaxLines(4);
                    collapseLongIntro = true;
                }
                break;
            case R.id.rlCommunity:
                BookDetailCommunityActivity.startActivity(this, bookId, mTvBookTitle.getText().toString(), 0);

                break;
        }
    }
}
