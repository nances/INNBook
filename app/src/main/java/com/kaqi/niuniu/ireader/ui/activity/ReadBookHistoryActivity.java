package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

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
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


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
