package com.kaqi.reader.ui.contract;

import com.kaqi.reader.base.BaseContract;
import com.kaqi.reader.bean.DiscussionList;

import java.util.List;

 

public interface GirlBookDiscussionContract {

    interface View extends BaseContract.BaseView {
        void showGirlBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getGirlBookDisscussionList(String sort, String distillate, int start, int limit);
    }

}
