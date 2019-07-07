package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.model.bean.HotCommentBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.HotCommentHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.BaseListAdapter;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-5-4.
 */

public class HotCommentAdapter extends BaseListAdapter<HotCommentBean> {
    @Override
    protected IViewHolder<HotCommentBean> createViewHolder(int viewType) {
        return new HotCommentHolder();
    }
}
