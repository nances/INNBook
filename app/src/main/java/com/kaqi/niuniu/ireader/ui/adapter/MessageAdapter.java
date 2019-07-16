package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.model.bean.MessageBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.MessageHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;
import com.kaqi.niuniu.ireader.widget.adapter.WholeAdapter;

/**
 * Created by Niqiao
 */

public class MessageAdapter extends WholeAdapter<MessageBean> {

    @Override
    protected IViewHolder<MessageBean> createViewHolder(int viewType) {
        return new MessageHolder();
    }

}
