package com.kaqi.niuniu.ireader.ui.adapter.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.bean.DownloadTaskBean;
import com.kaqi.niuniu.ireader.ui.activity.ReadActivity;
import com.kaqi.niuniu.ireader.ui.base.adapter.ViewHolderImpl;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.FileUtils;
import com.kaqi.niuniu.ireader.utils.StringUtils;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

/**
 * Created by newbiechen on 17-5-12.
 */

public class DownloadHolder extends ViewHolderImpl<DownloadTaskBean> {
    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvTip;
    private ProgressBar mPbShow;
    private RelativeLayout mRlToggle;
    private ImageView mIvStatus;
    private TextView mTvStatus;
    private RelativeLayout item_rl;
    private ImageView cover_img;
    private TextView download_info;
    DownloadTaskBean downloadTaskBean;

    @Override
    public void initView() {
        mTvTitle = findById(R.id.download_tv_title);
        mTvMsg = findById(R.id.download_tv_msg);
        mTvTip = findById(R.id.download_tv_tip);
        mPbShow = findById(R.id.download_pb_show);
        mRlToggle = findById(R.id.download_rl_toggle);
        mIvStatus = findById(R.id.download_iv_status);
        mTvStatus = findById(R.id.download_tv_status);
        item_rl = findById(R.id.item_rl);
        cover_img = findById(R.id.coll_book_iv_cover);
        download_info = findById(R.id.download_info);
    }

    @Override
    public void onBind(DownloadTaskBean value, int pos) {
        downloadTaskBean = value;
        if (!mTvTitle.getText().equals(value.getTaskName())) {
            mTvTitle.setText(value.getTaskName());
        }
        if (!download_info.getText().equals(value.getBook_info())) {
            download_info.setText(value.getBook_info());
        }
        Glide.with(getContext())
                .load(Constant.IMG_BASE_URL + value.getBook_cover())
                .placeholder(R.drawable.ic_book_loading)
                .transform(new GlideRoundTransform(getContext(),4))
                .into(cover_img);
        switch (value.getStatus()) {
            case DownloadTaskBean.STATUS_LOADING:
                changeBtnStyle(R.string.nb_download_pause,
                        R.color.nb_download_pause, R.drawable.ic_download_pause);

                //进度状态
                setProgressMax(value);
                mPbShow.setProgress(value.getCurrentChapter());

                setTip(R.string.nb_download_loading);

                mTvMsg.setText(StringUtils.getString(R.string.nb_download_progress,
                        value.getCurrentChapter(), value.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_PAUSE:
                //按钮状态
                changeBtnStyle(R.string.nb_download_start,
                        R.color.nb_download_loading, R.drawable.ic_download_loading);

                //进度状态
                setProgressMax(value);
                setTip(R.string.nb_download_pausing);

                mPbShow.setProgress(value.getCurrentChapter());
                mTvMsg.setText(StringUtils.getString(R.string.nb_download_progress,
                        value.getCurrentChapter(), value.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_WAIT:
                //按钮状态
                changeBtnStyle(R.string.nb_download_wait,
                        R.color.nb_download_wait, R.drawable.ic_download_wait);

                //进度状态
                setProgressMax(value);
                setTip(R.string.nb_download_waiting);

                mPbShow.setProgress(value.getCurrentChapter());
                mTvMsg.setText(StringUtils.getString(R.string.nb_download_progress,
                        value.getCurrentChapter(), value.getBookChapters().size()));
                break;
            case DownloadTaskBean.STATUS_ERROR:
                //按钮状态
                changeBtnStyle(R.string.nb_download_error,
                        R.color.nb_download_error, R.drawable.ic_download_error);
                setTip(R.string.nb_download_source_error);
                mPbShow.setVisibility(View.INVISIBLE);
                mTvMsg.setVisibility(View.GONE);
                break;
            case DownloadTaskBean.STATUS_FINISH:
                //按钮状态
                changeBtnStyle(R.string.nb_download_finish,
                        R.color.nb_download_finish, R.drawable.ic_download_complete);
                setTip(R.string.nb_download_complete);
                mPbShow.setVisibility(View.INVISIBLE);

                //设置文件大小
                mTvMsg.setText(FileUtils.getFileSize(value.getSize()));
                break;

        }
        item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadActivity.startActivity(getContext(),
                        createCollBookBean(), true);
            }
        });
    }

    CollBookBean bean;

    public CollBookBean createCollBookBean() {
        bean = new CollBookBean();
        bean.set_id(downloadTaskBean.getBookId());
        bean.setTitle(downloadTaskBean.getTaskName());
        bean.setAuthor(downloadTaskBean.getBook_anchor());
        bean.setShortIntro(downloadTaskBean.getBook_info());
        bean.setCover(downloadTaskBean.getBook_cover());
        bean.setUpdated(downloadTaskBean.getLastChapter() + "");
        bean.setChaptersCount(downloadTaskBean.getBookChapterList().size());
        bean.setLastChapter(downloadTaskBean.getLastChapter() + "");
        return bean;
    }


    private void changeBtnStyle(int strRes, int colorRes, int drawableRes) {
        //按钮状态
        if (!mTvStatus.getText().equals(
                StringUtils.getString(strRes))) {
            mTvStatus.setText(StringUtils.getString(strRes));
            mTvStatus.setTextColor(getContext().getResources().getColor(colorRes));
            mIvStatus.setImageResource(drawableRes);
        }
    }

    private void setProgressMax(DownloadTaskBean value) {
        if (mPbShow.getMax() != value.getBookChapters().size()) {
            mPbShow.setVisibility(View.VISIBLE);
            mPbShow.setMax(value.getBookChapters().size());
        }
    }

    //提示
    private void setTip(int strRes) {
        if (!mTvTip.getText().equals(StringUtils.getString(strRes))) {
            mTvTip.setText(StringUtils.getString(strRes));
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_download;
    }
}
