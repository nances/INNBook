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
import android.view.View;
import android.widget.ExpandableListView;

import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.bean.RankingList;
import com.kaqi.reader.common.OnRvItemClickListener;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerFindComponent;
import com.kaqi.reader.ui.adapter.TopRankAdapter;
import com.kaqi.reader.ui.contract.TopRankContract;
import com.kaqi.reader.ui.presenter.TopRankPresenter;
import com.kaqi.reader.utils.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopRankActivity extends BaseActivity implements TopRankContract.View {

    @Bind(R.id.elvFeMale)
    ExpandableListView elvFeMale;
    @Bind(R.id.elvMale)
    ExpandableListView elvMale;
    @Bind(R.id.common_toolbar)
    NormalTitleBar commonToolbar;

    private List<RankingList.MaleBean> maleGroups = new ArrayList<>();
    private List<List<RankingList.MaleBean>> maleChilds = new ArrayList<>();
    private TopRankAdapter maleAdapter;

    private List<RankingList.MaleBean> femaleGroups = new ArrayList<>();
    private List<List<RankingList.MaleBean>> femaleChilds = new ArrayList<>();
    private TopRankAdapter femaleAdapter;

    @Inject
    TopRankPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TopRankActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_top_rank;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        commonToolbar.setTitleText("排行榜");
        commonToolbar.setBackVisibility(true);
    }

    @Override
    public void initDatas() {
        maleAdapter = new TopRankAdapter(this, maleGroups, maleChilds);
        femaleAdapter = new TopRankAdapter(this, femaleGroups, femaleChilds);
        maleAdapter.setItemClickListener(new ClickListener());
        femaleAdapter.setItemClickListener(new ClickListener());
    }

    @Override
    public void configViews() {
        showDialog();
        elvMale.setAdapter(maleAdapter);
        elvFeMale.setAdapter(femaleAdapter);

        mPresenter.attachView(this);
        mPresenter.getRankList();
    }

    @Override
    public void showRankList(RankingList rankingList) {
        maleGroups.clear();
        femaleGroups.clear();
        updateMale(rankingList);
        updateFemale(rankingList);
    }

    private void updateMale(RankingList rankingList) {
    }

    private void updateFemale(RankingList rankingList) {
        femaleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class ClickListener implements OnRvItemClickListener<RankingList.MaleBean> {

        @Override
        public void onItemClick(View view, int position, RankingList.MaleBean data) {
//            if (data.monthRank == null) {
//                SubOtherHomeRankActivity.startActivity(mContext, data._id, data.title);
//            } else {
//                SubRankActivity.startActivity(mContext, data._id, data.monthRank, data.totalRank, data.title);
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
