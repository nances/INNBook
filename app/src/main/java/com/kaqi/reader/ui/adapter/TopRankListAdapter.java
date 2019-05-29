package com.kaqi.reader.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.kaqi.reader.R;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.bean.BooksByCats;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.kaqi.reader.ui.activity.BookDetailActivity;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年04月29日11:23:16
 */
public class TopRankListAdapter extends EasyRVAdapter<BooksByCats.BooksBean> {
    private OnRvItemClickListener itemClickListener;

    public TopRankListAdapter(Context context, List<BooksByCats.BooksBean> list) {
        super(context, list, R.layout.item_sub_category_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final BooksByCats.BooksBean item) {
        holder.setText(R.id.tvSubCateTitle, item.title)
                .setText(R.id.tvSubCateAuthor, (item.author == null ? "未知" : item.author) + " | " + (item.majorCate == null ? "未知" : item.majorCate))
                .setText(R.id.tvSubCateShort, item.shortIntro)
                .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.category_book_msg),
                        item.latelyFollower,
                        TextUtils.isEmpty(item.retentionRatio) ? "0" : item.retentionRatio));
        holder.setRoundImageUrl(R.id.ivSubCateCover, Constant.IMG_BASE_URL + item.cover,
                R.drawable.cover_default);
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.startActivity(mContext, item._id);
            }
        });
    }

}
