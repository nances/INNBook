package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.model.bean.BookChapterBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.CollBookDetailCatalogHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;
import com.kaqi.niuniu.ireader.widget.adapter.WholeAdapter;

/**
 * Created by Niqiao
 */

public class CollBookCatalogAdapter extends WholeAdapter<BookChapterBean> {

    @Override
    protected IViewHolder<BookChapterBean> createViewHolder(int viewType) {
        return new CollBookDetailCatalogHolder();
    }

}
