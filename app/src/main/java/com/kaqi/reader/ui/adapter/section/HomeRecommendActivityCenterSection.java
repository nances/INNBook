package com.kaqi.reader.ui.adapter.section;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaqi.reader.R;
import com.kaqi.reader.bean.RecommendListBean;
import com.kaqi.reader.ui.adapter.ActivityCenterRecyclerAdapter;
import com.kaqi.reader.view.sectioned.StatelessSection;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mob.MobSDK.getContext;

public class HomeRecommendActivityCenterSection extends StatelessSection {

    private Context mContext;
    private List<RecommendListBean.DataBean.ResultBean.BodyBean> activitys;

    public HomeRecommendActivityCenterSection(Context context, List<RecommendListBean.DataBean.ResultBean.BodyBean> activitys) {
        super(R.layout.layout_home_recommend_activitycenter, R.layout.layout_home_recommend_empty);
        this.mContext = context;
        this.activitys = activitys;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new ActivityCenterViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        ActivityCenterViewHolder centerViewHolder = (ActivityCenterViewHolder) holder;

        Glide.with(getContext())
                .load(Uri.parse(activitys.get(0).getCover()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(mContext, 6))
                .dontAnimate()
                .into(centerViewHolder.ivBookCover);

        centerViewHolder.mRecyclerView.setHasFixedSize(false);
        centerViewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        centerViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));
        centerViewHolder.mRecyclerView.setAdapter(new ActivityCenterRecyclerAdapter(
                centerViewHolder.mRecyclerView, activitys, mContext));
    }


    static class ActivityCenterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.activity_center_type_tv)
        TextView activityCenterTypeTv;
        @Bind(R.id.ivBookCover)
        ImageView ivBookCover;
        @Bind(R.id.ivBookCoverCardView)
        CardView ivBookCoverCardView;
        @Bind(R.id.tvBookListTitle)
        TextView tvBookListTitle;
        @Bind(R.id.tvBookListAuthor)
        TextView tvBookListAuthor;
        @Bind(R.id.tvCatgory)
        TextView tvCatgory;
        @Bind(R.id.tvWordCount)
        TextView tvWordCount;
        @Bind(R.id.tvSerializeWordCount)
        TextView tvSerializeWordCount;

        @Bind(R.id.recycle)
        RecyclerView mRecyclerView;

        ActivityCenterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
