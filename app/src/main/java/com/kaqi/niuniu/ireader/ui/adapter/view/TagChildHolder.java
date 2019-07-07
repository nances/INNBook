package com.kaqi.niuniu.ireader.ui.adapter.view;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.adapter.ViewHolderImpl;

/**
 * Created by newbiechen on 17-5-2.
 */

public class TagChildHolder extends ViewHolderImpl<String> {
    private TextView mTvName;
    private int mSelectTag = -1;

    @Override
    public void initView(){
        mTvName = findById(R.id.tag_child_btn_name);
    }

    @Override
    public void onBind(String value, int pos) {
        mTvName.setText(value);
        //这里要重置点击事件
        if (mSelectTag == pos){
            mTvName.setTextColor(ContextCompat.getColor(getContext(),R.color.yellow_30));
        }
        else {
            mTvName.setTextColor(ContextCompat.getColor(getContext(),R.color.gray));
        }
    }

    public void setTagSelected(int pos){
        mSelectTag = pos;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_tag_child;
    }
}