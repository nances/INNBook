package com.kaqi.reader.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseRVActivity;
import com.kaqi.reader.bean.BookLists;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.manager.CacheManager;
import com.kaqi.reader.ui.easyadapter.SubjectBookListAdapter;
import com.kaqi.reader.utils.NormalTitleBar;
import com.kaqi.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的书单
 */
public class MyBookListActivity extends BaseRVActivity<BookLists.BookListsBean> implements RecyclerArrayAdapter.OnItemLongClickListener {

    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        commonToolbar.setTitleText(R.string.subject_book_list_my_book_list);
        commonToolbar.setBackVisibility(true);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        initAdapter(SubjectBookListAdapter.class, true, false);
        mAdapter.setOnItemLongClickListener(this);
        onRefresh();
    }

    @Override
    public void onItemClick(int position) {
        SubjectBookListDetailActivity.startActivity(this, mAdapter.getItem(position));
    }

    @Override
    public boolean onItemLongClick(int position) {
        showLongClickDialog(position);
        return false;
    }

    /**
     * 显示长按对话框
     *
     * @param position
     */
    private void showLongClickDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(mAdapter.getItem(position).title)
                .setItems(getResources().getStringArray(R.array.my_book_list_item_long_click_choice),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        //删除
                                        CacheManager.getInstance().removeCollection(mAdapter.getItem(position)._id);
                                        mAdapter.remove(position);
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(null, null)
                .create().show();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        List<BookLists.BookListsBean> data = CacheManager.getInstance().getCollectionList();
        mAdapter.clear();
        mAdapter.addAll(data);
        mRecyclerView.setRefreshing(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
