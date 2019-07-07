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
package com.kaqi.niuniu.ireader.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.ItemModel;
import com.kaqi.niuniu.ireader.ui.base.BaseActivity;
import com.kaqi.niuniu.ireader.ui.fragment.MoneyDetailedListFragment;
import com.kaqi.niuniu.ireader.view.NormalTitleBar;
import com.kaqi.niuniu.ireader.widget.ComFragmentAdapter;
import com.kaqi.niuniu.ireader.widget.dialog.CommomRechargeMoneyDialog;

import java.util.ArrayList;

import butterknife.BindView;

public class MoneyDetailedListActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    NormalTitleBar commonToolbar;
    //    @Bind(R.id.money_list)
//    LRecyclerView moneyList;
    SlidingTabLayout slidingTabLayout;

    TextView rechagrgeMoneys;
    private View moneyheadView;
    public View moneyHeadTitle;
    private ViewPager viewPager;

    LRecyclerViewAdapter lRecyclerViewAdapter;

    MoneyDetailedListFragment moneyDetailedListFragment;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<ItemModel> newList = new ArrayList<>();
    private String[] titles = new String[]{"金币明细", "提现明细"};

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MoneyDetailedListActivity.class));
    }


    @Override
    protected int getContentId() {
        return R.layout.activity_money_list_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonToolbar.setTitleText("我的钱包");
        commonToolbar.setBackVisibility(true);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        moneyheadView = LayoutInflater.from(this).inflate(R.layout.activity_money_headview, (ViewGroup) findViewById(android.R.id.content), false);
//        moneyHeadTitle = LayoutInflater.from(this).inflate(R.layout.money_list_title, (ViewGroup) findViewById(android.R.id.content), false);
//        moneyList.setOverScrollMode(OVER_SCROLL_NEVER);
//        lRecyclerViewAdapter = new LRecyclerViewAdapter();
//        lRecyclerViewAdapter.addHeaderView(moneyheadView);
//        lRecyclerViewAdapter.addHeaderView(moneyHeadTitle);
//        moneyList.setLayoutManager(new LinearLayoutManager(this));
//        moneyList.setLoadMoreEnabled(false);
//        moneyList.setPullRefreshEnabled(false);
        slidingTabLayout = this.findViewById(R.id.tabs);
        rechagrgeMoneys = this.findViewById(R.id.recharge_days);
        viewPager = this.findViewById(R.id.viewPager);
        rechagrgeMoneys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRechageMoney();
            }
        });
        initViewPager();
    }

    /**
     * 提现
     */
    public void getRechageMoney() {
        new CommomRechargeMoneyDialog(this, R.style.dialog, new CommomRechargeMoneyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, String confirm) {

            }
        }).show();
    }


    private void initViewPager() {

        fragments.add(MoneyDetailedListFragment.newInstance(1));
        fragments.add(MoneyDetailedListFragment.newInstance(2));
        viewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), fragments));

        slidingTabLayout.setViewPager(viewPager, titles);
        viewPager.setOffscreenPageLimit(titles.length);
        slidingTabLayout.setCurrentTab(0);
        slidingTabLayout.getTitleView(0).setTextSize(18);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                updateTextSize(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void updateTextSize(int position) {
        for (int i = 0; i < fragments.size(); i++) {
            final boolean isSelect = i == position;
            if (isSelect) {
                slidingTabLayout.getTitleView(i).setTextSize(26);
            } else {
                slidingTabLayout.getTitleView(i).setTextSize(16);
            }
        }
    }

}
