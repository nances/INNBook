package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.model.bean.packages.SearchBookPackage;
import com.kaqi.niuniu.ireader.ui.adapter.view.SearchBookHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.BaseListAdapter;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-6-2.
 */

public class SearchBookAdapter extends BaseListAdapter<SearchBookPackage.BooksBean> {
    @Override
    protected IViewHolder<SearchBookPackage.BooksBean> createViewHolder(int viewType) {
        return new SearchBookHolder();
    }
}
