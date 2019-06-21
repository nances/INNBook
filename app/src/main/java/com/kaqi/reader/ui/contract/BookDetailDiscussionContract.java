package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.DiscussionList;

import java.util.List;

/**
 * @author lfh.
 * @date 2016/9/7.
 */
public interface BookDetailDiscussionContract {

    interface View extends BaseContract.BaseView {
        void showBookDetailDiscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookDetailDiscussionList(String bookId, String sort, int start, int limit);
    }
}
