package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;
import android.view.View;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.CategoryList;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年04月29日11:23:16
 */
public class TopCategoryPressListAdapter extends EasyRVAdapter<CategoryList.PressBean> {
    private OnRvItemClickListener itemClickListener;

    public TopCategoryPressListAdapter(Context context, List<CategoryList.PressBean> list) {
        super(context, list, R.layout.item_top_category_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final CategoryList.PressBean item) {
        holder.setText(R.id.tvName, item.getName())
                .setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .category_book_count), item.getBookCount()));
        if(item.getBookCover()!=null && item.getBookCover().size()>0){
            holder.setRoundImageUrl(R.id.ivRecommendCover, Constant.IMG_BASE_URL + item.getBookCover().get(0).toString(),
                    R.drawable.ic_book_loading);

        }
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SubCategoryListActivity.startActivity(mContext, item.getName(), Constant.Gender.FEMALE);
            }
        });
    }

}
