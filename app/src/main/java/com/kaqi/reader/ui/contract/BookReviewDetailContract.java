package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.BookReview;
import com.kaqi.reader.bean.CommentList;

/**
 * @author lfh.
 * @date 16/9/3
 */
public interface BookReviewDetailContract {

    interface View extends BaseContract.BaseView {

        void showBookReviewDetail(BookReview data);

        void showBestComments(CommentList list);

        void showBookReviewComments(CommentList list);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getBookReviewDetail(String id);

        void getBestComments(String bookReviewId);

        void getBookReviewComments(String bookReviewId, int start, int limit);

    }

}
