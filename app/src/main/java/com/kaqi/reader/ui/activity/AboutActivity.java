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

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerMainComponent;

public class AboutActivity extends BaseActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("关于我们");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
    }
}
