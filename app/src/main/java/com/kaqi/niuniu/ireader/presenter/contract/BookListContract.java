package com.kaqi.niuniu.ireader.presenter.contract;

import com.kaqi.niuniu.ireader.model.bean.BookListBean;
import com.kaqi.niuniu.ireader.model.flag.RecommendBookSelfType;
import com.kaqi.niuniu.ireader.ui.base.BaseContract;

import java.util.List;

/**
 * Created by newbiechen on 17-5-1.
 */

public interface BookListContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookListBean> beans);
        void finishLoading(List<BookListBean> beans);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookList(RecommendBookSelfType type, String tag, int start, int limited);
        void loadBookList(RecommendBookSelfType type, String tag, int start, int limited);
    }
}
