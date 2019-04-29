package com.kaqi.reader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaqi.reader.R;
import com.kaqi.reader.base.Constant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_BANNER = 0;//Banner
    public final static int TYPE_BOOK_GRID = 1;//书籍
    public final static int TYPE_BOOK_LINEAR = 2;
    public final static int TYPE_PARTITION_TITLE = 3;//标题
    public final static int TYPE_LABEL = 4;//分类

    private List<Label> labelList = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();
    private List<Book> linearBookList = new ArrayList<>();
    private Context mContext;

    public FindItemAdapter() {
        labelList.add(new Label(R.drawable.uv, "分类"));
        labelList.add(new Label(R.drawable.uu, "排行榜"));
        labelList.add(new Label(R.drawable.uy, "完结精品"));
        labelList.add(new Label(R.drawable.uz, "今日更新"));


        Gson gson = new Gson();
        Type bookListType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        bookList = gson.fromJson(booksJson, bookListType);
        linearBookList = gson.fromJson(booksJson, bookListType);
        linearBookList.addAll(linearBookList);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView;
        if (viewType == TYPE_BANNER) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_find_banner, parent, false);
            return new BannerViewHolder(itemView);
        } else if (viewType == TYPE_LABEL) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_find_label, parent, false);
            return new LabelViewHolder(itemView);
        } else if (viewType == TYPE_PARTITION_TITLE) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_find_partition_title, parent, false);
            return new PartitionTitleViewHolder(itemView);
        } else if (viewType == TYPE_BOOK_GRID) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_find_book_grid, parent, false);
            return new GridBookViewHolder(itemView);
        } else if (viewType == TYPE_BOOK_LINEAR) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_find_book_linear, parent, false);
            return new LinearBookViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {

        } else if (holder instanceof LabelViewHolder) {
            LabelViewHolder labelViewHolder = (LabelViewHolder) holder;
            Label label = labelList.get(position - 1);
            labelViewHolder.imageIv.setImageResource(label.icon);
            labelViewHolder.nameTv.setText(label.name);
        } else if (holder instanceof PartitionTitleViewHolder) {
            PartitionTitleViewHolder partitionTitleViewHolder = (PartitionTitleViewHolder) holder;
            partitionTitleViewHolder.titleTv.setText("本周看点");
            partitionTitleViewHolder.subTitleTv.setText("热血玄幻，不容错过");
        } else if (holder instanceof GridBookViewHolder) {
            GridBookViewHolder bookViewHolder = (GridBookViewHolder) holder;
            int p = position - (labelList.size() + 1 + 1);
            Book book = bookList.get(p);
            Glide.with(mContext)
                    .load(Constant.IMG_BASE_URL + book.cover)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bookViewHolder.imageIv);
            bookViewHolder.nameTv.setText(book.title);
        } else if (holder instanceof LinearBookViewHolder) {
            LinearBookViewHolder bookViewHolder = (LinearBookViewHolder) holder;
            int p = position - (labelList.size() + 1 + 1 + 1 + bookList.size());
            Book book = linearBookList.get(p);
            bookViewHolder.titleTv.setText(book.title);
            Glide.with(mContext)
                    .load(Constant.IMG_BASE_URL + book.cover)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bookViewHolder.imageIv);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LabelViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.nameTv)
        TextView nameTv;
        @Bind(R.id.imageIv)
        ImageView imageIv;

        public LabelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GridBookViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.nameTv)
        TextView nameTv;
        @Bind(R.id.imageIv)
        ImageView imageIv;

        public GridBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LinearBookViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.minorCateTv)
        TextView minorCateTv;
        @Bind(R.id.titleTv)
        TextView titleTv;
        @Bind(R.id.introTv)
        TextView introTv;
        @Bind(R.id.imageIv)
        ImageView imageIv;
        @Bind(R.id.retentionRatioTv)
        TextView retentionRatioTv;

        public LinearBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PartitionTitleViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.titleTv)
        TextView titleTv;
        @Bind(R.id.subTitleTv)
        TextView subTitleTv;
        @Bind(R.id.rightTv)
        TextView rightTv;

        public PartitionTitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Log.v("tag", String.valueOf(position));
        if (position == 0) {
            Log.v("tag", "TYPE_BANNER");
            return TYPE_BANNER;
        } else if (position > 0 && position <= labelList.size()) {
            Log.v("tag", "TYPE_LABEL");
            return TYPE_LABEL;
        } else if (position > labelList.size() && position <= labelList.size() + 1) {
            Log.v("tag", "TYPE_PARTITION_TITLE");
            return TYPE_PARTITION_TITLE;
        } else if (position > labelList.size() + 1 && position <= labelList.size() + 1 + bookList.size()) {
            return TYPE_BOOK_GRID;
        } else if (position == labelList.size() + 1 + bookList.size() + 1) {
            return TYPE_PARTITION_TITLE;
        } else {
            return TYPE_BOOK_LINEAR;
        }
