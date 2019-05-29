package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.BookMixAToc;
import com.kaqi.reader.bean.ChapterRead;

import java.util.List;

/**
 * @author niqiao.
 * @date 2019年04月24日15:11:51
 */
public interface BookReadContract {

    interface View extends BaseContract.BaseView {
        void showBookToc(List<BookMixAToc.mixToc.Chapters> list);

        void showChapterRead(ChapterRead.Chapter data, int chapter);

        void netError(int chapter);//添加网络处理异常接口
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookMixAToc(String bookId, String view,boolean iscate);

        void getChapterRead(String url, int chapter);
    }

}
