package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;
import android.view.View;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BooksByCats;
import com.kaqi.niuniu.ireader.ui.activity.BookDetailActivity;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.OnRvItemClickListener;
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
        super(context, list, R.layout.item_recommend_book_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final BooksByCats.BooksBean item) {
        holder.setText(R.id.tvRecommendTitle, item.title)
                .setText(R.id.tvBookListAuthor, (item.author == null ? "未知" : item.author))
                .setText(R.id.tvRecommendShort, item.shortIntro
                );
        holder.setRoundImageUrl(R.id.ivRecommendCover, Constant.IMG_BASE_URL + item.cover,
                R.drawable.ic_book_loading);
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.startActivity(mContext, item._id);
            }
        });
    }

}
