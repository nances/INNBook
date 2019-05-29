package com.kaqi.reader.ui.presenter;

import com.kaqi.reader.api.BookApi;
import com.kaqi.reader.base.RxPresenter;
import com.kaqi.reader.bean.CategoryListLv2;
import com.kaqi.reader.ui.contract.SubCategoryActivityContract;
import com.kaqi.reader.utils.LogUtils;
import com.kaqi.reader.utils.RxUtil;
import com.kaqi.reader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author yuyh.
 * @date 2016/8/31.
 */
public class SubCategoryActivityPresenter extends RxPresenter<SubCategoryActivityContract.View> implements SubCategoryActivityContract.Presenter<SubCategoryActivityContract.View> {

    private BookApi bookApi;

    @Inject
    public SubCategoryActivityPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getCategoryListLv2() {
        String key = StringUtils.creatAcacheKey("category-list2");
        Observable<CategoryListLv2> fromNetWork = bookApi.getCategoryListLv2()
                .compose(RxUtil.<CategoryListLv2>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, CategoryListLv2.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryListLv2>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getCategoryListLv2:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(CategoryListLv2 categoryListLv2) {
                        mView.showCategoryList(categoryListLv2);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
