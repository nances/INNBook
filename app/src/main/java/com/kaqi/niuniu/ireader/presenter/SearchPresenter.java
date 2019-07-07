package com.kaqi.niuniu.ireader.presenter;

import com.kaqi.niuniu.ireader.model.remote.RemoteRepository;
import com.kaqi.niuniu.ireader.presenter.contract.SearchContract;
import com.kaqi.niuniu.ireader.ui.base.RxPresenter;
import com.kaqi.niuniu.ireader.utils.LogUtils;
import com.kaqi.niuniu.ireader.utils.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by newbiechen on 17-6-2.
 */

public class SearchPresenter extends RxPresenter<SearchContract.View>
        implements SearchContract.Presenter{

    @Override
    public void searchHotWord() {
        Disposable disp = RemoteRepository.getInstance()
                .getHotWords()
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            mView.finishHotWords(bean);
                        },
                        e -> {
                            LogUtils.e(e);
                        }
                );
        addDisposable(disp);
    }

    @Override
    public void searchKeyWord(String query) {
        Disposable disp = RemoteRepository.getInstance()
                .getKeyWords(query)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            mView.finishKeyWords(bean);
                        },
                        e -> {
                            LogUtils.e(e);
                        }
                );
        addDisposable(disp);
    }

    @Override
    public void searchBook(String query) {
        Disposable disp = RemoteRepository.getInstance()
                .getSearchBooks(query)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        bean -> {
                            mView.finishBooks(bean);
                        },
                        e -> {
                            LogUtils.e(e);
                            mView.errorBooks();
                        }
                );
        addDisposable(disp);
    }
}
