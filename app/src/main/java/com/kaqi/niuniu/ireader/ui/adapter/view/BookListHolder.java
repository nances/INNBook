package com.kaqi.niuniu.ireader.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BookListBean;
import com.kaqi.niuniu.ireader.ui.base.adapter.ViewHolderImpl;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.yuyh.easyadapter.glide.GlideRoundTransform;


public class BookListHolder extends ViewHolderImpl<BookListBean> {

    private ImageView mIvPortrait;
    private TextView mTvTitle;
    private TextView mTvAuthor;
    private TextView mTvBrief;
    private TextView mTvMsg;
    private TextView updateTime;


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_coll_book;
    }

    @Override
    public void initView() {
        mIvPortrait = findById(R.id.coll_book_iv_cover);
        mTvTitle = findById(R.id.coll_book_tv_name);
        mTvAuthor = findById(R.id.tvBookListAuthor);
        mTvBrief = findById(R.id.coll_book_tv_chapter);
        updateTime = findById(R.id.coll_book_tv_lately_update);
    }

    @Override
    public void onBind(BookListBean value, int pos) {

        //头像
        Glide.with(getContext())
                .load(Constant.IMG_BASE_URL + value.getCover())
                .placeholder(R.drawable.ic_default_portrait)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .dontAnimate()
                .transform(new GlideRoundTransform(getContext(), 4))
                .into(mIvPortrait);
        updateTime.setText("");
        //书单名
        mTvTitle.setText(value.getTitle());
        //作者
        mTvAuthor.setText(value.getAuthor());
        //简介
        mTvBrief.setText(value.getDesc());
    }
}
