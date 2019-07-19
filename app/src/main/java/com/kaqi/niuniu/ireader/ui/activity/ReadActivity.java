package com.kaqi.niuniu.ireader.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.model.bean.BookChapterBean;
import com.kaqi.niuniu.ireader.model.bean.CollBookBean;
import com.kaqi.niuniu.ireader.model.local.BookRepository;
import com.kaqi.niuniu.ireader.model.local.ReadSettingManager;
import com.kaqi.niuniu.ireader.presenter.ReadPresenter;
import com.kaqi.niuniu.ireader.presenter.contract.ReadContract;
import com.kaqi.niuniu.ireader.ui.adapter.CategoryAdapter;
import com.kaqi.niuniu.ireader.ui.base.BaseMVPActivity;
import com.kaqi.niuniu.ireader.ui.dialog.ReadSettingDialog;
import com.kaqi.niuniu.ireader.utils.BrightnessUtils;
import com.kaqi.niuniu.ireader.utils.Constant;
import com.kaqi.niuniu.ireader.utils.LogUtils;
import com.kaqi.niuniu.ireader.utils.NetworkUtils;
import com.kaqi.niuniu.ireader.utils.RxUtils;
import com.kaqi.niuniu.ireader.utils.ScreenUtils;
import com.kaqi.niuniu.ireader.utils.SharedPreUtils;
import com.kaqi.niuniu.ireader.utils.StringUtils;
import com.kaqi.niuniu.ireader.utils.SystemBarUtils;
import com.kaqi.niuniu.ireader.utils.ToastUtils;
import com.kaqi.niuniu.ireader.widget.dialog.CommomAddShuJiaDialog;
import com.kaqi.niuniu.ireader.widget.page.PageLoader;
import com.kaqi.niuniu.ireader.widget.page.PageView;
import com.kaqi.niuniu.ireader.widget.page.TxtChapter;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.yirong.library.annotation.NetType;
import com.yirong.library.annotation.NetworkListener;
import com.yirong.library.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.abdolahi.CircularMusicProgressBar;
import info.abdolahi.OnCircularSeekBarChangeListener;
import io.reactivex.disposables.Disposable;

import static android.support.v4.view.ViewCompat.LAYER_TYPE_SOFTWARE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Niqiao on 2019年06月26日16:46:26.
 */

