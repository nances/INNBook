package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.model.bean.DownloadTaskBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.DownloadHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.BaseListAdapter;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-5-12.
 */

public class DownLoadAdapter extends BaseListAdapter<DownloadTaskBean> {

    @Override
    protected IViewHolder<DownloadTaskBean> createViewHolder(int viewType) {
        return new DownloadHolder();
    }
}
