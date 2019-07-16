package com.kaqi.niuniu.ireader.ui.adapter.view;

import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.MessageBean;
import com.kaqi.niuniu.ireader.ui.base.adapter.ViewHolderImpl;

/**
 * Created by Message on 18-5-8.
 * CollectionBookView
 */

public class MessageHolder extends ViewHolderImpl<MessageBean> {

    private static final String TAG = "MessageHolder";
    private TextView messageTitle;
    private TextView messageTime;
    private TextView messageInfo;

    @Override
    public void initView() {
        messageTitle = findById(R.id.message_title);
        messageTime = findById(R.id.message_time);
        messageInfo = findById(R.id.message_info);
    }

    @Override
    public void onBind(MessageBean value, int pos) {
        messageInfo.setText(value.getMessageInfo());
        messageTime.setText(value.getMessageTime());
        messageTitle.setText(value.getMessageTitle());


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_message;
    }

}