public class ReadActivity extends BaseMVPActivity<ReadContract.Presenter>
        implements ReadContract.View {
    private static final String TAG = "ReadActivity";
    public static final int REQUEST_MORE_SETTING = 1;
    public static final String EXTRA_COLL_BOOK = "extra_coll_book";
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";

    // 注册 Brightness 的 uri
    private final Uri BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
    private final Uri BRIGHTNESS_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");

    private static final int WHAT_CATEGORY = 2;
    private static final int WHAT_CHAPTER = 3;
    public static final int MSG_ONE = 4;
    public int money_time_sum = 1;


    @BindView(R.id.read_dl_slide)
    DrawerLayout mDlSlide;
    /*************top_menu_view*******************/
    @BindView(R.id.read_abl_top_menu)
    AppBarLayout mAblTopMenu;
    @BindView(R.id.read_tv_brief)
    TextView mTvBrief;
    /***************content_view******************/
    @BindView(R.id.read_pv_page)
    PageView mPvPage;
    /***************bottom_menu_view***************************/
    @BindView(R.id.read_tv_page_tip)
    TextView mTvPageTip;


    @BindView(R.id.read_ll_bottom_menu)
    LinearLayout mLlBottomMenu;
    @BindView(R.id.read_tv_pre_chapter)
    TextView mTvPreChapter;
    @BindView(R.id.read_sb_chapter_progress)
    SeekBar mSbChapterProgress;
    @BindView(R.id.read_tv_next_chapter)
    TextView mTvNextChapter;
    @BindView(R.id.read_tv_category)
    TextView mTvCategory;
    @BindView(R.id.read_tv_night_mode)
    TextView mTvNightMode;
    //    @BindView(R.id.read_tv_download)
//    TextView mTvDownload;
    @BindView(R.id.read_tv_setting)
    TextView mTvSetting;

    @BindView(R.id.money_vp)
    CircularMusicProgressBar money_vp;
    @BindView(R.id.money_vp_rl)
    RelativeLayout money_vp_rl;
    @BindView(R.id.is_money_vp_tv)
    TextView is_money_vp_tv;
    @BindView(R.id.net_tip)
    TextView net_tip;
    /***************left slide*******************************/
    @BindView(R.id.read_iv_category)
    ListView mLvCategory;
    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.book_title)
    TextView bookTitle;
    /*****************view******************/
    private ReadSettingDialog mSettingDialog;
    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private CategoryAdapter mCategoryAdapter;
    private CollBookBean mCollBook;
    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;


    /***************params*****************/
    private boolean isCollected = false; // isFromSDCard
    private boolean isNightMode = false;
    private boolean isFullScreen = false;
    private boolean isRegistered = false;

    private String mBookId;
    public boolean isConnection = false;

    /***************Ad view *****************/
    private View mAdView;
    private NativeExpressAD nativeAd;
    private NativeExpressADView nativeExpressADView;

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case WHAT_CATEGORY:
                mLvCategory.setSelection(mPageLoader.getChapterPos());
                break;
            case WHAT_CHAPTER:
                mPageLoader.openChapter();
                break;
            case MSG_ONE:
                money_time_sum++;
                updateMoneyProgress();
                mHandler.sendEmptyMessageDelayed(MSG_ONE, 1000);
                break;
        }
    }


    public static void startActivity(Context context, CollBookBean collBook, boolean isCollected) {
        context.startActivity(new Intent(context, ReadActivity.class)
                .putExtra(EXTRA_IS_COLLECTED, isCollected)
                .putExtra(EXTRA_COLL_BOOK, collBook));
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_read;
    }

    @Override
    protected ReadContract.Presenter bindPresenter() {
        return new ReadPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mCollBook = getIntent().getParcelableExtra(EXTRA_COLL_BOOK);
        isCollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);
        isNightMode = ReadSettingManager.getInstance().isNightMode();
        isFullScreen = ReadSettingManager.getInstance().isFullScreen();

        mBookId = mCollBook.get_id();
        bookTitle.setText(mCollBook.getTitle());
