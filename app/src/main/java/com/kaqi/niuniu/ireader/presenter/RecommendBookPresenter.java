package com.kaqi.niuniu.ireader.presenter;

import com.kaqi.niuniu.ireader.model.bean.RecommendListBean;
import com.kaqi.niuniu.ireader.model.remote.RemoteRepository;
import com.kaqi.niuniu.ireader.presenter.contract.RecommendBookContract;
import com.kaqi.niuniu.ireader.ui.base.RxPresenter;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.LogUtils;
import com.kaqi.niuniu.ireader.utils.RxUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by niqiao on 2019年06月28日13:15:21.
 */

public class RecommendBookPresenter extends RxPresenter<RecommendBookContract.View>
        implements RecommendBookContract.Presenter {
    private static final String TAG = "RecommendBookPresenter";

    @Override
    public void getRecommendList() {
        Disposable disposable = RemoteRepository.getInstance(Constant.API_BASE_NIUNIU_URL)
                .getRecommendBookSelfList()
                .doOnSuccess(new Consumer<RecommendListBean>() {
                    @Override
                    public void accept(RecommendListBean recommendListBean) throws Exception {

                    }
                })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            mView.showRecommendList(beans);
                            mView.complete();
                        },
                        (e) -> {
                            //提示没有网络
                            LogUtils.e(e);
                            mView.showErrorTip(e.toString());
                            mView.complete();
                        }
                );
        addDisposable(disposable);

    }
}
