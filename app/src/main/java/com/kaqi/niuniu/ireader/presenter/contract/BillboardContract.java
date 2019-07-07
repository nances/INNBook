package com.kaqi.niuniu.ireader.presenter.contract;

import com.kaqi.niuniu.ireader.model.bean.packages.BillboardPackage;
import com.kaqi.niuniu.ireader.ui.base.BaseContract;

/**
 * Created by newbiechen on 17-4-23.
 */

public interface BillboardContract {

    interface View extends BaseContract.BaseView {
        void finishRefresh(BillboardPackage beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadBillboardList();
    }
}
