package com.kaqi.niuniu.ireader.presenter;

import com.kaqi.niuniu.ireader.model.bean.BookHelpsBean;
import com.kaqi.niuniu.ireader.model.flag.BookDistillate;
import com.kaqi.niuniu.ireader.model.flag.BookSort;
import com.kaqi.niuniu.ireader.model.local.LocalRepository;
import com.kaqi.niuniu.ireader.model.remote.RemoteRepository;
import com.kaqi.niuniu.ireader.presenter.contract.DiscHelpsContract;
import com.kaqi.niuniu.ireader.ui.base.RxPresenter;
import com.kaqi.niuniu.ireader.utils.LogUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.kaqi.niuniu.ireader.utils.LogUtils.e;

/**
 * Created by newbiechen on 17-4-21.
 */

public class DiscHelpsPresenter extends RxPresenter<DiscHelpsContract.View> implements DiscHelpsContract.Presenter {
    private boolean isLocalLoad = false;

    @Override
    public void firstLoading(BookSort sort, int start, int limited, BookDistillate distillate) {
        //获取数据库中的数据
        Single<List<BookHelpsBean>> localObserver = LocalRepository.getInstance()
                .getBookHelps(sort.getDbName(), start, limited, distillate.getDbName());
        Single<List<BookHelpsBean>> remoteObserver = RemoteRepository.getInstance()
                .getBookHelps(sort.getNetName(), start, limited, distillate.getNetName());

        Single.concat(localObserver,remoteObserver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishRefresh(beans);
                        }
                        ,
                        (e) ->{
                            isLocalLoad = true;
                            mView.complete();
                            mView.showErrorTip();
                            LogUtils.e(e);
                        }
                        ,
                        ()-> {
                            isLocalLoad = false;
                            mView.complete();
                        }
                );
    }

    @Override
    public void refreshBookHelps(BookSort sort, int start, int limited, BookDistillate distillate) {
        Disposable refreshDispo = RemoteRepository.getInstance()
                .getBookHelps(sort.getNetName(), start, limited, distillate.getNetName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans)-> {
                            isLocalLoad = false;
                            mView.finishRefresh(beans);
                            mView.complete();
                        }
                        ,
                        (e) ->{
                            mView.complete();
                            mView.showErrorTip();
                            LogUtils.e(e);
                        }
                );
        addDisposable(refreshDispo);
    }

    @Override
    public void loadingBookHelps(BookSort sort, int start, int limited, BookDistillate distillate) {
        if (isLocalLoad){
            Single<List<BookHelpsBean>> single = LocalRepository.getInstance()
                    .getBookHelps(sort.getDbName(), start, limited, distillate.getDbName());
            loadBookHelps(single);
        }

        else{
            Single<List<BookHelpsBean>> single = RemoteRepository.getInstance()
                    .getBookHelps(sort.getNetName(), start, limited, distillate.getNetName());
            loadBookHelps(single);
        }
    }

    @Override
    public void saveBookHelps(List<BookHelpsBean> beans) {
        LocalRepository.getInstance()
                .saveBookHelps(beans);
    }

    private void loadBookHelps(Single<List<BookHelpsBean>> observable){
        Disposable loadDispo =observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (beans) -> {
                            mView.finishLoading(beans);
                        }
                        ,
                        (e) -> {
                            mView.showError();
                            LogUtils.e(e);
                        }
                );
        addDisposable(loadDispo);
    }
}
