package com.kaqi.niuniu.ireader.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.packages.SearchBookPackage;
import com.kaqi.niuniu.ireader.ui.base.adapter.ViewHolderImpl;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

public class SearchBookHolder extends ViewHolderImpl<SearchBookPackage.BooksBean> {

    private ImageView mIvCover;
    private TextView mTvName;
    private TextView mTvBrief;
    private TextView mAnchor;

    @Override
    public void initView() {
        mIvCover = findById(R.id.search_book_iv_cover);
        mTvName = findById(R.id.search_book_tv_name);
        mTvBrief = findById(R.id.search_book_tv_brief);
        mAnchor = findById(R.id.tvBookListAuthor);
    }

    @Override
    public void onBind(SearchBookPackage.BooksBean data, int pos) {
        //显示图片
        Glide.with(getContext())
                .load(Constant.IMG_BASE_URL + data.getCover())
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .transform(new GlideRoundTransform(getContext(), 4))
                .into(mIvCover);

        mTvName.setText(data.getTitle());
        mAnchor.setText(data.getAuthor());
        mTvBrief.setText(getContext().getString(R.string.nb_search_book_brief,
                data.getLatelyFollower(), data.getRetentionRatio()));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_search_book;
    }
}
