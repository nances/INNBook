package com.kaqi.reader.ui.adapter.section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.reader.R;
import com.kaqi.reader.bean.RecommendListBean;
import com.kaqi.reader.view.sectioned.StatelessSection;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

public class HomeRecommendedSection extends StatelessSection {
    private Context mContext;
    private String title;
    private String type;
    private int liveCount;
    private static final String TYPE_RECOMMENDED = "recommend";
    private static final String TYPE_LIVE = "live";
    private static final String TYPE_BANGUMI = "bangumi_2";
    private static final String GOTO_BANGUMI = "bangumi_list";
    private static final String TYPE_ACTIVITY = "activity";
    private List<RecommendListBean.DataBean.ResultBean.BodyBean> datas = new ArrayList<>();
    private final Random mRandom;


    public HomeRecommendedSection(Context context, String title, String type, List<RecommendListBean.DataBean.ResultBean.BodyBean> datas) {
        super(R.layout.item_find_partition_title,R.layout.item_recommend_book_list);
        this.mContext = context;
        this.title = title;
        this.type = type;
        this.datas = datas;
        mRandom = new Random();
    }


    @Override
    public int getContentItemsTotal() {
        return datas.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final RecommendListBean.DataBean.ResultBean.BodyBean bodyBean = datas.get(position);

        Glide.with(mContext)
                .load(Uri.parse(bodyBean.getCover()))
                .centerCrop()
                .placeholder(R.drawable.cover_default)
                .dontAnimate()
                .transform(new GlideRoundTransform(mContext,6))
                .into(itemViewHolder.ivRecommendCover);

        itemViewHolder.tvRecommendTitle.setText(bodyBean.getTitle());
        itemViewHolder.card_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.titleTv.setText(title);
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;

        HeaderViewHolder(View itemView) {
            super(itemView);
            titleTv= itemView.findViewById(R.id.title_tv);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRecommendCover;
        TextView ivUnReadDot;
        TextView tvRecommendTitle;
        RelativeLayout card_book;

         ItemViewHolder(View itemView) {
            super(itemView);
             ivRecommendCover= itemView.findViewById(R.id.ivRecommendCover);
             ivUnReadDot= itemView.findViewById(R.id.ivUnReadDot);
             tvRecommendTitle= itemView.findViewById(R.id.tvRecommendTitle);
             card_book= itemView.findViewById(R.id.card_book);
            ButterKnife.bind(this, itemView);
        }
    }
}
