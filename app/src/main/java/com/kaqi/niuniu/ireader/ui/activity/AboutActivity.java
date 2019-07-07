package com.kaqi.niuniu.ireader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        commonToolbar.setTitleText("关于我们");
        commonToolbar.setBackVisibility(true);

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
