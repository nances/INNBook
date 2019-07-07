package com.kaqi.niuniu.ireader.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kaqi.niuniu.ireader.ui.adapter.view.CategoryHolder;
import com.kaqi.niuniu.ireader.ui.base.EasyAdapter;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;
import com.kaqi.niuniu.ireader.widget.page.TxtChapter;

/**
 * Created by niqiao on 2019-06-30 14:52:09.
 */

public class CategoryAdapter extends EasyAdapter<TxtChapter> {
    private int currentSelected = 0;
    @Override
    protected IViewHolder<TxtChapter> onCreateViewHolder(int viewType) {
        return new CategoryHolder();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        CategoryHolder holder = (CategoryHolder) view.getTag();

        if (position == currentSelected){
            holder.setSelectedChapter();
        }

        return view;
    }

    public void setChapter(int pos){
        currentSelected = pos;
        notifyDataSetChanged();
    }
}
