package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.packages.SearchBookPackage;
import com.kaqi.niuniu.ireader.presenter.SearchPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.SearchContract;
import com.kaqi.niuniu.ireader.ui.adapter.KeyWordAdapter;
import com.kaqi.niuniu.ireader.ui.adapter.SearchBookAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPActivity;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.view.TagColor;
import com.kaqi.niuniu.ireader.view.TagGroup;
import com.kaqi.niuniu.ireader.widget.CleanableEditText;
import com.kaqi.niuniu.ireader.widget.RefreshLayout;
import com.kaqi.niuniu.ireader.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by newbiechen on 17-4-24.
 */

public class SearchActivity extends BaseMVPActivity<SearchContract.Presenter>
        implements SearchContract.View {
    private static final String TAG = "SearchActivity";
    private static final int TAG_LIMIT = 8;

    public static final String INTENT_QUERY = "query";
    @BindView(R.id.search_content)
    CleanableEditText searchContent;
    @BindView(R.id.search_cancle)
    TextView searchCancle;
    @BindView(R.id.tvSearchHistory)
    TextView tvSearchHistory;
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvSearch;
    @BindView(R.id.tvChangeWords)
    TextView mTvChangeWords;
    @BindView(R.id.tag_group)
    TagGroup mTagGroup;
    @BindView(R.id.rootLayout)
    LinearLayout mRootLayout;
    @BindView(R.id.layoutHotWord)
    RelativeLayout mLayoutHotWord;
    @BindView(R.id.rlHistory)
    RelativeLayout rlHistory;
    @BindView(R.id.tvClear)
    TextView tvClear;
    @BindView(R.id.lvSearchHistory)
    ListView lvSearchHistory;
    private KeyWordAdapter mKeyWordAdapter;
    private SearchBookAdapter mSearchAdapter;

    private boolean isTag;
    private List<String> mHotTagList;
    private int mTagStart = 0;

    @Override
    protected int getContentId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchContract.Presenter bindPresenter() {
        return new SearchPresenter();
    }


    public static void startActivity(Context context, String query) {
        if (!query.equals("")) {
            context.startActivity(new Intent(context, SearchActivity.class)
                    .putExtra(INTENT_QUERY, query));
        } else {
            Intent intent = new Intent(context, SearchActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
        mRlRefresh.setBackground(ContextCompat.getDrawable(this, R.color.white));
        setOnEditorctionLis();
    }

    private void setUpAdapter() {
        mKeyWordAdapter = new KeyWordAdapter();
        mSearchAdapter = new SearchBookAdapter();

        mRvSearch.setLayoutManager(new LinearLayoutManager(this));
        mRvSearch.addItemDecoration(new DividerItemDecoration(this));
    }

    @Override
    protected void initClick() {
        super.initClick();

        //退出
        searchCancle.setOnClickListener(
                (v) -> onBackPressed()
        );

        //点击查书
        mKeyWordAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //显示正在加载
                    mRlRefresh.showLoading();
                    String book = mKeyWordAdapter.getItem(pos);
                    mPresenter.searchBook(book);
                    toggleKeyboard();
                }
        );

        //Tag的点击事件
        mTagGroup.setOnTagClickListener(
                tag -> {
                    isTag = true;
                    searchContent.setText(tag);
                    searchBook();
                }
        );

        //Tag的刷新事件
        mTvChangeWords.setOnClickListener(
                (v) -> refreshTag()
        );

        //书本的点击事件
        mSearchAdapter.setOnItemClickListener(
                (view, pos) -> {
                    String bookId = mSearchAdapter.getItem(pos).get_id();
                    BookDetailActivity.startActivity(this, bookId);
                }
        );
    }

    /**
     * 监听软键盘搜索按钮
     */
    public void setOnEditorctionLis() {
        searchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionid, KeyEvent keyEvent) {
                if (actionid == EditorInfo.IME_ACTION_SEARCH) {
                    if (!searchContent.getText().toString().trim().isEmpty()) {
                        InputMethodManager imm = (InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(searchContent.getWindowToken(), 0);
                        searchBook();
                    } else {
                        ToastUtils.show("搜索内容不能为空...");
                    }
                }
                return false;
            }
        });
    }

    private void searchBook() {
        String query = searchContent.getText().toString().trim();
        if (!query.equals("")) {
            mRlRefresh.setVisibility(View.VISIBLE);
            mRlRefresh.showLoading();
            mPresenter.searchBook(query);
            //显示正在加载
            mRlRefresh.showLoading();
            toggleKeyboard();
        }
    }

    private void toggleKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //默认隐藏
        mRlRefresh.setVisibility(View.GONE);
        //获取热词
        mPresenter.searchHotWord();
    }

    @Override
    public void showError() {
    }

    @Override
    public void complete() {

    }

    @Override
    public void finishHotWords(List<String> hotWords) {
        mHotTagList = hotWords;
//        refreshTag();
        mTagStart = 0;
        showHotWord();
    }

    /**
     * 每次显示8个热搜词
     */
    private synchronized void showHotWord() {
        int tagSize = 8;
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < mHotTagList.size(); mTagStart++, j++) {
            tags[j] = mHotTagList.get(mTagStart % mHotTagList.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        mTagGroup.setTags(colors, tags);
    }


    private void refreshTag() {
        int last = mTagStart + TAG_LIMIT;
        if (mHotTagList.size() <= last) {
            mTagStart = 0;
            last = TAG_LIMIT;
        }
        List<String> tags = mHotTagList.subList(mTagStart, last);
        mTagGroup.setTags(tags);
        mTagStart += TAG_LIMIT;
    }

    @Override
    public void finishKeyWords(List<String> keyWords) {
        if (keyWords.size() == 0) mRlRefresh.setVisibility(View.INVISIBLE);
        mKeyWordAdapter.refreshItems(keyWords);
        if (!(mRvSearch.getAdapter() instanceof KeyWordAdapter)) {
            mRvSearch.setAdapter(mKeyWordAdapter);
        }
    }

    @Override
    public void finishBooks(List<SearchBookPackage.BooksBean> books) {
        mSearchAdapter.refreshItems(books);
        if (books.size() == 0) {
            mRlRefresh.showEmpty();
        } else {
            //显示完成
            mRlRefresh.showFinish();
        }
        //加载
        if (!(mRvSearch.getAdapter() instanceof SearchBookAdapter)) {
            mRvSearch.setAdapter(mSearchAdapter);
        }
    }

    @Override
    public void errorBooks() {
        mRlRefresh.showEmpty();
    }

    @Override
    public void onBackPressed() {
        if (mRlRefresh.getVisibility() == View.VISIBLE) {
            searchContent.setText("");
            finish();
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }
}
