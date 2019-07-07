package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        commonToolbar.setTitleText("反馈建议");
        commonToolbar.setBackVisibility(true);
    }


    @OnClick(R.id.submit_btn)
    void submit(View view) {
        Toast.makeText(this, "提交.......", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
