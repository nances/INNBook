package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.BookMixAToc;
import com.kaqi.reader.bean.Recommend;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public interface RecommendContract {

    interface View extends BaseContract.BaseView {
        void showRecommendList(List<Recommend.RecommendBooks> list);

        void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list);

        void syncBookShelfCompleted();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRecommendList();

        void syncBookShelf();
    }

}
