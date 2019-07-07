package com.kaqi.niuniu.ireader.ui.adapter;

import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.ui.adapter.view.CollBookHolder;
import com.kaqi.niuniu.ireader.ui.base.adapter.IViewHolder;
import com.kaqi.niuniu.ireader.widget.adapter.WholeAdapter;

/**
 * Created by Niqiao
 */

public class CollBookAdapter extends WholeAdapter<CollBookBean> {

    @Override
    protected IViewHolder<CollBookBean> createViewHolder(int viewType) {
        return new CollBookHolder();
    }

}
