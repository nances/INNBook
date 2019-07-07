package com.kaqi.niuniu.ireader.presenter.contract;

import com.kaqi.niuniu.ireader.model.bean.RecommendListBean;
import com.kaqi.niuniu.ireader.ui.base.BaseContract;

/**
 * Created by niqiao on 2019年06月28日13:02:13.
 */

public interface RecommendBookContract {

    interface View extends BaseContract.BaseView {

        void showRecommendList(RecommendListBean rankingList);

        void showErrorTip(String error);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getRecommendList();

    }
}
