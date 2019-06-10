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
import com.kaqi.reader.utils.NormalTitleBar;

import butterknife.Bind;

/**
 * 帮助中心
 */
public class HelpActivity extends BaseActivity {
    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HelpActivity.class));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public void initToolBar() {
        commonToolbar.setTitleText("帮助");
        commonToolbar.setBackVisibility(true);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
    }
}
