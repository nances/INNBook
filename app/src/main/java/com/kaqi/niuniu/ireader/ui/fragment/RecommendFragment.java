package com.kaqi.niuniu.ireader.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.RxBus;
import com.kaqi.niuniu.ireader.event.DeleteResponseEvent;
import com.kaqi.niuniu.ireader.event.DeleteTaskEvent;
import com.kaqi.niuniu.ireader.event.DownloadMessage;
import com.kaqi.niuniu.ireader.event.RecommendBookEvent;
import com.kaqi.niuniu.ireader.event.RefresRecommendBookEvent;
import com.kaqi.niuniu.ireader.event.TaskShareEvent;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.bean.NavItemEntity;
import com.kaqi.niuniu.ireader.model.local.BookRepository;
import com.kaqi.niuniu.ireader.presenter.BookShelfPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.BookShelfContract;
import com.kaqi.niuniu.ireader.ui.activity.BookCatalogActivity;
import com.kaqi.niuniu.ireader.ui.activity.FileSystemActivity;
import com.kaqi.niuniu.ireader.ui.activity.ReadActivity;
import com.kaqi.niuniu.ireader.ui.activity.ReadBookHistoryActivity;
import com.kaqi.niuniu.ireader.ui.activity.SearchActivity;
import com.kaqi.niuniu.ireader.ui.activity.WifiBookActivity;
import com.kaqi.niuniu.ireader.ui.adapter.CollBookAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPFragment;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.PermissionsChecker;
import com.kaqi.niuniu.ireader.utils.RxUtils;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.widget.adapter.WholeAdapter;
import com.kaqi.niuniu.ireader.widget.dialog.CommomMannagerDialog;
import com.kaqi.niuniu.ireader.widget.recyclerview.adapter.RecyclerArrayAdapter;
import com.kaqi.niuniu.ireader.widget.refresh.ScrollRefreshRecyclerView;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.PopupParams;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RecommendFragment extends BaseMVPFragment<BookShelfContract.Presenter> implements BookShelfContract.View, RecyclerArrayAdapter.OnItemLongClickListener {
    private static final int PERMISSIONS_REQUEST_STORAGE = 1;
    private static final int REQUEST_READ = 2;
    @BindView(R.id.llBatchManagement)
    RelativeLayout llBatchManagement;
    @BindView(R.id.tvSelectAll)
    TextView tvSelectAll;
    @BindView(R.id.tvDelete)
    TextView tvDelete;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    @BindView(R.id.book_admin)
    RelativeLayout bookAdmin;
    @BindView(R.id.recomend_title)
    TextView recomend_title;
    @BindView(R.id.recommendRL)
    RelativeLayout recommendRL;
    @BindView(R.id.read_book_rl)
    RelativeLayout read_book_rl;
    @BindView(R.id.book_shelf_rv_content)
    ScrollRefreshRecyclerView mRvContent;


    private CollBookAdapter mCollBookAdapter;
    private FooterItemView mFooterItem;
    private Typeface impact_tf;
    DialogInterface dialogAdmin;

    List<NavItemEntity> list = new ArrayList<>();
    List<CollBookBean> collBookListBeans = new ArrayList<>();

    static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    PermissionsChecker mPermissionsChecker;
    Class<?> activityCls = null;
    //是否是第一次进入
    private boolean isInit = true;
    public boolean isFirstApp = false;
    private boolean isSelectAll = false;
    private String sex;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    protected BookShelfContract.Presenter bindPresenter() {
        return new BookShelfPresenter();
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        isFirstApp = SharedPreUtils.getInstance()
                .getBoolean(Constant.FITST_APP, false);
        list.add(new NavItemEntity("书架管理", R.drawable.menu_admin));
        list.add(new NavItemEntity("WIFI传书", R.drawable.menu_wifi));
        list.add(new NavItemEntity("本地导入", R.drawable.local_import_book_icon));
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
                } else if (position == 2) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                        if (mPermissionsChecker == null) {
                            mPermissionsChecker = new PermissionsChecker(getActivity());
                        }

                        //获取读取和写入SD卡的权限
                        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                            //请求权限
                            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSIONS_REQUEST_STORAGE);
                        }
                    }

                    activityCls = FileSystemActivity.class;
                    if (activityCls != null) {
                        Intent intent = new Intent(getActivity(), activityCls);
                        startActivity(intent);
                    }
                }
            }
        });

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                dialogAdmin = dialog;
            }
        });
        mRvContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCollBookAdapter.getItemCount() > 0) {
                    mPresenter.updateCollBooks(mCollBookAdapter.getItems());
                } else {
                    mRvContent.finishRefresh();
                }
            }
        });
        deleteteBookEventBus();
        bookRecommend();
        bookDownload();
        refreshSlefBook();

    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpdate();

    }

    /**
     * 显示批量管理布局
     */
    private void showBatchManagementLayout() {
        llBatchManagement.setVisibility(View.VISIBLE);
        recommendRL.setVisibility(View.INVISIBLE);
        for (CollBookBean bean : mCollBookAdapter.getAlldata()) {
            bean.setIsCheckShow(true);
        }
        mCollBookAdapter.notifyDataSetChanged();
    }


    /**
     * 隐藏批量管理布局并刷新页面
     */
    public void goneBatchManagementAndRefreshUI() {
        llBatchManagement.setVisibility(View.GONE);
        recommendRL.setVisibility(View.VISIBLE);
        for (CollBookBean bean : mCollBookAdapter.getAlldata()) {
            bean.setIsCheckShow(false);
        }
        if (collBookListBeans.size() <= 0) {
            showAddBookCenter();
        }
        mCollBookAdapter.notifyDataSetChanged();
    }


    /**
     * 全选
     */
    public void selectAll() {
        isSelectAll = !isSelectAll;
        tvSelectAll.setText(isSelectAll ? getActivity().getString(R.string.cancel_selected_all) : getActivity().getString(R.string.selected_all));
        for (CollBookBean bean : mCollBookAdapter.getAlldata()) {
            bean.setIsCheckBox(isSelectAll);
        }
        mCollBookAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initClick() {
        super.initClick();
        mCollBookAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是本地文件，首先判断这个文件是否存在
                    CollBookBean collBook = mCollBookAdapter.getItem(pos);
                    if (collBook.isLocal()) {
                        //id表示本地文件的路径
                        String path = collBook.getCover();
                        File file = new File(path);
                        //判断这个本地文件是否存在
                        if (file.exists() && file.length() != 0) {
                            ReadActivity.startActivity(getContext(),
                                    mCollBookAdapter.getItem(pos), true);
                        } else {
                            String tip = getContext().getString(R.string.nb_bookshelf_book_not_exist);
                            //提示(从目录中移除这个文件)
                            new AlertDialog.Builder(getContext())
                                    .setTitle(getResources().getString(R.string.nb_common_tip))
                                    .setMessage(tip)
                                    .setPositiveButton(getResources().getString(R.string.nb_common_sure),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    deleteBook(collBook);
                                                }
                                            })
                                    .setNegativeButton(getResources().getString(R.string.nb_common_cancel), null)
                                    .show();
                        }
                    } else {
                        ReadActivity.startActivity(getContext(),
                                mCollBookAdapter.getItem(pos), true);
                    }
                }
        );

        mCollBookAdapter.setOnItemLongClickListener(
                (v, pos) -> {
                    //开启Dialog,最方便的Dialog,就是AlterDialog
                    if (llBatchManagement.getVisibility() == View.VISIBLE) return false;
                    setBookMannager(pos);
                    return true;
                }
        );
    }

    /**
     * 书本弹框管理
     */
    public void setBookMannager(int book_position) {
        final boolean isTop = BookRepository.getInstance().isTop(mCollBookAdapter.getItem(book_position).get_id());
        new CommomMannagerDialog(getActivity(), R.style.dialog, mCollBookAdapter.getItem(book_position), new CommomMannagerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, int type) {
                switch (type) {
                    case 0:
                        //置顶、取消置顶
                        BookRepository.getInstance().top(mCollBookAdapter.getItem(book_position).get_id(), !isTop);
                        break;
                    case 1:
                        downloadBook(mCollBookAdapter.getItem(book_position));
                        break;
                    case 2:
                        //删除
                        deleteBook(mCollBookAdapter.getItem(book_position));
                        break;
                    case 3:
                        //mulu
                        startActivityForResult(new Intent(getContext(), BookCatalogActivity.class)
                                .putExtra(BookCatalogActivity.EXTRA_BOOK_ID, mCollBookAdapter.getItem(book_position).get_id())
                                .putExtra(ReadActivity.EXTRA_IS_COLLECTED, true)
                                .putExtra(ReadActivity.EXTRA_COLL_BOOK, mCollBookAdapter.getItem(book_position)), REQUEST_READ);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        }).setTitle(mCollBookAdapter.getItem(book_position).getTitle()).show();

    }

    /**
     * 删除下载中以及本地
     */
    public void deleteteBookEventBus() {
        Disposable deleteDisp = RxBus.getInstance()
                .toObservable(DeleteResponseEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            if (event.isDelete) {
                                ProgressDialog progressDialog = new ProgressDialog(getContext());
                                progressDialog.setMessage("正在删除中");
                                progressDialog.show();
                                BookRepository.getInstance().deleteCollBookInRx(event.collBook)
                                        .compose(RxUtils::toSimpleSingle)
                                        .subscribe(
                                                (Void) -> {
                                                    mCollBookAdapter.removeItem(event.collBook);
                                                    progressDialog.dismiss();
                                                    if (mCollBookAdapter.getAlldata().size() <= 0) {
                                                        showAddBookCenter();
                                                    }
                                                }
                                        );
                            } else {
                                //弹出一个Dialog
                                AlertDialog tipDialog = new AlertDialog.Builder(getContext())
                                        .setTitle("您的任务正在加载")
                                        .setMessage("先请暂停任务再进行删除")
                                        .setPositiveButton("确定", (dialog, which) -> {
                                            dialog.dismiss();
                                        }).create();
                                tipDialog.show();
                            }
                        }
                );
        addDisposable(deleteDisp);
    }

    /**
     * 下载书本
     */
    public void bookDownload() {

        Disposable donwloadDisp = RxBus.getInstance()
                .toObservable(DownloadMessage.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            //使用Toast提示
                            ToastUtils.show(event.message);
                        }
                );
        addDisposable(donwloadDisp);
    }

    /**
     * 刷新
     */
    public void refreshSlefBook() {
        Disposable donwloadDisp = RxBus.getInstance()
                .toObservable(RefresRecommendBookEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            mCollBookAdapter.refreshItems(BookRepository
                                    .getInstance().getCollBooks());
                        }
                );
        addDisposable(donwloadDisp);
    }

    /**
     * 书本推荐
     */
    public void bookRecommend() {
        //推荐书籍
        Disposable recommendDisp = RxBus.getInstance()
                .toObservable(RecommendBookEvent.class)
                .subscribe(
                        event -> {
                            mRvContent.startRefresh();
                            mPresenter.loadRecommendBooks(event.sex);
                        }
                );
        addDisposable(recommendDisp);

    }

    /**
     * 默认删除本地文件
     *
     * @param collBook
     */
    private void deleteBook(CollBookBean collBook) {
        if (collBook.isLocal()) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_delete, null);
            CheckBox cb = (CheckBox) view.findViewById(R.id.delete_cb_select);
            new AlertDialog.Builder(getContext())
                    .setTitle("删除文件")
                    .setView(view)
                    .setPositiveButton(getResources().getString(R.string.nb_common_sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean isSelected = cb.isSelected();
                            if (isSelected) {
                                ProgressDialog progressDialog = new ProgressDialog(getContext());
                                progressDialog.setMessage("正在删除中");
                                progressDialog.show();
                                //删除
                                File file = new File(collBook.getCover());
                                if (file.exists()) file.delete();
                                BookRepository.getInstance().deleteCollBook(collBook);
                                BookRepository.getInstance().deleteBookRecord(collBook.get_id());

                                //从Adapter中删除
                                mCollBookAdapter.removeItem(collBook);
                                progressDialog.dismiss();
                            } else {
                                BookRepository.getInstance().deleteCollBook(collBook);
                                BookRepository.getInstance().deleteBookRecord(collBook.get_id());
                                //从Adapter中删除
                                mCollBookAdapter.removeItem(collBook);
                            }
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.nb_common_cancel), null)
                    .show();
        } else {
            RxBus.getInstance().post(new DeleteTaskEvent(collBook));
        }
    }


    private void downloadBook(CollBookBean collBook) {
        //创建任务
        mPresenter.createDownloadTask(collBook);
    }

    /**
     * 更新
     */
    private void setUpdate() {
        //添加Footer
        mCollBookAdapter = new CollBookAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(mCollBookAdapter);
    }


    /**
     * 没有数据是显示中间加入书本
     */
    public void showAddBookCenter() {
        if (mCollBookAdapter != null && mFooterItem != null) {
            mCollBookAdapter.removeFooterView(mFooterItem);
            mCollBookAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void showError() {
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        if (isFirstApp) {
            mRvContent.startRefresh();
            mPresenter.loadRecommendBooks(sex);
            SharedPreUtils.getInstance()
                    .putBoolean(Constant.FITST_APP, false);
        }
    }

    @Override
    public void complete() {
        if (mCollBookAdapter.getItemCount() > 0 && mFooterItem == null) {
            mFooterItem = new FooterItemView();
            mCollBookAdapter.addFooterView(mFooterItem);
        }
        if (mRvContent.isRefreshing()) {
            mRvContent.finishRefresh();
        }
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
                    if (llBatchManagement.getVisibility() == View.VISIBLE) {
                        goneBatchManagementAndRefreshUI();
                        return true;
                    }
                }
                return false;
            }
        });

        mPresenter.refreshCollBooks();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.search_rl, R.id.book_admin, R.id.read_book_rl, R.id.tvSelectAll, R.id.tvDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_rl:
                SearchActivity.startActivity(getActivity(), "'");
                break;
            case R.id.book_admin:
                //批量管理提示
                ShowBatchBookManagement();
                break;
            case R.id.read_book_rl:
                ReadBookHistoryActivity.startActivity(getActivity());
                break;
            case R.id.tvSelectAll:
                selectAll();
                break;
            case R.id.tvDelete:
                if (tvDelete.getVisibility() == View.VISIBLE) {
                    for (CollBookBean bean : mCollBookAdapter.getAlldata()) {
                        if (bean.getIsCheckBox()) {
                            deleteBook(bean);
                        }
                    }
                }
                goneBatchManagementAndRefreshUI();

                break;
        }
    }


    /**
     * 批量管理
     */
    CircleDialog.Builder builder = new CircleDialog.Builder();

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

    @Override
    public void finishRefresh(List<CollBookBean> collBookBeans) {
        this.collBookListBeans = collBookBeans;
        if (collBookListBeans.size() <= 0) {
            mRvContent.finishRefresh();
            showAddBookCenter();
        }
        mCollBookAdapter.refreshItems(collBookBeans);
        //如果是初次进入，则更新书籍信息
        if (isInit) {
            isInit = false;
            mRvContent.post(
                    () -> mPresenter.updateCollBooks(mCollBookAdapter.getItems())
            );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_STORAGE: {
                // 如果取消权限，则返回的值为0
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //跳转到 FileSystemActivity
                    Intent intent = new Intent(getActivity(), FileSystemActivity.class);
                    startActivity(intent);

                } else {
                    ToastUtils.show("用户拒绝开启读写权限");
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finishUpdate() {
        //重新从数据库中获取数据
        mCollBookAdapter.refreshItems(BookRepository
                .getInstance().getCollBooks());
    }

    @Override
    public void showErrorTip(String error) {
        ToastUtils.show(error);
    }

    class FooterItemView implements WholeAdapter.ItemView {
        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.footer_book_shelf, parent, false);
            view.setOnClickListener(
                    (v) -> {
                        //设置RxBus回调
                        RxBus.getInstance().post(new TaskShareEvent(2));
                    }
            );
            return view;
        }

        @Override
        public void onBindView(View view) {
        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
