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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseRVActivity;
import com.kaqi.reader.bean.BookListDetail;
import com.kaqi.reader.bean.BookLists;
import com.kaqi.reader.bean.Recommend;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerMainComponent;
import com.kaqi.reader.manager.HistoryManager;
import com.kaqi.reader.ui.contract.SubjectBookListDetailContract;
import com.kaqi.reader.ui.easyadapter.ReadHistoryBooksAdapter;
import com.kaqi.reader.ui.presenter.SubjectBookListDetailPresenter;
import com.kaqi.reader.utils.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 阅读历史
 * 2019年04月27日00:13:26
 *
 * @author Nancy
 */
public class ReadBookHistoryActivity extends BaseRVActivity<Recommend.RecommendBooks> implements SubjectBookListDetailContract.View {

    @Bind(R.id.llBatchManagement)
    LinearLayout llBatchManagement;
    @Bind(R.id.tvSelectAll)
    RelativeLayout tvSelectAll;
    @Bind(R.id.tvDelete)
    RelativeLayout tvDelete;
    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    @Bind(R.id.tvSelecttv)
    TextView tvSelecttv;
    private List<BookListDetail.BookListBean.BooksBean> mAllBooks = new ArrayList<>();

    private int start = 0;
    private int limit = 20;
    private boolean isSelectAll = false;

    @Inject
    SubjectBookListDetailPresenter mPresenter;

    public static final String INTENT_BEAN = "bookListsBean";

    private BookLists.BookListsBean bookListsBean;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ReadBookHistoryActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_subject_book_list_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        commonToolbar.setTitleText(R.string.book_read_hsitory);
        commonToolbar.setBackVisibility(true);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        initAdapter(ReadHistoryBooksAdapter.class, false, false);
        mRecyclerView.removeAllItemDecoration();
        mPresenter.attachView(this);
        onRefresh();
    }

    @Override
    public void showBookListDetail(BookListDetail data) {


    }

    private void loadNextPage() {
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void onItemClick(int position) {
        BookDetailActivity.startActivity(this, mAdapter.getItem(position)._id);
    }

    @Override
    public void onRefresh() {
        List<Recommend.RecommendBooks> data = HistoryManager.getInstance().getHistoryList();
        if (mAdapter == null) return;
        mAdapter.addAll(data);
        //不加下面这句代码会导致，添加本地书籍的时候，部分书籍添加后直接崩溃
        //报错：Scrapped or attached views may not be recycled. isScrap:false isAttached:true
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subject_detail, menu);
        return true;
    }


    /**
     * 隐藏批量管理布局并刷新页面
     */
    public void goneBatchManagementAndRefreshUI() {
        if (mAdapter == null) return;
        gone(llBatchManagement);
        for (Recommend.RecommendBooks bean :
                mAdapter.getAllData()) {
            bean.showCheckBox = false;
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 显示批量管理布局
     */
    private void showBatchManagementLayout() {
        visible(llBatchManagement);
        for (Recommend.RecommendBooks bean : mAdapter.getAllData()) {
            bean.showCheckBox = true;
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_collect) {
//            CacheManager.getInstance().addCollection(bookListsBean);
            showBatchManagementLayout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvSelectAll, R.id.tvDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSelectAll:
                isSelectAll = !isSelectAll;
                tvSelecttv.setText(isSelectAll ? getResources().getString(R.string.cancel_selected_all) : getResources().getString(R.string.selected_all));
                for (Recommend.RecommendBooks bean : mAdapter.getAllData()) {
                    bean.isSeleted = isSelectAll;
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tvDelete:
                List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                for (Recommend.RecommendBooks bean : mAdapter.getAllData()) {
                    if (bean.isSeleted) removeList.add(bean);
                }
                if (removeList.isEmpty()) {
                    //没有书籍点击完成可以关闭当前视图
//            mRecyclerView.showTipViewAndDelayClose(activity.getString(R.string.has_not_selected_delete_book));
//            visible(recommendRL);
//            gone(llBatchManagement);
                    goneBatchManagementAndRefreshUI();
                } else {
                    showDeleteCacheDialog(removeList);
                }
                break;
        }
    }

    /**
     * 显示删除本地缓存对话框
     *
     * @param removeList
     */
    private void showDeleteCacheDialog(final List<Recommend.RecommendBooks> removeList) {
        final boolean selected[] = {true};
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.remove_selected_book))
                .setMultiChoiceItems(new String[]{getResources().getString(R.string.delete_local_cache)}, selected,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selected[0] = isChecked;
                            }
                        })
                .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new AsyncTask<String, String, String>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                showDialog();
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                HistoryManager.getInstance().removeSome(removeList, selected[0]);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                mRecyclerView.showTipViewAndDelayClose("成功移除书籍");
                                for (Recommend.RecommendBooks bean : removeList) {
                                    mAdapter.remove(bean);
                                }
                                if (isVisible(llBatchManagement)) {
                                    //批量管理完成后，隐藏批量管理布局并刷新页面
                                    goneBatchManagementAndRefreshUI();
                                }
                                hideDialog();
                            }
                        }.execute();

                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .create().show();
    }
}
