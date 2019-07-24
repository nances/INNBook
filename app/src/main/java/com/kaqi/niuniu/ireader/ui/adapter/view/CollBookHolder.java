package com.kaqi.niuniu.ireader.ui.adapter.view;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.local.BookRepository;
import com.kaqi.niuniu.ireader.ui.base.adapter.ViewHolderImpl;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.StringUtils;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import static android.view.View.GONE;

/**
 * Created by newbiechen on 17-5-8.
 * CollectionBookView
 */

public class CollBookHolder extends ViewHolderImpl<CollBookBean> {

    private static final String TAG = "CollBookView";
    private ImageView mIvCover;
    private TextView mTvName;
    private TextView mTvChapter;
    private TextView mTvTime;
    private CheckBox mCbSelected;
    private TextView mIvRedDot;
    private ImageView mIvTop;
    private TextView tvBookListAuthor;

    @Override
    public void initView() {
        mIvCover = findById(R.id.coll_book_iv_cover);
        mTvName = findById(R.id.coll_book_tv_name);
        mTvChapter = findById(R.id.coll_book_tv_chapter);
        mTvTime = findById(R.id.coll_book_tv_lately_update);
        mCbSelected = findById(R.id.coll_book_cb_selected);
        mIvRedDot = findById(R.id.coll_book_iv_red_rot);
        mIvTop = findById(R.id.coll_book_iv_top);
        tvBookListAuthor = findById(R.id.tvBookListAuthor);
    }

    @Override
    public void onBind(CollBookBean value, int pos) {
        if (value.isLocal()) {
            //本地文件的图片
            Glide.with(getContext())
                    .load(R.drawable.ic_local_file)
                    .fitCenter()
                    .into(mIvCover);
        } else {
            //书的图片
            Glide.with(getContext())
                    .load(Constant.IMG_BASE_URL + value.getCover())
                    .asBitmap()
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_book_loading)
                    .error(R.drawable.ic_load_error)
                    .transform(new GlideRoundTransform(getContext(), 4))
                    .into(mIvCover);
        }
        mCbSelected.setVisibility(value.getIsCheckShow() ? View.VISIBLE : GONE);
        //书名
        mTvName.setText(value.getTitle());
        tvBookListAuthor.setText(value.getAuthor());
        if (!value.isLocal()) {
            //时间
            mTvTime.setText(StringUtils.
                    dateConvert(value.getUpdated(), Constant.FORMAT_BOOK_DATE) + ":");
            mTvTime.setVisibility(View.VISIBLE);
        } else {
            mTvTime.setText("阅读进度:");
        }
        mIvTop.setVisibility(BookRepository.getInstance().isTop(value.get_id()) ? View.VISIBLE : GONE);
        //章节
        mTvChapter.setText(value.getLastChapter());
        //我的想法是，在Collection中加一个字段，当追更的时候设置为true。当点击的时候设置为false。
        //当更新的时候，最新数据跟旧数据进行比较，如果更新的话，设置为true。
        if (value.isUpdate()) {
            mIvRedDot.setVisibility(View.VISIBLE);
        } else {
            mIvRedDot.setVisibility(GONE);
        }
        mCbSelected.setChecked(value.getIsCheckBox());
        mCbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                value.setIsCheckBox(isChecked);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_coll_book;
    }

}
