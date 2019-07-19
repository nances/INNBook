package com.kaqi.niuniu.ireader.ui.adapter.section;

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
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.RecommendListBean;
import com.kaqi.niuniu.ireader.ui.adapter.ActivityCenterRecyclerAdapter;
import com.kaqi.niuniu.ireader.view.sectioned.StatelessSection;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.List;

import butterknife.BindView;
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
                .transform(new GlideRoundTransform(mContext, 4))
                .dontAnimate()
                .into(centerViewHolder.ivBookCover);
        centerViewHolder.tv_anchor.setText(activitys.get(0).getTitle());
        centerViewHolder.mRecyclerView.setHasFixedSize(false);
        centerViewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        centerViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));
        centerViewHolder.mRecyclerView.setAdapter(new ActivityCenterRecyclerAdapter(
                centerViewHolder.mRecyclerView, activitys, mContext));
    }


    static class ActivityCenterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.activity_center_type_tv)
        TextView activityCenterTypeTv;
        @BindView(R.id.ivBookCover)
        ImageView ivBookCover;
        @BindView(R.id.ivBookCoverCardView)
        CardView ivBookCoverCardView;
        @BindView(R.id.tvBookListTitle)
        TextView tvBookListTitle;
        @BindView(R.id.tvBookListInfo)
        TextView tvBookListInfo;
        @BindView(R.id.AnchorTv)
        TextView tv_anchor;
//        @BindView(R.id.tvWordCount)
//        TextView tvWordCount;
        @BindView(R.id.tvSerializeWordCount)
        TextView tvSerializeWordCount;

        @BindView(R.id.recycle)
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
