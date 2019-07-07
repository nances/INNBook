package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;

import com.kaqi.niuniu.ireader.model.bean.BookListDetailBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.BookListInfoHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;
import com.kaqi.niuniu.ireader.widget.adapter.WholeAdapter;

/**
 * Created by newbiechen on 17-5-2.
 */

public class BookListDetailAdapter extends WholeAdapter<BookListDetailBean.BooksBean.BookBean> {
    public BookListDetailAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookListDetailBean.BooksBean.BookBean> createViewHolder(int viewType) {
        return new BookListInfoHolder();
    }
}
