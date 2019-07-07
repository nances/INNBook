package com.kaqi.niuniu.ireader.presenter;

import com.kaqi.niuniu.ireader.model.bean.BooksByCats;
import com.kaqi.niuniu.ireader.model.bean.CategoryList;
import com.kaqi.niuniu.ireader.model.bean.RankingList;
import com.kaqi.niuniu.ireader.model.bean.Rankings;
import com.kaqi.niuniu.ireader.model.remote.RemoteRepository;
import com.kaqi.niuniu.ireader.presenter.contract.BookChannelContract;
import com.kaqi.niuniu.ireader.ui.base.RxPresenter;
import com.kaqi.niuniu.ireader.utils.LogUtils;
import com.kaqi.niuniu.ireader.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by niqiao on 2019年06月29日23:12:15.
 */

public class BookChannelPresenter extends RxPresenter<BookChannelContract.View>
        implements BookChannelContract.Presenter {
    private static final String TAG = "BookShelfPresenter";

    @Override
    public void getCategoryList() {
        Disposable disposable = RemoteRepository.getInstance()
                .getCategoryList()
                .doOnSuccess(new Consumer<CategoryList>() {
                    @Override
                    public void accept(CategoryList categoryList) throws Exception {

                    }
                })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            mView.showCategoryList(beans);
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

    @Override
    public void getRankList() {
        Disposable disposable = RemoteRepository.getInstance()
                .getRanking()
                .doOnSuccess(new Consumer<RankingList>() {
                    @Override
                    public void accept(RankingList rankingList) throws Exception {

                    }
                })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            mView.showRankList(beans);
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

    @Override
    public void getRankList(String id) {
        Disposable disposable = RemoteRepository.getInstance()
                .getRankList(id)
                .doOnSuccess(new Consumer<Rankings>() {
                    @Override
                    public void accept(Rankings rankings) throws Exception {

                    }
                })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            List<Rankings.RankingBean.BooksBean> books = beans.ranking.books;

                            BooksByCats cats = new BooksByCats();
                            cats.books = new ArrayList<>();
                            for (Rankings.RankingBean.BooksBean bean : books) {
                                cats.books.add(new BooksByCats.BooksBean(bean._id, bean.cover, bean.title, bean.author, bean.cat, bean.shortIntro, bean.latelyFollower, bean.retentionRatio));
                            }
                            mView.showRankList(cats);
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