//        initMenuAnim();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        SystemBarUtils.transparentStatusBar(this);
        // 如果 API < 18 取消硬件加速
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mPvPage.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        //获取页面加载器
        mPageLoader = mPvPage.getPageLoader(mCollBook);
        //禁止滑动展示DrawerLayout
        mDlSlide.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //侧边打开后，返回键能够起作用
        mDlSlide.setFocusableInTouchMode(false);
        mSettingDialog = new ReadSettingDialog(this, mPageLoader);

        setUpAdapter();
        setChangeListner();
        //夜间模式按钮的状态
        toggleNightMode();
        /*** 抓取模式 ***/
        mPageLoader.is_money_vp = SharedPreUtils.getInstance().getBoolean(Constant.MONEY_VP, false);
        if (mPageLoader.is_money_vp && NetworkUtils.isConnected()) {
            is_money_vp_tv.setVisibility(GONE);
            money_vp.setVisibility(VISIBLE);
            mHandler.sendEmptyMessageDelayed(MSG_ONE, 1000);
        } else {
            is_money_vp_tv.setVisibility(VISIBLE);
            money_vp.setVisibility(GONE);
        }
        /*** 抓取模式 ***/
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);

        //设置当前Activity的Brightness
        if (ReadSettingManager.getInstance().isBrightnessAuto()) {
            BrightnessUtils.setDefaultBrightness(this);
        } else {
            BrightnessUtils.setBrightness(this, ReadSettingManager.getInstance().getBrightness());
        }

        //初始化屏幕常亮类
        try {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //隐藏StatusBar
        mPvPage.post(
                () -> hideSystemBar()
        );

        //初始化TopMenu
        initTopMenu();
        //初始化BottomMenu
        initBottomMenu();

        requestAd();
        View coverPageView = LayoutInflater.from(this).inflate(R.layout.layout_cover_view, null, false);
        coverPageView.findViewById(R.id.bt).setOnClickListener(v -> ToastUtils.show("点击了按钮"));
//        mAdView= LayoutInflater.from(this).inflate(R.layout.layout_ad_view, null, false);
        mPvPage.setReaderAdListener(new PageView.ReaderAdListener() {
            @Override
            public View getAdView() {
                return mAdView;
            }

            @Override
            public void onRequestAd() {
                requestAd();
            }

            @Override
            public View getCoverPageView() {
                return coverPageView;
            }
        });
    }

    /**
     * 加载广告
     */
    private void requestAd() {
        NativeExpressAD nativeAd = new NativeExpressAD(this,
                new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT),
                "1101152570", "7030020348049331", new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {
                Log.i("niqiao", "noAd:" + adError.getErrorMsg());
            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                Log.i("niqiao", "loaded ===== ");
                NativeExpressADView nativeExpressADView = list.get(0);
                nativeExpressADView.setId(R.id.nativeExpressAdId);
                nativeExpressADView.render();
                mAdView = nativeExpressADView;
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }
        });
        nativeAd.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS) // 设置什么网络环境下可以自动播放视频
                .setAutoPlayMuted(true) // 设置自动播放视频时，是否静音
                .build()); // setVideoOption是可选的，开发者可根据需要选择是否配置
        nativeAd.loadAD(1);//这里的1是指加载广告的数量
    }

    private void initTopMenu() {
        if (Build.VERSION.SDK_INT >= 19) {
            mAblTopMenu.setPadding(0, ScreenUtils.getStatusBarHeight(), 0, 0);
        }
    }

    private void initBottomMenu() {
        //判断是否全屏
        if (ReadSettingManager.getInstance().isFullScreen()) {
            //还需要设置mBottomMenu的底部高度
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = ScreenUtils.getNavigationBarHeight();
            mLlBottomMenu.setLayoutParams(params);
        } else {
            //设置mBottomMenu的底部距离
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = 0;
            mLlBottomMenu.setLayoutParams(params);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void toggleNightMode() {
        if (isNightMode) {
            mTvNightMode.setText(StringUtils.getString(R.string.nb_mode_morning));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_morning);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        } else {
            mTvNightMode.setText(StringUtils.getString(R.string.nb_mode_night));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_night);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
    }

    /**
     * 目录
     */
    private void setUpAdapter() {
        mCategoryAdapter = new CategoryAdapter();
        mLvCategory.setAdapter(mCategoryAdapter);
        mLvCategory.setFastScrollEnabled(true);
    }


    @Override
    protected void initClick() {
        super.initClick();

        mPageLoader.setOnPageChangeListener(
                new PageLoader.OnPageChangeListener() {

                    @Override
                    public void onChapterChange(int pos) {
                        mCategoryAdapter.setChapter(pos);
                    }

                    @Override
                    public void requestChapters(List<TxtChapter> requestChapters) {
                        mPresenter.loadChapter(mBookId, requestChapters);
                        mHandler.sendEmptyMessage(WHAT_CATEGORY);
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }

                    @Override
                    public void onCategoryFinish(List<TxtChapter> chapters) {
                        for (TxtChapter chapter : chapters) {
                            chapter.setTitle(StringUtils.convertCC(chapter.getTitle(), mPvPage.getContext()));
                        }
                        mCategoryAdapter.refreshItems(chapters);
                    }

                    @Override
                    public void onPageCountChange(int count) {
                        mSbChapterProgress.setMax(Math.max(0, count - 1));
                        mSbChapterProgress.setProgress(0);
                        // 如果处于错误状态，那么就冻结使用
                        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING
                                || mPageLoader.getPageStatus() == PageLoader.STATUS_ERROR) {
                            mSbChapterProgress.setEnabled(false);
                        } else {
                            mSbChapterProgress.setEnabled(true);
                        }
                    }

                    @Override
                    public void onPageChange(int pos) {
                        mSbChapterProgress.post(
                                () -> mSbChapterProgress.setProgress(pos)
                        );
                    }
                }
        );

        mSbChapterProgress.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mLlBottomMenu.getVisibility() == VISIBLE) {
                            //显示标题
                            mTvPageTip.setText((progress + 1) + "/" + (mSbChapterProgress.getMax() + 1));
                            mTvPageTip.setVisibility(VISIBLE);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //进行切换
                        int pagePos = mSbChapterProgress.getProgress();
                        if (pagePos != mPageLoader.getPagePos()) {
                            mPageLoader.skipToPage(pagePos);
                        }
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }
                }
        );

        mPvPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                return !hideReadMenu();
            }

            @Override
            public void center() {
                toggleMenu(true);
            }

            @Override
            public void prePage() {
            }

            @Override
            public void nextPage() {
            }

            @Override
            public void cancel() {
            }
        });

        mLvCategory.setOnItemClickListener(
                (parent, view, position, id) -> {
                    mDlSlide.closeDrawer(Gravity.START);
                    mPageLoader.skipToChapter(position);
                }
        );
        mSettingDialog.setOnDismissListener(
                dialog -> hideSystemBar()
        );

    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        hideSystemBar();
        if (mAblTopMenu.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return true;
        }
        return false;
    }

    private void showSystemBar() {
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {


        if (mAblTopMenu.getVisibility() == VISIBLE) {
            //关闭
//            mAblTopMenu.startAnimation(mTopOutAnim);
//            mLlBottomMenu.startAnimation(mBottomOutAnim);
            mAblTopMenu.setVisibility(GONE);
            mLlBottomMenu.setVisibility(GONE);
            mTvPageTip.setVisibility(GONE);

            if (hideStatusBar) {
                hideSystemBar();
            }
        } else {
            mAblTopMenu.setVisibility(VISIBLE);
            mLlBottomMenu.setVisibility(VISIBLE);
//            mAblTopMenu.startAnimation(mTopInAnim);
//            mLlBottomMenu.startAnimation(mBottomInAnim);

            showSystemBar();
        }
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        //退出的速度要快
        mTopOutAnim.setDuration(50);
        mBottomOutAnim.setDuration(50);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        // 如果是已经收藏的，那么就从数据库中获取目录
        if (isCollected) {
            Disposable disposable = BookRepository.getInstance()
                    .getBookChaptersInRx(mBookId)
                    .compose(RxUtils::toSimpleSingle)
                    .subscribe(
                            (bookChapterBeen, throwable) -> {
                                // 设置 CollBook
                                mPageLoader.getCollBook().setBookChapters(bookChapterBeen);
                                // 刷新章节列表
                                mPageLoader.refreshChapterList();
                                // 如果是网络小说并被标记更新的，则从网络下载目录
                                if (mCollBook.isUpdate() && !mCollBook.isLocal()) {
                                    mPresenter.loadCategory(mBookId);
                                }
                                LogUtils.e(throwable);
                            }
                    );
            addDisposable(disposable);
        } else {
            // 从网络中获取目录
            mPresenter.loadCategory(mBookId);
        }
    }

    /***************************view************************************/
    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showCategory(List<BookChapterBean> bookChapters) {
        mPageLoader.getCollBook().setBookChapters(bookChapters);
        mPageLoader.refreshChapterList();

        // 如果是目录更新的情况，那么就需要存储更新数据
        if (mCollBook.isUpdate() && isCollected) {
            BookRepository.getInstance()
                    .saveBookChaptersWithAsync(bookChapters);
        }
    }

    @Override
    public void finishChapter() {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mHandler.sendEmptyMessage(WHAT_CHAPTER);
        }
        // 当完成章节的时候，刷新列表
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void errorChapter() {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mPageLoader.chapterError();
        }
    }

    @Override
    public void onBackPressed() {
        if (mAblTopMenu.getVisibility() == VISIBLE) {
            // 非全屏下才收缩，全屏下直接退出
            if (!ReadSettingManager.getInstance().isFullScreen()) {
                toggleMenu(true);
                return;
            }
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return;
        } else if (mDlSlide.isDrawerOpen(Gravity.START)) {
            mDlSlide.closeDrawer(Gravity.START);
            return;
        }

        if (!mCollBook.isLocal() && !isCollected
                && !mCollBook.getBookChapters().isEmpty()) {
            showJoinBookShelfDialog();
        } else {
            exit();
        }
    }

    /**
     * 显示加入书架对话框
     */
    private void showJoinBookShelfDialog() {
        new CommomAddShuJiaDialog(this, R.style.dialog, getString(R.string.book_read_would_you_like_to_add_this_to_the_book_shelf), new CommomAddShuJiaDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    //设置为已收藏
                    isCollected = true;
                    //设置阅读时间
                    mCollBook.setIsJoinAddBookSlef(false);
                    mCollBook.setLastRead(StringUtils.
                            dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));

                    BookRepository.getInstance()
                            .saveCollBookWithAsync(mCollBook);
                } else {
                    //表示当前CollBook已经阅读
                    mCollBook.setIsUpdate(false);
                    mCollBook.setIsJoinAddBookSlef(false);
                    mCollBook.setLastRead(StringUtils.
                            dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
                    //直接更新
                    BookRepository.getInstance()
                            .saveCollBook(mCollBook);
                }
                exit();
            }
        }).setTitle("加入书架").show();
    }


    // 退出
    private void exit() {
        // 返回给BookDetail
        Intent result = new Intent();
        result.putExtra(BookDetailActivity.RESULT_IS_COLLECTED, isCollected);
        setResult(Activity.RESULT_OK, result);
        // 退出
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBrightObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPageLoader.is_money_vp) {
            mHandler.sendEmptyMessageDelayed(MSG_ONE, 1000);
        }
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
        if (mPageLoader.is_money_vp) {
            mHandler.removeMessages(MSG_ONE);
        }
        if (isCollected) {
            mPageLoader.saveRecord();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        try {
            unregisterReceiver(mReceiver);
            unregisterBrightObserver();
        } catch (Exception e) {
            LogUtils.e("Receiver not registered");
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        EventBus.getDefault().unregister(this);
        mPageLoader.closeBook();
        mPageLoader = null;
        if (mAdView != null) {
            mAdView = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isVolumeTurnPage = ReadSettingManager
                .getInstance().isVolumeTurnPage();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToPrePage();
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToNextPage();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 金币Progress 监听
     */
    public void setChangeListner() {
        money_vp.setOnCircularBarChangeListener(new OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularMusicProgressBar circularBar, int progress, boolean fromUser) {
                if (progress == 100) {
                    money_time_sum = 0;
                }
            }

            @Override
            public void onClick(CircularMusicProgressBar circularBar) {

            }

            @Override
            public void onLongPress(CircularMusicProgressBar circularBar) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SystemBarUtils.hideStableStatusBar(this);
        if (requestCode == REQUEST_MORE_SETTING) {
            boolean fullScreen = ReadSettingManager.getInstance().isFullScreen();
            if (isFullScreen != fullScreen) {
                isFullScreen = fullScreen;
                // 刷新BottomMenu
                initBottomMenu();
            }

            // 设置显示状态
            if (isFullScreen) {
                SystemBarUtils.hideStableNavBar(this);
            } else {
                SystemBarUtils.showStableNavBar(this);
            }
        }
    }

    // 接收电池信息和时间更新的广播
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mPageLoader.updateBattery(level);
            }
            // 监听分钟的变化
            else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                mPageLoader.updateTime();
            }
        }
    };

    // 亮度调节监听
    // 由于亮度调节没有 Broadcast 而是直接修改 ContentProvider 的。所以需要创建一个 Observer 来监听 ContentProvider 的变化情况。
    private ContentObserver mBrightObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            // 判断当前是否跟随屏幕亮度，如果不是则返回
            if (selfChange || !mSettingDialog.isBrightFollowSystem()) return;

            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI.equals(uri)) {
            } else if (BRIGHTNESS_URI.equals(uri) && !BrightnessUtils.isAutoBrightness(ReadActivity.this)) {
                BrightnessUtils.setBrightness(ReadActivity.this, BrightnessUtils.getScreenBrightness(ReadActivity.this));
            } else if (BRIGHTNESS_ADJ_URI.equals(uri) && BrightnessUtils.isAutoBrightness(ReadActivity.this)) {
                BrightnessUtils.setDefaultBrightness(ReadActivity.this);
            } else {
            }
        }
    };


    // 注册亮度观察者
    private void registerBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (!isRegistered) {
                    final ContentResolver cr = getContentResolver();
                    cr.unregisterContentObserver(mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_MODE_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_ADJ_URI, false, mBrightObserver);
                    isRegistered = true;
                }
            }
        } catch (Throwable throwable) {
            LogUtils.e(TAG, "register mBrightObserver error! " + throwable);
        }
    }

    //解注册
    private void unregisterBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (isRegistered) {
                    getContentResolver().unregisterContentObserver(mBrightObserver);
                    isRegistered = false;
                }
            }
        } catch (Throwable throwable) {
            LogUtils.e(TAG, "unregister BrightnessObserver error! " + throwable);
        }
    }


    @OnClick({R.id.read_tv_pre_chapter, R.id.read_sb_chapter_progress, R.id.read_tv_next_chapter, R.id.read_tv_category, R.id.read_tv_night_mode, R.id.read_tv_setting, R.id.read_ll_bottom_menu, R.id.read_tv_brief, R.id.money_vp_rl, R.id.back_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.read_tv_pre_chapter:
                if (mPageLoader.skipPreChapter()) {
                    mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                }
                break;
            case R.id.read_sb_chapter_progress:
                break;
            case R.id.read_tv_next_chapter:
                if (mPageLoader.skipNextChapter()) {
                    mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                }
                break;
            case R.id.read_tv_category:
                //移动到指定位置
                if (mCategoryAdapter.getCount() > 0) {
                    mLvCategory.setSelection(mPageLoader.getChapterPos());
                }
                //切换菜单
                toggleMenu(true);
                //打开侧滑动栏
                mDlSlide.openDrawer(Gravity.START);
                break;
            case R.id.read_tv_night_mode:
                if (isNightMode) {
                    isNightMode = false;
                } else {
                    isNightMode = true;
                }
                mPageLoader.setNightMode(isNightMode);
                toggleNightMode();
                break;
            case R.id.read_tv_setting:
                toggleMenu(false);
                mSettingDialog.show();
                break;
            case R.id.read_ll_bottom_menu:
                break;
            case R.id.read_tv_brief:
                BookDetailActivity.startActivity(this, mBookId);
                break;
            case R.id.money_vp_rl:
                if (mPageLoader.is_money_vp) {
                    is_money_vp_tv.setVisibility(VISIBLE);
                    money_vp.setVisibility(GONE);
                    SharedPreUtils.getInstance().putBoolean(Constant.MONEY_VP, false);
                    mPageLoader.is_money_vp = false;
                    ToastUtils.show("已关闭赚钱模式");
                    net_tip.setVisibility(GONE);
                    mHandler.removeMessages(MSG_ONE);
                    money_time_sum = 0;
                } else {
                    is_money_vp_tv.setVisibility(GONE);
                    money_vp.setVisibility(VISIBLE);
                    ToastUtils.show("已开启赚钱魔模式");
                    mPageLoader.is_money_vp = true;
                    SharedPreUtils.getInstance().putBoolean(Constant.MONEY_VP, true);
                    mHandler.sendEmptyMessageDelayed(MSG_ONE, 1000);
                }
                break;
            case R.id.back_rl:
                finish();
                break;
        }
    }

    //网络监听
    @NetworkListener(type = NetType.WIFI)
    public void netork(@NetType String type) {
        switch (type) {
            case NetType.AUTO:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "AUTO");
                break;
            case NetType.CMNET:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "CMNET");
                break;
            case NetType.CMWAP:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "CMWAP");
                break;
            case NetType.WIFI:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "WIFI");
                break;
            case NetType.NONE:
                Log.i(Constants.TAG, "NONE");
                resumeReadBookVpMoney(false);
                break;
        }
    }


    //网络监听
    @NetworkListener(type = NetType.WIFI)
    public void netorkListen(@NetType String type) {
        switch (type) {
            case NetType.AUTO:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "AUTO*");
                break;
            case NetType.CMNET:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "CMNET*");
                break;
            case NetType.CMWAP:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "CMWAP*");
                break;
            case NetType.WIFI:
                resumeReadBookVpMoney(true);
                Log.i(Constants.TAG, "WIFI*");
                break;
            case NetType.NONE:
                resumeReadBookVpMoney(false);
                Log.i(Constants.TAG, "NONE*");
                break;
        }
    }

    /**
     * 网络监听变化 阅读赚钱模式
     */
    public void resumeReadBookVpMoney(boolean isConnection) {
        mHandler.removeMessages(MSG_ONE);
        this.isConnection = isConnection;
        if (mPageLoader.is_money_vp && isConnection) {
            //开启
            net_tip.setVisibility(GONE);
            mHandler.sendEmptyMessageDelayed(MSG_ONE, 1000);
        } else {
            //关闭
            net_tip.setVisibility(VISIBLE);
            mHandler.removeMessages(MSG_ONE);
        }

    }

    /**
     * 刷新金币计时器
     */
    public void updateMoneyProgress() {
        money_vp.setValue(money_time_sum * 1f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
