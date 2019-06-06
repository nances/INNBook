package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.RecommendListBean;


public interface RecommendListContract {

    interface View extends BaseContract.BaseView {
        void showRecommendList(RecommendListBean rankingList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRecommendList();
    }

}
