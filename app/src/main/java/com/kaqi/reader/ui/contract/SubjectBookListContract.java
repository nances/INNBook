package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.BookListTags;

public interface SubjectBookListContract {

    interface View extends BaseContract.BaseView {
        void showBookListTags(BookListTags data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListTags();
    }
}
