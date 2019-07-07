package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.CategoryList;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.OnRvItemClickListener;
import com.kaqi.niuniu.ireader.utils.StringUtils;
import com.yuyh.easyadapter.glide.GlideRoundTransform;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author Nancy.
 * @date 2019年04月29日11:23:16
 */
public class TopCategoryFemaleListAdapter extends EasyRVAdapter<CategoryList.FemaleBean> {
    private OnRvItemClickListener itemClickListener;

    public TopCategoryFemaleListAdapter(Context context, List<CategoryList.FemaleBean> list) {
        super(context, list, R.layout.item_top_category_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final CategoryList.FemaleBean item) {
        holder.setText(R.id.tvName, item.getName())
                .setText(R.id.tvBookCount, StringUtils.formatBookSum(item.getBookCount()));
        if (item.getBookCover() != null && item.getBookCover().size() > 0) {
            Glide.with(mContext)
                    .load(Constant.IMG_BASE_URL + item.getBookCover().get(0))
                    .placeholder(R.drawable.ic_book_loading)
                    .error(R.drawable.ic_load_error)
                    .fitCenter()
                    .dontAnimate()
                    .transform(new GlideRoundTransform(mContext,6))
                    .into((ImageView) holder.getView(R.id.ivRecommendCover));

        }
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SubCategoryListActivity.startActivity(mContext, item.getName(), Constant.Gender.FEMALE);
            }
        });
    }
}
