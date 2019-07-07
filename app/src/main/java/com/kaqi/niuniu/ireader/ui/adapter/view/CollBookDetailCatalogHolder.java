package com.kaqi.niuniu.ireader.ui.adapter.view;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BookChapterBean;
import com.kaqi.niuniu.ireader.ui.base.adapter.ViewHolderImpl;
import com.kaqi.niuniu.ireader.utils.BookManager;
import com.kaqi.niuniu.ireader.utils.OnRvItemClickListener;

/**
 * Created by niqiao on 2019年06月30日15:19:46.
 */

public class CollBookDetailCatalogHolder extends ViewHolderImpl<BookChapterBean> {

    private static final String TAG = "CollBookDetailCatalogHolder";
    private TextView mTvChapter;
    private BookChapterBean bookChapterBean;


    private OnRvItemClickListener listener;
    @Override
    public void initView() {
        mTvChapter = findById(R.id.category_tv_chapter);
    }

    @Override
    public void onBind(BookChapterBean data, int pos) {
        //首先判断是否该章已下载
        bookChapterBean = data;
        Drawable drawable = null;
        //TODO:目录显示设计的有点不好，需要靠成员变量是否为null来判断。
        //如果没有链接地址表示是本地文件
        if (data.getLink() == null) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
        } else {
            if (data.getBookId() != null
                    && BookManager
                    .isChapterCached(data.getBookId(), data.getTitle())) {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
            } else {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_unload);
            }
        }
        mTvChapter.setSelected(false);
        mTvChapter.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        mTvChapter.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        mTvChapter.setText(data.getTitle());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_category;
    }


}
