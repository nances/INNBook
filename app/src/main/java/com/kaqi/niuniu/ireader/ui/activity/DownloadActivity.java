package com.kaqi.niuniu.ireader.ui.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.DownloadTaskBean;
import com.kaqi.niuniu.ireader.service.DownloadService;
import com.kaqi.niuniu.ireader.ui.adapter.DownLoadAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.RefreshLayout;
import com.kaqi.niuniu.ireader.widget.itemdecoration.DividerItemDecoration;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import butterknife.BindView;

/**
 * Created by niqiao on 2019年07月11日12:44:44.
 * 下载面板
 */

public class DownloadActivity extends BaseActivity implements DownloadService.OnDownloadListener {
    private static final String TAG = "DownloadActivity";
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    ScrollRefreshRecyclerView mRvContent;
    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    private DownLoadAdapter mDownloadAdapter;

    private ServiceConnection mConn;
    private DownloadService.IDownloadManager mService;

    @Override
    protected int getContentId() {
        return R.layout.activity_refresh_list;
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, DownloadActivity.class));
    }


    @Override
    protected void initWidget() {
        commonToolbar.setBackVisibility(true);
        commonToolbar.setTitleText("下载中心");
        setUpAdapter();
    }

    private void setUpAdapter() {
        mDownloadAdapter = new DownLoadAdapter();
        mRvContent.addItemDecoration(new DividerItemDecoration(this));
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mDownloadAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mDownloadAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //传递信息
                    DownloadTaskBean bean = mDownloadAdapter.getItem(pos);
                    switch (bean.getStatus()) {
                        //准备暂停
                        case DownloadTaskBean.STATUS_LOADING:
                            mService.setDownloadStatus(bean.getTaskName(), DownloadTaskBean.STATUS_PAUSE);
                            break;
                        //准备暂停
                        case DownloadTaskBean.STATUS_WAIT:
                            mService.setDownloadStatus(bean.getTaskName(), DownloadTaskBean.STATUS_PAUSE);
                            break;
                        //准备启动
                        case DownloadTaskBean.STATUS_PAUSE:
                            mService.setDownloadStatus(bean.getTaskName(), DownloadTaskBean.STATUS_WAIT);
                            break;
                        //准备启动
                        case DownloadTaskBean.STATUS_ERROR:
                            mService.setDownloadStatus(bean.getTaskName(), DownloadTaskBean.STATUS_WAIT);
                            break;
                    }
                }
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();

        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = (DownloadService.IDownloadManager) service;
                //添加数据到队列中
                mDownloadAdapter.addItems(mService.getDownloadTaskList());

                mService.setOnDownloadListener(DownloadActivity.this);

                mRefreshLayout.showFinish();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        //绑定
        bindService(new Intent(this, DownloadService.class), mConn, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    @Override
    public void onDownloadChange(int pos, int status, String msg) {
        DownloadTaskBean bean = mDownloadAdapter.getItem(pos);
        bean.setStatus(status);
        if (DownloadTaskBean.STATUS_LOADING == status) {
            bean.setCurrentChapter(Integer.valueOf(msg));
        }
        mDownloadAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onDownloadResponse(int pos, int status) {
        DownloadTaskBean bean = mDownloadAdapter.getItem(pos);
        bean.setStatus(status);
        mDownloadAdapter.notifyItemChanged(pos);
    }

}
