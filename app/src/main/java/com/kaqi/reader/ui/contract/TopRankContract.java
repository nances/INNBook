package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.RankingList;


public interface TopRankContract {

    interface View extends BaseContract.BaseView {
        void showRankList(RankingList rankingList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRankList();
    }

}
