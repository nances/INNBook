package com.kaqi.reader.ui.fragment;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaqi.reader.R;
import com.kaqi.reader.base.BaseRVFragment;
import com.kaqi.reader.bean.BookMixAToc;
import com.kaqi.reader.bean.NavItemEntity;
import com.kaqi.reader.bean.Recommend;
import com.kaqi.reader.bean.support.DownloadMessage;
import com.kaqi.reader.bean.support.DownloadProgress;
import com.kaqi.reader.bean.support.DownloadQueue;
import com.kaqi.reader.bean.support.RefreshCollectionListEvent;
import com.kaqi.reader.bean.support.UserSexChooseFinishedEvent;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerMainComponent;
import com.kaqi.reader.manager.CollectionsManager;
import com.kaqi.reader.manager.EventManager;
import com.kaqi.reader.manager.SettingManager;
import com.kaqi.reader.service.DownloadBookService;
import com.kaqi.reader.ui.activity.BookDetailActivity;
import com.kaqi.reader.ui.activity.MainActivity;
import com.kaqi.reader.ui.activity.ReadActivity;
import com.kaqi.reader.ui.activity.ReadBookHistoryActivity;
import com.kaqi.reader.ui.activity.SearchActivity;
import com.kaqi.reader.ui.activity.WifiBookActivity;
import com.kaqi.reader.ui.contract.RecommendContract;
import com.kaqi.reader.ui.easyadapter.RecommendAdapter;
import com.kaqi.reader.ui.presenter.RecommendPresenter;
import com.kaqi.reader.view.dialog.CommomMannagerDialog;
import com.kaqi.reader.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.PopupParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecommendFragment extends BaseRVFragment<RecommendPresenter, Recommend.RecommendBooks> implements RecommendContract.View, RecyclerArrayAdapter.OnItemLongClickListener {

    @Bind(R.id.llBatchManagement)
    RelativeLayout llBatchManagement;
    @Bind(R.id.tvSelectAll)
    TextView tvSelectAll;
    @Bind(R.id.tvDelete)
    TextView tvDelete;
    @Bind(R.id.search_rl)
    RelativeLayout searchRl;
    @Bind(R.id.book_admin)
    RelativeLayout bookAdmin;
    @Bind(R.id.recomend_title)
    TextView recomend_title;
    @Bind(R.id.recommendRL)
    RelativeLayout recommendRL;
    @Bind(R.id.read_book_rl)
    RelativeLayout read_book_rl;

    private boolean isSelectAll = false;
    private Typeface impact_tf;
    List<NavItemEntity> list = new ArrayList<>();
    DialogInterface dialogAdmin;
    private List<BookMixAToc.mixToc.Chapters> chaptersList = new ArrayList<>();

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initDatas() {
        AssetManager mgr = getActivity().getAssets();
        impact_tf = Typeface.createFromAsset(mgr, "fonts/ImpactMTStd.otf");
        recomend_title.setTypeface(impact_tf);
        EventBus.getDefault().register(this);

        if (SettingManager.getInstance().getFirstInApp() == 0) {
            EventBus.getDefault().post(new UserSexChooseFinishedEvent());
        }

        SettingManager.getInstance().saveFirstInApp(1);
        list.add(new NavItemEntity("书架管理", R.drawable.ic_popup_abnormal));
        list.add(new NavItemEntity("WIFI传书", R.drawable.ic_popup_note));
        list.add(new NavItemEntity("申请延迟", R.drawable.ic_popup_delay));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dialogAdmin == null) {
                    return;
                }
                dialogAdmin.dismiss();
                if (position == 0) {
                    showBatchManagementLayout();
                } else if (position == 1) {
                    WifiBookActivity.startActivity(getActivity());
                }
            }
        });

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                dialogAdmin = dialog;
            }
        });
    }

    @Override
    public void configViews() {

        initAdapter(RecommendAdapter.class, true, false);
        mRecyclerView.removeAllItemDecoration();
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View headerView = LayoutInflater.from(activity).inflate(R.layout.foot_view_shelf, parent, false);
                return headerView;
            }

            @Override
            public void onBindView(View headerView) {
                headerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) activity).setCurrentItem(1);
                    }
                });
            }
        });
        mRecyclerView.getEmptyView().findViewById(R.id.btnToAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).setCurrentItem(1);
            }
        });
        onRefresh();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showRecommendList(List<Recommend.RecommendBooks> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        //推荐列表默认加入收藏
        for (Recommend.RecommendBooks bean : list) {
            //TODO 此处可优化：批量加入收藏->加入前需先判断是否收藏过
            CollectionsManager.getInstance().add(bean);
        }
    }

    @Override
    public void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list) {
        chaptersList.clear();
        chaptersList.addAll(list);
        DownloadBookService.post(new DownloadQueue(bookId, list, 1, list.size()));
        dismissDialog();
    }

    @Override
    public void syncBookShelfCompleted() {
        dismissDialog();
        EventManager.refreshCollectionList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadMessage(final DownloadMessage msg) {
        mRecyclerView.setTipViewText(msg.message);
        if (msg.isComplete) {
            mRecyclerView.hideTipView(2200);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDownProgress(DownloadProgress progress) {
        mRecyclerView.setTipViewText(progress.message);
    }

    @Override
    public void onItemClick(int position) {
        if (isVisible(llBatchManagement)) //批量管理时，屏蔽点击事件
            return;
        ReadActivity.startActivity(activity, mAdapter.getItem(position), mAdapter.getItem(position).isFromSD);
    }

    @Override
    public boolean onItemLongClick(int position) {
        //批量管理时，屏蔽长按事件
        if (isVisible(llBatchManagement)) return false;
//        showLongClickDialog(position);
        setBookMannager(position);
        return false;
    }

    /**
     * 显示长按对话框
     *
     * @param position
     */
    private void showLongClickDialog(final int position) {
        final boolean isTop = CollectionsManager.getInstance().isTop(mAdapter.getItem(position)._id);
        setBookMannager(position);
        String[] items;
        DialogInterface.OnClickListener listener;
//        if (mAdapter.getItem(position).isFromSD) {
        if (true) {
            items = getResources().getStringArray(R.array.recommend_item_long_click_choice_local);
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            //置顶、取消置顶
                            CollectionsManager.getInstance().top(mAdapter.getItem(position)._id, !isTop);
                            break;
                        case 1:
                            //删除
                            List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                            removeList.add(mAdapter.getItem(position));
                            showDeleteCacheDialog(removeList);
                            break;
                        case 2:
                            //批量管理
                            showBatchManagementLayout();
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            };
        } else {
            items = getResources().getStringArray(R.array.recommend_item_long_click_choice);
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            //置顶、取消置顶
                            CollectionsManager.getInstance().top(mAdapter.getItem(position)._id, !isTop);
                            break;
                        case 1:
                            //书籍详情
                            BookDetailActivity.startActivity(activity,
                                    mAdapter.getItem(position)._id);
                            break;
                        case 2:
                            //缓存全本
                            if (mAdapter.getItem(position).isFromSD) {
                                mRecyclerView.showTipViewAndDelayClose("本地文件不支持该选项哦");
                            } else {
                                showDialog();
                                mPresenter.getTocList(mAdapter.getItem(position)._id);
                            }
                            break;
                        case 3:
                            //删除
                            List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                            removeList.add(mAdapter.getItem(position));
                            showDeleteCacheDialog(removeList);
                            break;
                        case 4:
                            //批量管理
                            showBatchManagementLayout();
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            };
        }
        if (isTop) items[0] = getString(R.string.cancle_top);
        new AlertDialog.Builder(activity)
                .setTitle(mAdapter.getItem(position).title)
                .setItems(items, listener)
                .setNegativeButton(null, null)
                .create().show();
    }

    /**
     * 书本弹框管理
     */
    public void setBookMannager(int book_position) {
        final boolean isTop = CollectionsManager.getInstance().isTop(mAdapter.getItem(book_position)._id);
        new CommomMannagerDialog(mContext, R.style.dialog, isTop, new CommomMannagerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, int type) {

                switch (type) {
                    case 0:
                        //置顶、取消置顶
                        CollectionsManager.getInstance().top(mAdapter.getItem(book_position)._id, !isTop);
                        break;
                    case 1:
                        //删除
                        List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                        removeList.add(mAdapter.getItem(book_position));
                        showDeleteCacheDialog(removeList);
                        break;
                    case 2:
                        //批量管理
                        showBatchManagementLayout();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        }).setTitle(mAdapter.getItem(book_position).title).show();

    }

    /**
     * 显示删除本地缓存对话框
     *
     * @param removeList
     */
    private void showDeleteCacheDialog(final List<Recommend.RecommendBooks> removeList) {
        final boolean selected[] = {true};
        new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.remove_selected_book))
                .setMultiChoiceItems(new String[]{activity.getString(R.string.delete_local_cache)}, selected,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selected[0] = isChecked;
                            }
                        })
                .setPositiveButton(activity.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new AsyncTask<String, String, String>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                showDialog();
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                CollectionsManager.getInstance().removeSome(removeList, selected[0]);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                mRecyclerView.showTipViewAndDelayClose("成功移除书籍");
                                for (Recommend.RecommendBooks bean : removeList) {
                                    mAdapter.remove(bean);
                                }
                                if (isVisible(llBatchManagement)) {
                                    //批量管理完成后，隐藏批量管理布局并刷新页面
                                    goneBatchManagementAndRefreshUI();
                                }
                                hideDialog();
                            }
                        }.execute();

                    }
                })
                .setNegativeButton(activity.getString(R.string.cancel), null)
                .create().show();
    }


    public void pullSyncBookShelf() {
        mPresenter.syncBookShelf();
    }


    /**
     * 隐藏批量管理布局并刷新页面
     */
    public void goneBatchManagementAndRefreshUI() {
        if (mAdapter == null) return;
        gone(llBatchManagement);
        visible(recommendRL);
        for (Recommend.RecommendBooks bean :
                mAdapter.getAllData()) {
            bean.showCheckBox = false;
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 显示批量管理布局
     */
    private void showBatchManagementLayout() {
        visible(llBatchManagement);
        gone(recommendRL);
        for (Recommend.RecommendBooks bean : mAdapter.getAllData()) {
            bean.showCheckBox = true;
        }
        mAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.tvSelectAll)
    public void selectAll() {
        isSelectAll = !isSelectAll;
        tvSelectAll.setText(isSelectAll ? activity.getString(R.string.cancel_selected_all) : activity.getString(R.string.selected_all));
        for (Recommend.RecommendBooks bean : mAdapter.getAllData()) {
            bean.isSeleted = isSelectAll;
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tvDelete)
    public void delete() {
        List<Recommend.RecommendBooks> removeList = new ArrayList<>();
        for (Recommend.RecommendBooks bean : mAdapter.getAllData()) {
            if (bean.isSeleted) removeList.add(bean);
        }
        if (removeList.isEmpty()) {
            //没有书籍点击完成可以关闭当前视图
//            mRecyclerView.showTipViewAndDelayClose(activity.getString(R.string.has_not_selected_delete_book));
//            visible(recommendRL);
//            gone(llBatchManagement);
            goneBatchManagementAndRefreshUI();
        } else {
            showDeleteCacheDialog(removeList);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

        StackTraceElement stack[] = (new Throwable()).getStackTrace();


        boolean hasRefBookShelfInCallStack = false;
        boolean isMRefresh = false;
        for (int i = 0; i < stack.length; i++) {
            StackTraceElement ste = stack[i];
            if (ste.getMethodName().equals("pullSyncBookShelf")) {
                hasRefBookShelfInCallStack = true;
            }
            if (ste.getMethodName().equals("onAnimationEnd") && ste.getFileName().equals("SwipeRefreshLayout.java")) {
                isMRefresh = true;
            }
        }

        if (!hasRefBookShelfInCallStack && isMRefresh) {
            pullSyncBookShelf();
            return;
        }


        gone(llBatchManagement);
        visible(recommendRL);
        List<Recommend.RecommendBooks> data = CollectionsManager.getInstance().getCollectionListBySort();

        mAdapter.clear();
        mAdapter.addAll(data);
        //不加下面这句代码会导致，添加本地书籍的时候，部分书籍添加后直接崩溃
        //报错：Scrapped or attached views may not be recycled. isScrap:false isAttached:true
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCollectionList(RefreshCollectionListEvent event) {
        mRecyclerView.setRefreshing(true);
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UserSexChooseFinished(UserSexChooseFinishedEvent event) {
        //首次进入APP，选择性别后，获取推荐列表
        mPresenter.getRecommendList();
    }

    @Override
    public void showError() {
        loaddingError();
        dismissDialog();
    }


    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!getUserVisibleHint()) {
            goneBatchManagementAndRefreshUI();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //这样监听返回键有个缺点就是没有拦截Activity的返回监听，如果有更优方案可以改掉
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (isVisible(llBatchManagement)) {
                        goneBatchManagementAndRefreshUI();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    private boolean isForeground() {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (HomeFragment.class.getName().contains(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.search_rl, R.id.book_admin, R.id.read_book_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_rl:
                SearchActivity.startActivity(getActivity(), "");
                break;
            case R.id.book_admin:
                //批量管理提示
                ShowBatchBookManagement();
                break;
            case R.id.read_book_rl:
                ReadBookHistoryActivity.startActivity(getActivity());
                break;
        }
    }


    CircleDialog.Builder builder = new CircleDialog.Builder();

    /**
     * 批量管理
     */
    boolean isTrue = false;

    public void ShowBatchBookManagement() {
        builder.setPopupItems(adapter, new LinearLayoutManager(getActivity()));
        builder.setPopup(bookAdmin, PopupParams.TRIANGLE_TOP_RIGHT)
                .show(getChildFragmentManager());
    }

    BaseQuickAdapter<NavItemEntity, BaseViewHolder> adapter
            = new BaseQuickAdapter<NavItemEntity, BaseViewHolder>(R.layout.item_rv_icon, list) {
        @Override
        protected void convert(BaseViewHolder helper, NavItemEntity item) {
            helper.setImageResource(R.id.imageView, item.getTextResId())
                    .setText(R.id.textView, item.getName());
        }
    };

}
