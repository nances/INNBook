package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.local.BookRepository;
import com.kaqi.niuniu.ireader.ui.adapter.CollBookAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import java.io.File;

import butterknife.BindView;

public class ReadBookHistoryActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    @BindView(R.id.book_shelf_rv_content)
    ScrollRefreshRecyclerView mRvContent;

    private CollBookAdapter mCollBookAdapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ReadBookHistoryActivity.class));
    }

    @Override
    protected void initWidget() {
        commonToolbar.setTitleText("阅读记录");
        commonToolbar.setBackVisibility(true);
        initAdapter();
        mCollBookAdapter.refreshItems(BookRepository
                .getInstance().getHistoryCollBooks());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initClick() {
        super.initClick();
        mCollBookAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是本地文件，首先判断这个文件是否存在
                    CollBookBean collBook = mCollBookAdapter.getItem(pos);
                    if (collBook.isLocal()) {
                        //id表示本地文件的路径
                        String path = collBook.getCover();
                        File file = new File(path);
                        //判断这个本地文件是否存在
                        if (file.exists() && file.length() != 0) {
                            ReadActivity.startActivity(this,
                                    mCollBookAdapter.getItem(pos), true);
                        }
                    } else {
                        ReadActivity.startActivity(this,
                                mCollBookAdapter.getItem(pos), true);
                    }
                }
        );

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

    /**
     * 初始化Adapter
     */
    public void initAdapter() {
        //添加Footer
        mCollBookAdapter = new CollBookAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mCollBookAdapter);
        mRvContent.setEnabled(false);
    }
}
