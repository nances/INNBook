package com.kaqi.niuniu.ireader.presenter.contract;

import com.kaqi.niuniu.ireader.model.bean.BooksByCats;
import com.kaqi.niuniu.ireader.model.bean.CategoryList;
import com.kaqi.niuniu.ireader.model.bean.RankingList;
import com.kaqi.niuniu.ireader.ui.base.BaseContract;

/**
 * Created by Niqiao on 2019年06月29日23:00:24.
 */

public interface BookChannelContract {

    interface View extends BaseContract.BaseView {
        void showCategoryList(CategoryList data);

        void showRankList(RankingList rankingList);

        void showRankList(BooksByCats data);

        void showErrorTip(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getCategoryList();

        void getRankList();

        void getRankList(String id);
    }
}
