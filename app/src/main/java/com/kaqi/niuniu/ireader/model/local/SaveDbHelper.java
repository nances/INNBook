package com.kaqi.niuniu.ireader.model.local;

import com.kaqi.niuniu.ireader.model.bean.AuthorBean;
import com.kaqi.niuniu.ireader.model.bean.BookCommentBean;
import com.kaqi.niuniu.ireader.model.bean.BookHelpfulBean;
import com.kaqi.niuniu.ireader.model.bean.BookHelpsBean;
import com.kaqi.niuniu.ireader.model.bean.BookReviewBean;
import com.kaqi.niuniu.ireader.model.bean.DownloadTaskBean;
import com.kaqi.niuniu.ireader.model.bean.ReviewBookBean;

import java.util.List;

/**
 * Created by newbiechen on 17-4-28.
 */

public interface SaveDbHelper {
    void saveBookComments(List<BookCommentBean> beans);
    void saveBookHelps(List<BookHelpsBean> beans);
    void saveBookReviews(List<BookReviewBean> beans);
    void saveAuthors(List<AuthorBean> beans);
    void saveBooks(List<ReviewBookBean> beans);
    void saveBookHelpfuls(List<BookHelpfulBean> beans);

    /*************DownloadTask*********************/
    void saveDownloadTask(DownloadTaskBean bean);
}
