package com.kaqi.niuniu.ireader.ui.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.activity.SearchActivity;
import com.kaqi.niuniu.ireader.view.TagColor;
import com.kaqi.niuniu.ireader.view.TagGroup;
import com.kaqi.niuniu.ireader.view.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecommendedHotSection extends StatelessSection {
    private Context mContext;

    private List<String> activitys = new ArrayList<>();

    public HomeRecommendedHotSection(Context mContext) {
        super(R.layout.layout_home_recommend_hot_activitycenter, R.layout.layout_home_recommend_empty);
        this.mContext = mContext;
        setMode();
    }

    public void setMode() {
        activitys.add("银河补习班");
        activitys.add("最强修仙高手");
        activitys.add("那小子真帅");
        activitys.add("每日");
        activitys.add("上门女婿");
        activitys.add("太古剑尊");
        activitys.add("最佳女婿");
        activitys.add("狂嘘");
        activitys.add("时间都知道");
        activitys.add("雷神");
        activitys.add("亲爱的，热爱的");
        activitys.add("太古剑尊");

    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeRecommendedHotSection.ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeRecommendedHotSection.HotViewHolder(view);
    }


    private int mTagStart = 0;

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeRecommendedHotSection.HotViewHolder hotViewHolder = (HomeRecommendedHotSection.HotViewHolder) holder;
        int tagSize = activitys.size();
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < activitys.size(); mTagStart++, j++) {
            tags[j] = activitys.get(mTagStart % activitys.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        hotViewHolder.mTagGroup.setTags(colors, tags);

        //Tag的点击事件
        hotViewHolder.mTagGroup.setOnTagClickListener(
                tag -> {
                    SearchActivity.startActivity(mContext, tag);
                }
        );
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class HotViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tag_group)
        TagGroup mTagGroup;

        HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
