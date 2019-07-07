package com.kaqi.niuniu.ireader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.ItemModel;
import com.kaqi.niuniu.ireader.ui.adapter.MoneyListAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * 清单
 *
 * @author Niqiao.
 * @date 2019年05月31日10:59:50
 */
public class MoneyDetailedListFragment extends BaseFragment {

    @BindView(R.id.money_list)
    LRecyclerView moneyList;
    ArrayList<ItemModel> newList = new ArrayList<>();

    MoneyListAdapter moneyListAdapter;
    LRecyclerViewAdapter lRecyclerViewAdapter;

    @Override
    protected int getContentId() {
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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        for (int i = 0; i < 10; i++) {

            ItemModel item = new ItemModel();
            item.id = +i;
            item.title = "2019年06月03日10:20:01 " + (item.id);
            newList.add(item);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        moneyListAdapter = new MoneyListAdapter(getActivity());
        moneyListAdapter.setDataList(newList);
        moneyList.setOverScrollMode(OVER_SCROLL_NEVER);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(moneyListAdapter);
        moneyList.setAdapter(lRecyclerViewAdapter);
        moneyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        moneyList.setLoadMoreEnabled(false);
        moneyList.setPullRefreshEnabled(false);
    }


}
