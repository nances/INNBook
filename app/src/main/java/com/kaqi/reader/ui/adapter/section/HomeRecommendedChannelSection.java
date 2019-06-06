package com.kaqi.reader.ui.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.kaqi.reader.R;
import com.kaqi.reader.ui.activity.ClassicFicationActivity;
import com.kaqi.reader.ui.activity.ClassicRankTopActivity;
import com.kaqi.reader.view.banner.BannerEntity;
import com.kaqi.reader.view.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomeRecommendedChannelSection extends StatelessSection {
    private Context mContext;

    private List<BannerEntity> banners = new ArrayList<>();

    public HomeRecommendedChannelSection(Context mContext) {
        super(R.layout.layout_recommend_channel, R.layout.layout_home_recommend_empty);
        this.mContext = mContext;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeRecommendedChannelSection.ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeRecommendedChannelSection.BannerViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeRecommendedChannelSection.BannerViewHolder bannerViewHolder = (HomeRecommendedChannelSection.BannerViewHolder) holder;
        bannerViewHolder.bookChannelLr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassicFicationActivity.startActivity(mContext);
            }
        });
        bannerViewHolder.bookRankingLr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassicRankTopActivity.startActivity(mContext);
            }
        });
        bannerViewHolder.bookCompleteLr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bannerViewHolder.bookUpdateLr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout bookChannelLr;
        private LinearLayout bookRankingLr;
        private LinearLayout bookCompleteLr;
        private LinearLayout bookUpdateLr;

        BannerViewHolder(View itemView) {
            super(itemView);
            bookChannelLr = itemView.findViewById(R.id.book_channel_lr);
            bookRankingLr = itemView.findViewById(R.id.book_ranking_lr);
            bookCompleteLr = itemView.findViewById(R.id.book_complete_lr);
            bookUpdateLr = itemView.findViewById(R.id.book_update_lr);

            ButterKnife.bind(this, itemView);
        }
    }
}
