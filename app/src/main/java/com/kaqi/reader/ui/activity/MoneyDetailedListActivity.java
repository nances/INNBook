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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.bean.ItemModel;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerMainComponent;
import com.kaqi.reader.ui.adapter.MoneyListAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.OVER_SCROLL_NEVER;

public class MoneyDetailedListActivity extends BaseActivity {

    @Bind(R.id.common_toolbar)
    Toolbar commonToolbar;
    @Bind(R.id.money_list)
    LRecyclerView moneyList;

    MoneyListAdapter moneyListAdapter;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    private View moneyheadView;
    ArrayList<ItemModel> newList = new ArrayList<>();
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MoneyDetailedListActivity.class));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_money_list_detail;
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
        mCommonToolbar.setTitle("我的钱包");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        for (int i = 0; i < 10; i++) {

            ItemModel item = new ItemModel();
            item.id =  + i;
            item.title = "获取多少酒金币 " + (item.id);
            newList.add(item);
        }
    }

    @Override
    public void configViews() {
        moneyheadView = LayoutInflater.from(this).inflate(R.layout.activity_money_headview,(ViewGroup)findViewById(android.R.id.content), false);
        moneyListAdapter = new MoneyListAdapter(this);
        moneyListAdapter.setDataList(newList);
        moneyList.setOverScrollMode(OVER_SCROLL_NEVER);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(moneyListAdapter);
        moneyList.setAdapter(lRecyclerViewAdapter);
        lRecyclerViewAdapter.addHeaderView(moneyheadView);
        moneyList.setLayoutManager(new LinearLayoutManager(this));
        moneyList.setLoadMoreEnabled(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
