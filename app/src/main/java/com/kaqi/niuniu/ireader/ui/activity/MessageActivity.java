package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.gyf.barlibrary.ImmersionBar;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.MessageBean;
import com.kaqi.niuniu.ireader.ui.adapter.MessageAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    @BindView(R.id.book_shelf_rv_content)
    ScrollRefreshRecyclerView mRvContent;

    private MessageAdapter messageAdapter;
    List<MessageBean> messageBeanList = new ArrayList<>();

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MessageActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .fullScreen(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    @Override
    protected void initWidget() {
        commonToolbar.setTitleText("消息");
        commonToolbar.setBackVisibility(true);
        initAdapter();
        setModeData();
    }

    /**
     * 模拟数据
     */
    public void setModeData() {
        for (int i = 0; i < 14; i++) {
            MessageBean messageBean = new MessageBean();
            messageBean.messageInfo = "签到成功，恭喜获得" + (12 + i * 4) + "金币";
            messageBean.messageTime = "2019年07月16日16:33:31";
            messageBean.messageTitle = "今日签到";
            messageBeanList.add(messageBean);
        }
        messageAdapter.addItems(messageBeanList);
        messageAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initClick() {
        super.initClick();

    }

    /**
     * 初始化Adapter
     */
    public void initAdapter() {
        //添加Footer
        messageAdapter = new MessageAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(messageAdapter);
        mRvContent.setEnabled(false);
    }
}
