package com.kaqi.reader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseFragment;
import com.kaqi.reader.bean.ItemModel;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.ui.adapter.MoneyListAdapter;

import java.util.ArrayList;

import butterknife.Bind;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * 清单
 *
 * @author Niqiao.
 * @date 2019年05月31日10:59:50
 */
public class MoneyDetailedListFragment extends BaseFragment {

    @Bind(R.id.money_list)
    LRecyclerView moneyList;
    ArrayList<ItemModel> newList = new ArrayList<>();

    MoneyListAdapter moneyListAdapter;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    public int getLayoutResId() {
        return R.layout.activity_money_list;
    }

    public static MoneyDetailedListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MoneyDetailedListFragment fragment = new MoneyDetailedListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initDatas() {
        for (int i = 0; i < 10; i++) {

            ItemModel item = new ItemModel();
            item.id = +i;
            item.title = "2019年06月03日10:20:01 " + (item.id);
            newList.add(item);
        }
    }

    @Override
    public void configViews() {
        moneyListAdapter = new MoneyListAdapter(getActivity());
        moneyListAdapter.setDataList(newList);
        moneyList.setOverScrollMode(OVER_SCROLL_NEVER);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(moneyListAdapter);
        moneyList.setAdapter(lRecyclerViewAdapter);
        moneyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        moneyList.setLoadMoreEnabled(false);
        moneyList.setPullRefreshEnabled(false);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void attachView() {

    }

}
