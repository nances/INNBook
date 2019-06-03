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

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.bean.ItemModel;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerMainComponent;
import com.kaqi.reader.ui.adapter.ComFragmentAdapter;
import com.kaqi.reader.ui.fragment.MoneyDetailedListFragment;
import com.kaqi.reader.view.dialog.CommomRechargeMoneyDialog;

import java.util.ArrayList;

import butterknife.Bind;

public class MoneyDetailedListActivity extends BaseActivity {

    @Bind(R.id.common_toolbar)
    Toolbar commonToolbar;
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


    }

    @Override
    public void configViews() {
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
        new CommomRechargeMoneyDialog(mContext, R.style.dialog, new CommomRechargeMoneyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, String confirm) {
                dismissDialog();
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