//        return super.getItemViewType(position);
    }


    public int getSpanSize(int position) {
        if (position == 0)
            return 12;
        else if (position > 0 && position <= labelList.size()) {
            return 3;
        } else if (position > labelList.size() && position <= labelList.size() + 1) {
            return 12;
        } else if (position > labelList.size() + 1 && position <= labelList.size() + 1 + bookList.size()) {
            return 3;
        } else if (position == labelList.size() + 1 + bookList.size() + 1) {
            return 12;
        }
        return 12;
    }

    @Override
    public int getItemCount() {
        return labelList.size() + 1 + 1 + bookList.size() + 1 + linearBookList.size();
    }

    class Label {
        private int icon;
        private String name;

        public Label(int icon, String name) {
            this.icon = icon;
            this.name = name;
        }
    }

    class Book {
        private String _id;
        private String site;
        private String author;
        private String majorCate;
        private String minorCate;
        private String title;
        private String cover;
        private String shortIntro;
        private String allowMonthly;
        private String banned;
        private String latelyFollower;
        private String retentionRatio;
    }


    private String booksJson = "[{\n" +
            "\"_id\":\"5a4b46106c81b81b70301c16\",\n" +
            "\"site\":\"zhuishuvip\",\n" +
            "\"author\":\"伪戒\",\n" +
            "\"majorCate\":\"都市\",\n" +
            "\"minorCate\":\"都市生活\",\n" +
            "\"title\":\"正道潜龙\",\n" +
            "\"cover\":\"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2201994%2F2201994_a534e56708034bc58ee93a9b753abdad.jpg%2F\",\n" +
            "\"shortIntro\":\"一件枪案，\\r\\n　　一名卧底横死街头。\\r\\n　　……\\r\\n　　一位办案人，\\r\\n　　曾在庄严的国徽下宣誓。\\r\\n　　……\\r\\n　　命运的路口，一位孪生兄弟粉墨登场，引出一段孤独且热血的故事……\\r\\n　　梦回1998！\\r\\n　　兄弟还在，青春还在，我们从这里再次扬帆起航，铸就下一个辉煌！\",\n" +
            "\"allowMonthly\":true,\n" +
            "\"banned\":0,\n" +
            "\"latelyFollower\":20617,\n" +
            "\"retentionRatio\":\"72.96\"\n" +
            "},\n" +
            "{\n" +
            "\"_id\":\"563059d9705d64a75aad8200\",\n" +
            "\"cover\":\"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F859483%2F859483_497723a93e824af4ae3310e41d162af0.jpg%2F\",\n" +
            "\"site\":\"zhuishuvip\",\n" +
            "\"author\":\"心动可乐\",\n" +
            "\"majorCate\":\"游戏\",\n" +
            "\"minorCate\":\"电子竞技\",\n" +
            "\"title\":\"英雄联盟之绝世无双\",\n" +
            "\"shortIntro\":\"什么？你说英雄联盟是5V5的游戏？这难道不一直是1打9的游戏吗？夏新露出了一脸凝重表情……难道大家玩的不是同一款游戏……\",\n" +
            "\"allowMonthly\":false,\n" +
            "\"banned\":0,\n" +
            "\"latelyFollower\":6930,\n" +
            "\"retentionRatio\":\"52.43\"\n" +
            "},\n" +
            "{\n" +
            "\"_id\":\"58db11eef02f7d7e2ab97775\",\n" +
            "\"site\":\"zhuishuvip\",\n" +
            "\"author\":\"七十二翼天使\",\n" +
            "\"majorCate\":\"都市\",\n" +
            "\"minorCate\":\"青春校园\",\n" +
            "\"title\":\"校园狂少\",\n" +
            "\"cover\":\"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1469620%2F1469620_0d1f02d7bf1447319ce16597f26daa20.jpg%2F\",\n" +
            "\"shortIntro\":\"热血！澎湃！刺激！一切尽在狂少中！\\r\\n绝不能让美女们在黑夜里寂寞地哭泣！\\r\\n\",\n" +
            "\"allowMonthly\":true,\n" +
            "\"banned\":0,\n" +
            "\"latelyFollower\":11187,\n" +
            "\"retentionRatio\":\"35.85\"\n" +
            "},\n" +
            "{\n" +
            "\"_id\":\"561f833655620041276f5d49\",\n" +
            "\"cover\":\"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F601888%2F601888_b75ef20d3f8e41358c4838d919a74a1a.jpg%2F\",\n" +
            "\"site\":\"zhuishuvip\",\n" +
            "\"majorCate\":\"玄幻\",\n" +
            "\"author\":\"八异\",\n" +
            "\"minorCate\":\"东方玄幻\",\n" +
            "\"title\":\"神魂至尊\",\n" +
            "\"shortIntro\":\"\\r\\n一块浩瀚无垠的广阔大陆；一个波澜壮阔的璀璨时代；一道阴差阳错的重生灵魂；一名备受歧视的懦弱少年！\\\\r\\r\\n一场巧合的意外，当那道灵魂重生在那名懦弱少年体内时，开启了一段震古烁今的强者传说！\\r\\n从二十一世纪穿越而来的卓文，能否在这奇诡波澜的大时代一步步走向巅峰……\",\n" +
            "\"allowMonthly\":false,\n" +
            "\"banned\":0,\n" +
            "\"latelyFollower\":11397,\n" +
            "\"retentionRatio\":\"57.92\"\n" +
            "}]";
}
