package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.model.bean.BillBookBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.BillBookHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.BaseListAdapter;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-5-3.
 */

public class BillBookAdapter extends BaseListAdapter<BillBookBean> {
    @Override
    protected IViewHolder<BillBookBean> createViewHolder(int viewType) {
        return new BillBookHolder();
    }
}
