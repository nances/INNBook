package com.kaqi.reader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaqi.reader.R;
import com.kaqi.reader.bean.RecommendListBean;
import com.kaqi.reader.ui.adapter.helper.AbsRecyclerViewAdapter;

import java.util.List;
public class ActivityCenterRecyclerAdapter extends AbsRecyclerViewAdapter {
    private List<RecommendListBean.DataBean.ResultBean.BodyBean> activitys;

    public ActivityCenterRecyclerAdapter(RecyclerView recyclerView, List<RecommendListBean.DataBean.ResultBean.BodyBean> activitys) {
        super(recyclerView);
        this.activitys = activitys;
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
            Glide.with(getContext())
                    .load(activitys.get(position).getCover())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(itemViewHolder.bookImg);

        }
    }


    @Override
    public int getItemCount() {
        return activitys.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView bookImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            bookImg = $(R.id.ivRecommendCover);
        }
    }
}