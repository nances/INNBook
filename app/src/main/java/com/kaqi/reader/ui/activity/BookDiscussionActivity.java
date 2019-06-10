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
import android.os.Bundle;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseCommuniteActivity;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.fragment.BookDiscussionFragment;
import com.kaqi.reader.utils.NormalTitleBar;
import com.kaqi.reader.view.SelectionLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 综合讨论区
 */
public class BookDiscussionActivity extends BaseCommuniteActivity {

    private static final String INTENT_DIS = "isDis";
    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    public static void startActivity(Context context, boolean isDiscussion) {
        context.startActivity(new Intent(context, BookDiscussionActivity.class)
                .putExtra(INTENT_DIS, isDiscussion));
    }

    private boolean mIsDiscussion;

    @Bind(R.id.slOverall)
    SelectionLayout slOverall;

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_book_discussion;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mIsDiscussion = getIntent().getBooleanExtra(INTENT_DIS, false);
        if (mIsDiscussion) {
            commonToolbar.setTitleText("综合讨论区");
        } else {
            commonToolbar.setTitleText("原创区");
        }
        commonToolbar.setBackVisibility(true);
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    protected List<List<String>> getTabList() {
        return list1;
    }

    @Override
    public void configViews() {
        BookDiscussionFragment fragment = BookDiscussionFragment.newInstance(mIsDiscussion ? "ramble" : "original");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentCO, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
