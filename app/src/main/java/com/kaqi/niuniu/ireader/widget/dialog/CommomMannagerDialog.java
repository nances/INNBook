package com.kaqi.niuniu.ireader.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.StringUtils;
import com.yuyh.easyadapter.glide.GlideRoundTransform;


public class CommomMannagerDialog extends Dialog implements View.OnClickListener {
    ImageView collBookIvCover;
    CardView ivRecommendCover;
    TextView collBookTvName;
    TextView collBookTvLatelyUpdate;
    TextView collBookTvChapter;
    TextView tvBookListAuthor;
    TextView tvBookCat;
    TextView bookSetTop;
    LinearLayout bookSetTopLr;
    TextView bookDownSet;
    LinearLayout bookDownSetLr;
    TextView bookDel;
    LinearLayout bookDelLr;
    TextView bookAllMannager;
    LinearLayout bookAllMannagerLr;
    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String title;
    CollBookBean collBookBean;

    public CommomMannagerDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomMannagerDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CommomMannagerDialog(Context context, int themeResId, CollBookBean collBookBean, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.collBookBean = collBookBean;
        this.listener = listener;
    }

    protected CommomMannagerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomMannagerDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mannager_book);
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    private void initView() {

        collBookIvCover = (ImageView) this.findViewById(R.id.coll_book_iv_cover_manager);

        collBookTvName = (TextView) findViewById(R.id.coll_book_tv_name);
        collBookTvLatelyUpdate = (TextView) findViewById(R.id.coll_book_tv_lately_update);
        collBookTvChapter = (TextView) findViewById(R.id.coll_book_tv_chapter);
        tvBookListAuthor = (TextView) findViewById(R.id.tvBookListAuthor);
        tvBookCat = (TextView) findViewById(R.id.tvBookCat);
        bookSetTop = (TextView) findViewById(R.id.book_set_top);
        bookSetTopLr = (LinearLayout) findViewById(R.id.book_set_top_lr);
        bookDownSet = (TextView) findViewById(R.id.book_down_set);
        bookDownSetLr = (LinearLayout) findViewById(R.id.book_down_set_lr);
        bookDelLr = (LinearLayout) findViewById(R.id.book_del_lr);
        bookAllMannagerLr = (LinearLayout) findViewById(R.id.book_all_mannager_lr);

        bookSetTopLr.setOnClickListener(this);
        bookDownSetLr.setOnClickListener(this);
        bookDelLr.setOnClickListener(this);
        bookAllMannagerLr.setOnClickListener(this);


        if (collBookBean != null) {
            Glide.with(getContext())
                    .load(Constant.IMG_BASE_URL + collBookBean.getCover())
                    .asBitmap()
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_book_loading)
                    .error(R.drawable.ic_load_error)
                    .transform(new GlideRoundTransform(getContext()))
                    .into(collBookIvCover);
            collBookTvName.setText(collBookBean.getTitle());
            tvBookListAuthor.setText(collBookBean.getAuthor());
            bookSetTop.setText(collBookBean.getIsTop() ? "取消置顶 " : "置顶");
            if (!collBookBean.isLocal()) {
                //时间
                collBookTvLatelyUpdate.setText(StringUtils.
                        dateConvert(collBookBean.getUpdated(), Constant.FORMAT_BOOK_DATE) + ":");
                collBookTvLatelyUpdate.setVisibility(View.VISIBLE);
            } else {
                collBookTvLatelyUpdate.setText("阅读进度:");
            }
            collBookTvChapter.setText(collBookBean.getLastChapter());
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.book_set_top_lr:
                if (listener != null) {
                    listener.onClick(this, 0);
                }
                break;
            case R.id.book_down_set_lr:
                if (listener != null) {
                    listener.onClick(this, 1);
                }
                break;
            case R.id.book_del_lr:
                if (listener != null) {
                    listener.onClick(this, 2);
                }
                break;
            case R.id.book_all_mannager_lr:
                if (listener != null) {
                    listener.onClick(this, 3);
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, int confirm);
    }
}