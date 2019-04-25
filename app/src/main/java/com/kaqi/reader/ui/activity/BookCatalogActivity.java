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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.bean.BookMixAToc;
import com.kaqi.reader.bean.ChapterRead;
import com.kaqi.reader.bean.Recommend;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerBookComponent;
import com.kaqi.reader.ui.adapter.BookCatalogListAdapter;
import com.kaqi.reader.ui.contract.BookReadContract;
import com.kaqi.reader.ui.presenter.BookReadPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by lfh on 2016/8/6.
 */
public class BookCatalogActivity extends BaseActivity implements BookReadContract.View, OnRvItemClickListener<BookMixAToc.mixToc.Chapters> {

    public static String INTENT_BOOK_ID = "bookId";
    @Inject
    BookReadPresenter mPresenter;
    @Bind(R.id.common_toolbar)
    Toolbar commonToolbar;
    @Bind(R.id.recyclerview)
    RecyclerView refreshLayout;

    private List<BookMixAToc.mixToc.Chapters> mChapterList = new ArrayList<>();
    private Recommend.RecommendBooks recommendBooks;
    private String bookId;
    private String author;
    private String cover;
    private String lastChapter;
    private String updated;

    public static final String INTENT_BEAN = "recommendBooksBean";
    public static final String CURRENT_CHAPTER = "current_chapter";
    public static final String INTENT_SD = "isFromSD";
    private BookCatalogListAdapter mTocListAdapter;

    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks) {
        startActivity(context, recommendBooks, false);
    }

    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks, boolean isFromSD) {
        context.startActivity(new Intent(context, BookCatalogActivity.class)
                .putExtra(INTENT_BEAN, recommendBooks)
                .putExtra(INTENT_SD, isFromSD));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_catalog_layout;
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
        refreshLayout.setHasFixedSize(true);
        refreshLayout.setLayoutManager(new LinearLayoutManager(this));
        mTocListAdapter = new BookCatalogListAdapter(this, mChapterList, bookId, 1, this);
        refreshLayout.setAdapter(mTocListAdapter);
    }

    @Override
    public void initDatas() {
        recommendBooks = (Recommend.RecommendBooks) getIntent().getSerializableExtra(INTENT_BEAN);
        bookId = recommendBooks._id;
        author = recommendBooks.author;
        author = recommendBooks.cover;
        lastChapter = recommendBooks.lastChapter;
        updated = recommendBooks.updated;
    }

    @Override
    public void configViews() {
        mPresenter.attachView(this);
        mPresenter.getBookMixAToc(bookId, "chapters", false);
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

    @Override
    public void showChapterRead(ChapterRead.Chapter data, int chapter) {

    }

    @Override
    public void showBookToc(List<BookMixAToc.mixToc.Chapters> list) {
        if (list != null) {
            mChapterList.clear();
            mChapterList.addAll(list);
            mTocListAdapter.setCataList(list);
        }
    }

    @Override
    public void netError(int chapter) {

    }

    @Override
    public void onItemClick(View view, int position, BookMixAToc.mixToc.Chapters data) {
        recommendBooks = new Recommend.RecommendBooks();
        recommendBooks.title = mChapterList.get(position).title;
        recommendBooks._id = bookId;
        recommendBooks.cover = cover;
        recommendBooks.lastChapter = lastChapter;
        recommendBooks.updated = updated;
        recommendBooks.author = author;
        recommendBooks.path = mChapterList.get(position).link;
        ReadActivity.startActivity(this, recommendBooks, position, true);
        finish();
    }
}
