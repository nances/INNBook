/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaqi.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.component.AppComponent;

import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("反馈建议");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.submit_btn)
    void submit(View view) {
        Toast.makeText(this, "提交.......", Toast.LENGTH_SHORT).show();
    }
}
