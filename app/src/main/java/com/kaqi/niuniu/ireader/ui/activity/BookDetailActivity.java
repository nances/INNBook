package com.kaqi.niuniu.ireader.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BookDetailBean;
import com.kaqi.niuniu.ireader.model.bean.BookListBean;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.bean.HotCommentBean;
import com.kaqi.niuniu.ireader.model.local.BookRepository;
import com.kaqi.niuniu.ireader.presenter.BookDetailPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.BookDetailContract;
import com.kaqi.niuniu.ireader.ui.adapter.BookListAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.HotCommentAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPActivity;
import com.kaqi.niuniu.ireader.utils.BlurTransformation;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.StringUtils;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.widget.RefreshLayout;
import com.kaqi.niuniu.ireader.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by newbiechen on 17-5-4.
 */

public class BookDetailActivity extends BaseMVPActivity<BookDetailContract.Presenter>
        implements BookDetailContract.View {
    public static final String RESULT_IS_COLLECTED = "result_is_collected";

    private static final String TAG = "BookDetailActivity";
    private static final String EXTRA_BOOK_ID = "extra_book_id";

    private static final int REQUEST_READ = 1;
    //    @BindView(R.id.common_toolbar)
//    NormalTitleBar commonToolbar;
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.book_detail_iv_cover)
    ImageView mIvCover;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.book_detail_iv_cover_bg)
    ImageView book_detail_iv_cover_bg;
    @BindView(R.id.book_detail_tv_title)
    TextView mTvTitle;
    @BindView(R.id.book_detail_tv_author)
    TextView mTvAuthor;
    @BindView(R.id.book_detail_tv_type)
    TextView mTvType;
    @BindView(R.id.book_detail_tv_word_count)
    TextView mTvWordCount;
    @BindView(R.id.book_detail_tv_lately_update)
    TextView mTvLatelyUpdate;
    @BindView(R.id.book_list_tv_chase)
    TextView mTvChase;
    @BindView(R.id.book_detail_tv_read)
    TextView mTvRead;
    @BindView(R.id.mulu_tv)
    TextView muluTv;
    @BindView(R.id.book_detail_tv_brief)
    TextView mTvBrief;
    @BindView(R.id.book_list_tv_recommend_book_list)
    TextView mTvRecommendBookList;
    @BindView(R.id.book_detail_rv_recommend_book_list)
    RecyclerView mRvRecommendBookList;


    /************************************/
    private HotCommentAdapter mHotCommentAdapter;
    private BookListAdapter mBookListAdapter;
    private CollBookBean mCollBookBean;
    private ProgressDialog mProgressDialog;
    /*******************************************/
    private String mBookId;
    private boolean isBriefOpen = false;
    private boolean isCollected = false;

    public static void startActivity(Context context, String bookId) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected BookDetailContract.Presenter bindPresenter() {
        return new BookDetailPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mBookId = savedInstanceState.getString(EXTRA_BOOK_ID);
        } else {
            mBookId = getIntent().getStringExtra(EXTRA_BOOK_ID);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        commonToolbar.setBackVisibility(true);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookDetail(mBookId);
    }

    @Override
    public void finishRefresh(BookDetailBean bean) {
        //封面
        Glide.with(this)
                .load(Constant.IMG_BASE_URL + bean.getCover())
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .centerCrop()
                .into(mIvCover);

        Glide.with(this).load(Constant.IMG_BASE_URL + bean.getCover()).bitmapTransform(new BlurTransformation(this, 25)).crossFade(500).into(book_detail_iv_cover_bg);


        //书名
        mTvTitle.setText(bean.getTitle());
        //作者
        mTvAuthor.setText(bean.getAuthor());
        //类型
        mTvType.setText(bean.getMajorCate());
        //目录
        muluTv.setText(bean.getLastChapter());
        //总字数
        mTvWordCount.setText(getResources().getString(R.string.nb_book_word, bean.getWordCount() / 10000));
        //更新时间
        mTvLatelyUpdate.setText(StringUtils.dateConvert(bean.getUpdated(), Constant.FORMAT_BOOK_DATE));
        //简介
        mTvBrief.setText(bean.getLongIntro());
        mCollBookBean = BookRepository.getInstance().getCollBook(bean.get_id());

        //判断是否收藏
        if (mCollBookBean != null) {
            isCollected = true;
            mTvChase.setText(getResources().getString(R.string.nb_book_detail_give_up));
            mTvRead.setText("继续阅读");
        } else {
            mCollBookBean = bean.getCollBookBean();
        }
    }

    @Override
    public void finishHotComment(List<HotCommentBean> beans) {
        if (beans.isEmpty()) {
            return;
        }
    }

    @Override
    public void finishRecommendBookList(List<BookListBean> beans) {
        if (beans.isEmpty()) {
            mTvRecommendBookList.setVisibility(View.GONE);
            return;
        }
        //推荐书单列表
        mBookListAdapter = new BookListAdapter();
        mRvRecommendBookList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                //与外部ScrollView滑动冲突
                return false;
            }
        });
        mRvRecommendBookList.addItemDecoration(new DividerItemDecoration(this));
        mRvRecommendBookList.setAdapter(mBookListAdapter);
        mBookListAdapter.addItems(beans);
    }

    @Override
    public void waitToBookShelf() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("正在添加到书架中");
        }
        mProgressDialog.show();
    }

    @Override
    public void errorToBookShelf() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        ToastUtils.show("加入书架失败，请检查网络");
    }

    @Override
    public void succeedToBookShelf() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        ToastUtils.show("加入书架成功");
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }

    /*******************************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_BOOK_ID, mBookId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果进入阅读页面收藏了，页面结束的时候，就需要返回改变收藏按钮
        if (requestCode == REQUEST_READ) {
            if (data == null) {
                return;
            }

            isCollected = data.getBooleanExtra(RESULT_IS_COLLECTED, false);

            if (isCollected) {
                mTvChase.setText(getResources().getString(R.string.nb_book_detail_give_up));
                mTvRead.setText("继续阅读");
            }
        }
    }

    @OnClick({R.id.book_detail_tv_brief, R.id.book_list_tv_chase, R.id.book_detail_tv_read, R.id.book_catalog_rl, R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_detail_tv_brief:
                if (isBriefOpen) {
                    mTvBrief.setMaxLines(20);
                    isBriefOpen = false;
                } else {
                    mTvBrief.setMaxLines(4);
                    isBriefOpen = true;
                }
                break;
            case R.id.book_list_tv_chase:
                if (isCollected) {
                    BookRepository.getInstance()
                            .deleteCollBookInRx(mCollBookBean);
                    mTvChase.setText(getResources().getString(R.string.nb_book_detail_chase_update));
                    isCollected = false;
                } else {
                    mPresenter.addToBookShelf(mCollBookBean);
                    mTvChase.setText(getResources().getString(R.string.nb_book_detail_give_up));
                    isCollected = true;
                }
                break;
            case R.id.book_detail_tv_read:
                startActivityForResult(new Intent(this, ReadActivity.class)
                        .putExtra(ReadActivity.EXTRA_IS_COLLECTED, isCollected)
                        .putExtra(ReadActivity.EXTRA_COLL_BOOK, mCollBookBean), REQUEST_READ);
                break;
            case R.id.book_catalog_rl:
                startActivityForResult(new Intent(this, BookCatalogActivity.class)
                        .putExtra(BookCatalogActivity.EXTRA_BOOK_ID, mBookId)
                        .putExtra(ReadActivity.EXTRA_IS_COLLECTED, isCollected)
                        .putExtra(ReadActivity.EXTRA_COLL_BOOK, mCollBookBean), REQUEST_READ);
                break;
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
