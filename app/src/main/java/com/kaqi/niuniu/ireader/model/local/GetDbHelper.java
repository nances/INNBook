package com.kaqi.niuniu.ireader.model.local;

import com.kaqi.niuniu.ireader.model.bean.AuthorBean;
import com.kaqi.niuniu.ireader.model.bean.BookCommentBean;
import com.kaqi.niuniu.ireader.model.bean.BookHelpfulBean;
import com.kaqi.niuniu.ireader.model.bean.BookHelpsBean;
import com.kaqi.niuniu.ireader.model.bean.BookReviewBean;
import com.kaqi.niuniu.ireader.model.bean.DownloadTaskBean;
import com.kaqi.niuniu.ireader.model.bean.ReviewBookBean;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by newbiechen on 17-4-28.
 */

public interface GetDbHelper {
    Single<List<BookCommentBean>> getBookComments(String block, String sort, int start, int limited, String distillate);
    Single<List<BookHelpsBean>> getBookHelps(String sort, int start, int limited, String distillate);
    Single<List<BookReviewBean>> getBookReviews(String sort, String bookType, int start, int limited, String distillate);

    AuthorBean getAuthor(String id);
    ReviewBookBean getReviewBook(String id);
    BookHelpfulBean getBookHelpful(String id);

    /******************************/
    List<DownloadTaskBean> getDownloadTaskList();
}
