package com.kaqi.reader.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kaqi.reader.R;

public class CommomMannagerDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView bookTop;
    private TextView bookDel;
    private TextView bookMananger;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String title;

    public CommomMannagerDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomMannagerDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CommomMannagerDialog(Context context, int themeResId,  OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
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
        contentTxt = (TextView) findViewById(R.id.content);
        titleTxt = (TextView) findViewById(R.id.book_title);
        bookTop = (TextView) findViewById(R.id.book_set_top);
        bookDel = (TextView) findViewById(R.id.book_del);
        bookMananger = (TextView) findViewById(R.id.book_all_mannager);


        bookTop.setOnClickListener(this);
        bookDel.setOnClickListener(this);
        bookMananger.setOnClickListener(this);

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_set_top:
                if (listener != null) {
                    listener.onClick(this, 0);
                }
                this.dismiss();
                break;
            case R.id.book_del:
                if (listener != null) {
                    listener.onClick(this, 1);
                }
                this.dismiss();
                break;
            case R.id.book_all_mannager:
                if (listener != null) {
                    listener.onClick(this, 2);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, int confirm);
    }
}