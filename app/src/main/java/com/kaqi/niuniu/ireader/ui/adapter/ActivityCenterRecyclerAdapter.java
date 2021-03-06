package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.RecommendListBean;
import com.kaqi.niuniu.ireader.ui.adapter.helper.AbsRecyclerViewAdapter;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.List;

public class ActivityCenterRecyclerAdapter extends AbsRecyclerViewAdapter {
    private List<RecommendListBean.DataBean.ResultBean.BodyBean> activitys;

    private Context mContext;

    public ActivityCenterRecyclerAdapter(RecyclerView recyclerView, List<RecommendListBean.DataBean.ResultBean.BodyBean> activitys, Context context) {
        super(recyclerView);
        this.activitys = activitys;
        this.mContext = context;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.recommend_week_book_item, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if (activitys.get(position).getCover() != null && position < activitys.size() - 1) {
                itemViewHolder.tvRecommendTitle.setText(activitys.get(position + 1).getTitle());
                Glide.with(getContext())
                        .load(Uri.parse(activitys.get(position + 1).getCover()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new GlideRoundTransform(mContext, 4))
                        .dontAnimate()
                        .into(itemViewHolder.bookImg);
            }

        }
    }


    @Override
    public int getItemCount() {
        return activitys.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView bookImg;
        TextView tvRecommendTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            bookImg = $(R.id.ivRecommendCover);
            tvRecommendTitle = $(R.id.tvRecommendTitle);
        }
    }
}