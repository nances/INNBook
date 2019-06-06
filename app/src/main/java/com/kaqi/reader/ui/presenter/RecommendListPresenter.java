package com.kaqi.reader.ui.presenter;

import android.util.Log;

import com.kaqi.reader.api.BookApi;
import com.kaqi.reader.base.RxPresenter;
import com.kaqi.reader.bean.RecommendListBean;
import com.kaqi.reader.ui.contract.RecommendListContract;
import com.kaqi.reader.utils.LogUtils;
import com.kaqi.reader.utils.RxUtil;
import com.kaqi.reader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author nancy.
 * @date 2019年06月05日14:27:18
 */
public class RecommendListPresenter extends RxPresenter<RecommendListContract.View> implements RecommendListContract.Presenter<RecommendListContract.View> {

        private BookApi bookApi;

    @Inject
    public RecommendListPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getRecommendList() {
        String key = StringUtils.creatAcacheKey("recommend_list");
        Observable<RecommendListBean> fromNetWork = bookApi.getShowRecommendList()
                .compose(RxUtil.<RecommendListBean>rxCacheBeanHelper(key));
        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, RecommendListBean.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendListBean>() {
                    @Override
                    public void onNext(RecommendListBean data) {
                        if (data != null && mView != null) {
                            Log.v("Nancys","return showRecommendList");
                            mView.showRecommendList(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("showRecommendList:" + e.toString());
                        mView.complete();
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
