package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.ui.adapter.view.KeyWordHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.BaseListAdapter;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-6-2.
 */

public class KeyWordAdapter extends BaseListAdapter<String> {
    @Override
    protected IViewHolder<String> createViewHolder(int viewType) {
        return new KeyWordHolder();
    }
}
