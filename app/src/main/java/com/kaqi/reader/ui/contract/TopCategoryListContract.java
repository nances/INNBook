package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.BooksByCats;
import com.kaqi.reader.bean.CategoryList;
import com.kaqi.reader.bean.RankingList;


public interface TopCategoryListContract {

    interface View extends BaseContract.BaseView {
        void showCategoryList(CategoryList data);

        void showRankList(RankingList rankingList);

        void showRankList(BooksByCats data);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getCategoryList();

        void getRankList();

        void getRankList(String id);

    }

}
