/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaqi.reader.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.bean.BookMixAToc;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.kaqi.reader.utils.FileUtils;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nancy
 * @date 2019年04月24日15:41:57
 */
public class BookCatalogListAdapter extends EasyRVAdapter<BookMixAToc.mixToc.Chapters> {

    private int currentChapter;
    private String bookId;

    private boolean isEpub = false;
    List<BookMixAToc.mixToc.Chapters> listChapters = new ArrayList<>();
    private OnRvItemClickListener listener;

    public BookCatalogListAdapter(Context context, List<BookMixAToc.mixToc.Chapters> list, String bookId, int currentChapter, OnRvItemClickListener listener) {
        super(context, list, R.layout.item_book_read_toc_list);
        this.currentChapter = currentChapter;
        this.listChapters = list;
        this.bookId = bookId;
        this.listener = listener;
    }

    public void setCataList(List<BookMixAToc.mixToc.Chapters> list) {
        this.listChapters = list;
        notifyDataSetChanged();
    }

    public void setCurrentChapter(int chapter) {
        currentChapter = chapter;
        notifyDataSetChanged();
    }

    public void setEpub(boolean isEpub) {
        this.isEpub = isEpub;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final BookMixAToc.mixToc.Chapters chapters) {
        final TextView tvTocItem = holder.getView(R.id.tvTocItem);
        tvTocItem.setText(listChapters.get(position).title);
        Drawable drawable;
        if (currentChapter == position + 1) {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_red));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_activated);
        } else if (isEpub || FileUtils.getChapterFile(bookId, position + 1).length() > 10) {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_download);
        } else {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_normal);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvTocItem.setCompoundDrawables(drawable, null, null, null);
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    Log.v("Nancy","positione is value : " + position);
                    listener.onItemClick(holder.getItemView(), position, chapters);
            }
        });
    }

}
