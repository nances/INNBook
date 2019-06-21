package com.kaqi.reader.ui.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kaqi.reader.R;
import com.kaqi.reader.ui.adapter.ViewPagerHolder;
import com.kaqi.reader.view.banner.BannerEntity;
import com.kaqi.reader.view.banner.MZBannerView;
import com.kaqi.reader.view.banner.holder.MZHolderCreator;
import com.kaqi.reader.view.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;

public class HomeRecommendBannerSection extends StatelessSection {
    private List<BannerEntity> banners = new ArrayList<>();
    ViewPagerHolder viewPagerHolder;

    public HomeRecommendBannerSection(List<BannerEntity> banners) {
        super(R.layout.layout_banner, R.layout.layout_home_recommend_empty);
        this.banners = banners;
        viewPagerHolder = new ViewPagerHolder();
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new BannerViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        viewPagerHolder.setData(banners);
        bannerViewHolder.mBannerView.setPages(banners, new MZHolderCreator<ViewPagerHolder>() {
            @Override
            public ViewPagerHolder createViewHolder() {
                return viewPagerHolder;
            }
        });
        bannerViewHolder.mBannerView.start();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        MZBannerView mBannerView;

        BannerViewHolder(View itemView) {
            super(itemView);
            mBannerView = (MZBannerView) itemView.findViewById(R.id.recommended_banner);
        }
    }
}
