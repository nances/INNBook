package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BookChapterBean;
import com.kaqi.niuniu.ireader.model.bean.BookRecordBean;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.local.BookRepository;
import com.kaqi.niuniu.ireader.presenter.ReadPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.ReadContract;
import com.kaqi.niuniu.ireader.ui.adapter.CollBookCatalogAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPActivity;
import com.kaqi.niuniu.ireader.ui.base.adapter.BaseListAdapter;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by niqiao on 2019年06月30日14:44:35.
 */

public class BookCatalogActivity extends BaseMVPActivity<ReadContract.Presenter>
        implements ReadContract.View {
    private static final String TAG = "BookCatalogActivity";
    private static final int REQUEST_READ = 1;
    public static String EXTRA_BOOK_ID = "bookId";

    public static final String EXTRA_COLL_BOOK = "extra_coll_book";
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";
    @BindView(R.id.recyclerview)
    ScrollRefreshRecyclerView mRefreshLayout;
    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    private LinearLayoutManager mManager;

    private CollBookCatalogAdapter bookDetailCatalogAdapter;
    private BookRecordBean mBookRecord;
    private LinearSmoothScroller smoothScroller;
    private CollBookBean mCollBook;
    private String mBookId = "";
    private boolean iscollected = false;

    public static void startActivity(Context context, String bookId, boolean iscollected, CollBookBean collBookBean) {
        context.startActivity(new Intent(context, ReadActivity.class)
                .putExtra(EXTRA_BOOK_ID, bookId)
                .putExtra(EXTRA_IS_COLLECTED, iscollected)
                .putExtra(EXTRA_COLL_BOOK, collBookBean));
    }

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case 1:
                smoothScroller.setTargetPosition(mBookRecord.getChapter());
                mManager.startSmoothScroll(smoothScroller);
                break;
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.book_catalog_list;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        commonToolbar.setTitleText("目录");
        commonToolbar.setBackVisibility(true);
        smoothScroller = new TopSmoothScroller(this);
        setUpAdapter();
    }

    private void setUpAdapter() {
        bookDetailCatalogAdapter = new CollBookCatalogAdapter();
        mManager = new LinearLayoutManager(this);
        mRefreshLayout.setLayoutManager(mManager);
        mRefreshLayout.setAdapter(bookDetailCatalogAdapter);
        mRefreshLayout.setEnabled(false);
        mBookRecord = BookRepository.getInstance()
                .getBookRecord(mBookId);
    }

    @Override
    protected void initClick() {
        super.initClick();
        bookDetailCatalogAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                saveRecord(pos);
            }
        });
    }

    @Override
    protected ReadContract.Presenter bindPresenter() {
        return new ReadPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mBookId = savedInstanceState.getString(EXTRA_BOOK_ID);
        } else {
            mBookId = getIntent().getStringExtra(EXTRA_BOOK_ID);
            mCollBook = getIntent().getParcelableExtra(EXTRA_COLL_BOOK);
            iscollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonToolbar.setBackVisibility(true);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadCategory(mBookId);
    }

    @Override
    public void showError() {
    }

    @Override
    public void complete() {
    }

    /*******************************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果进入阅读页面收藏了，页面结束的时候，就需要返回改变收藏按钮
        if (requestCode == REQUEST_READ) {
            if (data == null) {
                return;
            }

        }
    }

    @Override
    public void showCategory(List<BookChapterBean> bookChapterList) {
        if (bookChapterList != null && bookChapterList.size() > 0) {
            bookDetailCatalogAdapter.refreshItems(bookChapterList);
            bookDetailCatalogAdapter.notifyDataSetChanged();
            if (mBookRecord != null) {
                mHandler.sendEmptyMessageDelayed(1, 300);
            }
        }
    }

    @Override
    public void finishChapter() {
    }

    @Override
    public void errorChapter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void saveRecord(int position) {
        // 存储阅读记录类
        if (mBookRecord == null) {
            mBookRecord = new BookRecordBean();
        }
        mBookRecord.setBookId(mBookId);
        mBookRecord.setChapter(position);
        mBookRecord.setPagePos(0);
        //存储到数据库
        BookRepository.getInstance()
                .saveBookRecord(mBookRecord);
        ReadActivity.startActivity(this,
                mCollBook, iscollected);
    }

    public class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }
    }
}
