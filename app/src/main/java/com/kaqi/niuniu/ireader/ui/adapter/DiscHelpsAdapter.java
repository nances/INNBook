package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;

import com.kaqi.niuniu.ireader.model.bean.BookHelpsBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.DiscHelpsHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;
import com.kaqi.niuniu.ireader.widget.adapter.WholeAdapter;

/**
 * Created by newbiechen on 17-4-21.
 */

public class DiscHelpsAdapter extends WholeAdapter<BookHelpsBean>{

    public DiscHelpsAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookHelpsBean> createViewHolder(int viewType) {
        return new DiscHelpsHolder();
    }
}
