package com.kaqi.reader.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kaqi.reader.R;
import com.kaqi.reader.ReaderApplication;
import com.kaqi.reader.base.BaseActivity;
import com.kaqi.reader.base.Constant;
import com.kaqi.reader.bean.BookMixAToc;
import com.kaqi.reader.bean.BookSource;
import com.kaqi.reader.bean.ChapterRead;
import com.kaqi.reader.bean.Recommend;
import com.kaqi.reader.bean.support.BookMark;
import com.kaqi.reader.bean.support.DownloadMessage;
import com.kaqi.reader.bean.support.DownloadProgress;
import com.kaqi.reader.bean.support.DownloadQueue;
import com.kaqi.reader.bean.support.ReadTheme;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.component.DaggerBookComponent;
import com.kaqi.reader.manager.CacheManager;
import com.kaqi.reader.manager.CollectionsManager;
import com.kaqi.reader.manager.EventManager;
import com.kaqi.reader.manager.HistoryManager;
import com.kaqi.reader.manager.ReadManager;
import com.kaqi.reader.manager.SettingManager;
import com.kaqi.reader.manager.ThemeManager;
import com.kaqi.reader.service.DownloadBookService;
import com.kaqi.reader.ui.adapter.BookMarkAdapter;
import com.kaqi.reader.ui.adapter.TocListAdapter;
import com.kaqi.reader.ui.contract.BookReadContract;
import com.kaqi.reader.ui.easyadapter.ReadFontsAdapter;
import com.kaqi.reader.ui.easyadapter.ReadThemeAdapter;
import com.kaqi.reader.ui.presenter.BookReadPresenter;
import com.kaqi.reader.utils.AppUtils;
import com.kaqi.reader.utils.FormatUtils;
import com.kaqi.reader.utils.LogUtils;
import com.kaqi.reader.utils.ScreenUtils;
import com.kaqi.reader.utils.SharedPreferencesUtil;
import com.kaqi.reader.utils.ToastUtils;
import com.kaqi.reader.view.circleprogress.CircleProgress;
import com.kaqi.reader.view.dialog.CommomAddShuJiaDialog;
import com.kaqi.reader.view.readview.BaseReadView;
import com.kaqi.reader.view.readview.NoAimWidget;
import com.kaqi.reader.view.readview.OnReadStateChangeListener;
import com.kaqi.reader.view.readview.OverlappedWidget;
import com.kaqi.reader.view.readview.PageWidget;
import com.sinovoice.hcicloudsdk.android.tts.player.TTSPlayer;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by niqiao on 2019年04月24日15:11:37.
 */
public class ReadActivity extends BaseActivity implements BookReadContract.View {

    @Bind(R.id.ivBack)
    ImageView mIvBack;
    @Bind(R.id.tvBookReadIntroduce)
    TextView mTvBookReadChangeSource;
    @Bind(R.id.tvBookReadSource)
    TextView mTvBookReadSource;

    @Bind(R.id.flReadWidget)
    FrameLayout flReadWidget;

    @Bind(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;
    @Bind(R.id.tvBookReadTocTitle)
    TextView mTvBookReadTocTitle;
    @Bind(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
    @Bind(R.id.tvBookReadSettings)
    TextView mTvBookReadSettings;
    @Bind(R.id.tvBookReadDownload)
    TextView mTvBookReadDownload;
    @Bind(R.id.tvBookReadToc)
    TextView mTvBookReadToc;
    @Bind(R.id.llBookReadBottom)
    LinearLayout mLlBookReadBottom;
    @Bind(R.id.rlBookReadRoot)
    RelativeLayout mRlBookReadRoot;
    @Bind(R.id.tvDownloadProgress)
    TextView mTvDownloadProgress;

    @Bind(R.id.rlReadAaSet)
    LinearLayout rlReadAaSet;
    @Bind(R.id.ivBrightnessMinus)
    ImageView ivBrightnessMinus;
    @Bind(R.id.seekbarLightness)
    SeekBar seekbarLightness;
    @Bind(R.id.ivBrightnessPlus)
    ImageView ivBrightnessPlus;
    @Bind(R.id.tvFontsizeMinus)
    TextView tvFontsizeMinus;
    @Bind(R.id.seekbarFontSize)
    SeekBar seekbarFontSize;
    @Bind(R.id.tvFontsizePlus)
    TextView tvFontsizePlus;

    @Bind(R.id.rlReadMark)
    LinearLayout rlReadMark;
    @Bind(R.id.tvAddMark)
    TextView tvAddMark;
    @Bind(R.id.lvMark)
    ListView lvMark;

    @Bind(R.id.cbVolume)
    CheckBox cbVolume;
    @Bind(R.id.cbAutoBrightness)
    CheckBox cbAutoBrightness;
    @Bind(R.id.gvTheme)
    GridView gvTheme;
    @Bind(R.id.font_type)
    GridView font_type;
    @Bind(R.id.read_book_get_money_rl)
    RelativeLayout readBookMoneyRl;
    @Bind(R.id.tvClear)
    TextView tvClear;
    @Bind(R.id.tvBookMark)
    TextView tvBookMark;
    @Bind(R.id.read_book_tip_tv)
    TextView readBookTipTv;
    @Bind(R.id.circle_progress)
    CircleProgress circleProgress;


    // 注册 Brightness 的 uri
    private final Uri BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
    private final Uri BRIGHTNESS_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");
    private View decodeView;

    @Inject
    BookReadPresenter mPresenter;

    private List<BookMixAToc.mixToc.Chapters> mChapterList = new ArrayList<>();
    private ListPopupWindow mTocListPopupWindow;
    private TocListAdapter mTocListAdapter;

    private List<BookMark> mMarkList;
    private BookMarkAdapter mMarkAdapter;

    private int currentChapter = 1;
    private int currentNewChapter = 1;

    /**
     * 是否开始阅读章节
     **/
    private boolean startRead = false;

    /**
     * 朗读 播放器
     */
    private TTSPlayer mTtsPlayer;
    private TtsConfig ttsConfig;

    private BaseReadView mPageWidget;
    private int curTheme = -1;
    private int curFont = -1;
    private List<ReadTheme> themes;
    private ReadThemeAdapter gvAdapter;
    private ReadFontsAdapter fontAdapter;
    List<String> Fontlist = new ArrayList<String>();
    private Receiver receiver = new Receiver();
    private IntentFilter intentFilter = new IntentFilter();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public static final String INTENT_BEAN = "recommendBooksBean";
    public static final String CURRENT_CHAPTER = "current_chapter";
    public static final String INTENT_SD = "isFromSD";
    public static final String IS_CATE = "isCata";

    private Recommend.RecommendBooks recommendBooks;
    private String bookId;

    private boolean isAutoLightness = false; // 记录其他页面是否自动调整亮度
    private boolean isFromSD = false;
    private boolean iSCata = false;

    /*****************Animation******************/
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;

    /*****************广告******************/
    public static final int TIME_COUNT_ADD = 0x01;
    public static final int UPDATE_AD_VIDE = 0x02;
    public float ad_count = 1f;

    @Override
    protected void dispatchHandler(Message msg) {
        switch (msg.what) {
            case TIME_COUNT_ADD:
                if (ad_count == 1000) {
                    ad_count = 0;
                    setReadBookMone(ad_count);
                    mHandler.removeCallbacksAndMessages(TIME_COUNT_ADD);
                    mHandler.sendEmptyMessageDelayed(TIME_COUNT_ADD, 200); //开始计费
                }
                ad_count++;
                setReadBookMone(ad_count);
                mHandler.sendEmptyMessageDelayed(TIME_COUNT_ADD, 200); //开始计费
                break;
            case UPDATE_AD_VIDE:
                mPageWidget.setAdRefresh();
                break;
        }
    }

    //添加收藏需要，所以跳转的时候传递整个实体类
    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks) {
        startActivity(context, recommendBooks, false);
    }

    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks, boolean isFromSD) {
        context.startActivity(new Intent(context, ReadActivity.class)
                .putExtra(INTENT_BEAN, recommendBooks)
                .putExtra(INTENT_SD, isFromSD));
    }

    //添加收藏需要，添加外部目录跳转
    public static void startActivity(Context context, Recommend.RecommendBooks recommendBooks, int currentChapter, boolean isCata) {
        context.startActivity(new Intent(context, ReadActivity.class)
                .putExtra(INTENT_BEAN, recommendBooks)
                .putExtra(CURRENT_CHAPTER, currentChapter)
                .putExtra(IS_CATE, isCata));
    }


    @Override
    public int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        statusBarColor = ContextCompat.getColor(this, R.color.reader_menu_bg_color);
        return R.layout.activity_read;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
    }

    @Override
    public void initDatas() {
        recommendBooks = (Recommend.RecommendBooks) getIntent().getSerializableExtra(INTENT_BEAN);
        bookId = recommendBooks._id;
        isFromSD = getIntent().getBooleanExtra(INTENT_SD, false);
        iSCata = getIntent().getBooleanExtra(IS_CATE, false);
        currentNewChapter = getIntent().getIntExtra(CURRENT_CHAPTER, 1);
        getFontFromAssets(); // 获取字体
        EventBus.getDefault().register(this);
        showDialog();
        mTvBookReadTocTitle.setText(recommendBooks.title);

        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);

        CollectionsManager.getInstance().setRecentReadingTime(bookId);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        //延迟1秒刷新书架
                        EventManager.refreshCollectionList();
                    }
                });


    }

    @Override
    public void configViews() {
//        hideStatusBar();
        decodeView = getWindow().getDecorView();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlBookReadTop.getLayoutParams();
//        params.topMargin = ScreenUtils.getStatusBarHeight(this) - 2;
        mLlBookReadTop.setLayoutParams(params);
        initTocList();
        initMenuAnim();
        initAASet();
        initPagerWidget();
        mPresenter.attachView(this);
        mPresenter.getBookMixAToc(bookId, "chapters", iSCata);


        //--- 上次设置过的读小说获取金币状态--- //
        if (ReadManager.getInstance().isReadBookGetMoney() && mPageWidget != null) {
            mPageWidget.pagefactory.setIs_AdStatus(ReadManager.getInstance().isReadBookGetMoney());
            readBookTipTv.setVisibility(View.GONE);
            circleProgress.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessageDelayed(TIME_COUNT_ADD, 1000); //开始广告
        }
    }

    /**
     * 书本目录
     */
    private void initTocList() {
        mTocListAdapter = new TocListAdapter(this, mChapterList, bookId, currentChapter);
        mTocListPopupWindow = new ListPopupWindow(this);
        mTocListPopupWindow.setAdapter(mTocListAdapter);
        mTocListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mTocListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTocListPopupWindow.setAnchorView(mLlBookReadTop);
        mTocListPopupWindow.setModal(true);
        mTocListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPageWidget.pagefactory.is_adShow = 1;
                mTocListPopupWindow.dismiss();
                currentChapter = position + 1;
                mTocListAdapter.setCurrentChapter(currentChapter);
                startRead = false;
                showDialog();
                readCurrentChapter();
                hideReadBar();
            }
        });
        mTocListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                gone(mTvBookReadTocTitle);
                visible(mTvBookReadChangeSource);
            }
        });
    }


    //初始化动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;
        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }

    /**
     * 时刻监听系统亮度改变事件
     */
    private ContentObserver Brightness = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (!ScreenUtils.isAutoBrightness(ReadActivity.this)) {
                seekbarLightness.setProgress(ScreenUtils.getScreenBrightness());
            }

        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI.equals(uri)) {
            } else if (BRIGHTNESS_URI.equals(uri) && !ScreenUtils.isAutoBrightness(ReadActivity.this)) {
                ScreenUtils.setBrightness(ReadActivity.this, ScreenUtils.getScreenBrightness(ReadActivity.this));
            } else if (BRIGHTNESS_ADJ_URI.equals(uri) && ScreenUtils.isAutoBrightness(ReadActivity.this)) {
                ScreenUtils.setDefaultBrightness(ReadActivity.this);
            } else {
            }
        }
    };

    private void initAASet() {
        curTheme = SettingManager.getInstance().getReadTheme();
        curFont = SettingManager.getInstance().getReadFont();
        ThemeManager.setReaderTheme(curTheme, mRlBookReadRoot);

        seekbarFontSize.setMax(10);
        //int fontSizePx = SettingManager.getInstance().getReadFontSize(bookId);
        int fontSizePx = SettingManager.getInstance().getReadFontSize();
        int progress = (int) ((ScreenUtils.pxToDpInt(fontSizePx) - 12) / 1.7f);
        seekbarFontSize.setProgress(progress);
        seekbarFontSize.setOnSeekBarChangeListener(new SeekBarChangeListener());

        seekbarLightness.setMax(100);
        seekbarLightness.setOnSeekBarChangeListener(new SeekBarChangeListener());
        seekbarLightness.setProgress(ScreenUtils.getScreenBrightness());
        isAutoLightness = ScreenUtils.isAutoBrightness(this);

        this.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), true, Brightness);

        if (SettingManager.getInstance().isAutoBrightness()) {
            startAutoLightness();
        } else {
            stopAutoLightness();
        }

        cbVolume.setChecked(SettingManager.getInstance().isVolumeFlipEnable());
        cbVolume.setOnCheckedChangeListener(new ChechBoxChangeListener());

        cbAutoBrightness.setChecked(SettingManager.getInstance().isAutoBrightness());
        cbAutoBrightness.setOnCheckedChangeListener(new ChechBoxChangeListener());

        gvAdapter = new ReadThemeAdapter(this, (themes = ThemeManager.getReaderThemeData(curTheme)), curTheme);
        fontAdapter = new ReadFontsAdapter(this, (Fontlist), curFont);

        font_type.setAdapter(fontAdapter);
        font_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fontAdapter.select(position);
                SettingManager.getInstance().setReadFont(position);
                mPageWidget.setUpdateTextType(position);
            }
        });


        gvTheme.setAdapter(gvAdapter);
        gvTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < themes.size() - 1) {
                    changedMode(false, position);
                } else {
                    changedMode(true, position);
                }
            }
        });
    }

    /**
     * 获取字体
     */
    private void getFontFromAssets() {
        Fontlist.add("默认");
        Fontlist.add("少女");
        Fontlist.add("宋体");
        Fontlist.add("楷书");
        Fontlist.add("幼体");
    }

    private void initPagerWidget() {
        switch (SharedPreferencesUtil.getInstance().getInt(Constant.FLIP_STYLE, 0)) {
            case 0:
                mPageWidget = new PageWidget(this, bookId, mChapterList, new ReadListener());
                break;
            case 1:
                mPageWidget = new OverlappedWidget(this, bookId, mChapterList, new ReadListener());
                break;
            case 2:
                mPageWidget = new NoAimWidget(this, bookId, mChapterList, new ReadListener());
                break;
        }

        registerReceiver(receiver, intentFilter);
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night));
        }
        flReadWidget.removeAllViews();
        flReadWidget.addView(mPageWidget);
    }

    /**
     * 加载章节列表
     *
     * @param list
     */
    @Override
    public void showBookToc(List<BookMixAToc.mixToc.Chapters> list) {
        mChapterList.clear();
        mChapterList.addAll(list);
        readCurrentChapter();
    }

    /**
     * 获取当前章节。章节文件存在则直接阅读，不存在则请求
     */
    public void readCurrentChapter() {
        if (CacheManager.getInstance().getChapterFile(bookId, currentChapter) != null) {
            showChapterRead(null, currentChapter);
        } else {
            mPresenter.getChapterRead(mChapterList.get(currentChapter - 1).link, currentChapter);
        }
    }

    @Override
    public synchronized void showChapterRead(ChapterRead.Chapter data, int chapter) { // 加载章节内容
        if (data != null) {
            CacheManager.getInstance().saveChapterFile(bookId, chapter, data);
        }

        if (!startRead) {
            startRead = true;
            currentChapter = chapter;
            if (!mPageWidget.isPrepared) {
                mPageWidget.init(curTheme);
            } else {
                mPageWidget.jumpToChapter(currentChapter);
            }
            hideDialog();
        }
    }

    @Override
    public void netError(int chapter) {
        hideDialog();//防止因为网络问题而出现dialog不消失
        if (Math.abs(chapter - currentChapter) <= 1) {
            ToastUtils.showToast(R.string.net_error);
        }
    }

    @Override
    public void showError() {
        hideDialog();
    }

    @Override
    public void complete() {
        hideDialog();
    }

    private synchronized void hideReadBar() {
        gone(mTvDownloadProgress, mLlBookReadBottom, mLlBookReadTop, rlReadAaSet, rlReadMark);
        hideStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    private synchronized void showReadBar() { // 显示工具栏
        visible(mLlBookReadBottom, mLlBookReadTop);
        showStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    private synchronized void toggleReadBar() { // 切换工具栏 隐藏/显示 状态
        if (isVisible(mLlBookReadTop)) {
            hideReadBar();
        } else {
            showReadBar();
        }
    }


    private void updateMark() {
        if (mMarkAdapter == null) {
            mMarkAdapter = new BookMarkAdapter(this, new ArrayList<BookMark>());
            lvMark.setAdapter(mMarkAdapter);
            lvMark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BookMark mark = mMarkAdapter.getData(position);
                    if (mark != null) {
                        mPageWidget.setPosition(new int[]{mark.chapter, mark.startPos, mark.endPos});
                        hideReadBar();
                    } else {
                        ToastUtils.showSingleToast("书签无效");
                    }
                }
            });
        }
        mMarkAdapter.clear();

        mMarkList = SettingManager.getInstance().getBookMarks(bookId);
        if (mMarkList != null && mMarkList.size() > 0) {
            Collections.reverse(mMarkList);
            mMarkAdapter.addAll(mMarkList);
        }
    }

    /***************Event*****************/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDownProgress(DownloadProgress progress) {
        if (bookId.equals(progress.bookId)) {
            if (isVisible(mLlBookReadBottom)) { // 如果工具栏显示，则进度条也显示
                visible(mTvDownloadProgress);
                // 如果之前缓存过，就给提示
                mTvDownloadProgress.setText(progress.message);
            } else {
                gone(mTvDownloadProgress);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadMessage(final DownloadMessage msg) {
        if (isVisible(mLlBookReadBottom)) { // 如果工具栏显示，则进度条也显示
            if (bookId.equals(msg.bookId)) {
                visible(mTvDownloadProgress);
                mTvDownloadProgress.setText(msg.message);
                if (msg.isComplete) {
                    mTvDownloadProgress.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gone(mTvDownloadProgress);
                        }
                    }, 2500);
                }
            }
        }
    }

    /**
     * 显示加入书架对话框
     *
     * @param bean
     */
    private void showJoinBookShelfDialog(final Recommend.RecommendBooks bean) {
        new CommomAddShuJiaDialog(mContext, R.style.dialog, getString(R.string.book_read_would_you_like_to_add_this_to_the_book_shelf), new CommomAddShuJiaDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    bean.recentReadingTime = FormatUtils.getCurrentTimeString(FormatUtils.FORMAT_DATE_TIME);
                    CollectionsManager.getInstance().add(bean);
                }
                finish();
            }
        }).setTitle("加入书架").show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    BookSource bookSource = (BookSource) data.getSerializableExtra("source");
                    bookId = bookSource._id;
                }
                //mPresenter.getBookMixAToc(bookId, "chapters");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (!HistoryManager.getInstance().isHistory(bookId)) {
                    HistoryManager.getInstance().add(recommendBooks);
                }
                if (mTocListPopupWindow != null && mTocListPopupWindow.isShowing()) {
                    mTocListPopupWindow.dismiss();
                    gone(mTvBookReadTocTitle);
                    visible(mTvBookReadChangeSource);
                    return true;
                } else if (isVisible(rlReadAaSet)) {
                    gone(rlReadAaSet);
                    return true;
                } else if (isVisible(mLlBookReadBottom)) {
                    hideReadBar();
                    return true;
                } else if (!CollectionsManager.getInstance().isCollected(bookId)) {
                    showJoinBookShelfDialog(recommendBooks);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_MENU:
                toggleReadBar();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (SettingManager.getInstance().isVolumeFlipEnable()) {
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (SettingManager.getInstance().isVolumeFlipEnable()) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                mPageWidget.nextPage();
                return true;// 防止翻页有声音
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                mPageWidget.prePage();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Brightness != null) {
            this.getContentResolver().unregisterContentObserver(Brightness);
        }
        EventManager.refreshCollectionIcon();
        EventManager.refreshCollectionList();
        EventBus.getDefault().unregister(this);

        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            LogUtils.e("Receiver not registered");
        }

//        if (isAutoLightness) {
//            ScreenUtils.startAutoBrightness(ReadActivity.this);
//        } else {
//            ScreenUtils.stopAutoBrightness(ReadActivity.this);
//        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        // 观察内存泄漏情况
        ReaderApplication.getRefWatcher(this).watch(this);
    }

    @OnClick({R.id.read_book_get_money_rl, R.id.ivBack, R.id.tvDownloadProgress, R.id.ivBrightnessMinus, R.id.tvBookReadIntroduce, R.id.tvBookReadSource, R.id.tvBookReadMode, R.id.tvBookReadDownload, R.id.tvBookMark, R.id.tvBookReadToc, R.id.ivBrightnessPlus

            , R.id.tvFontsizeMinus, R.id.tvFontsizePlus, R.id.tvClear, R.id.tvAddMark, R.id.tvBookReadSettings
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.read_book_get_money_rl:
                mPageWidget.pagefactory.is_adShow = 1;
                if (readBookTipTv.getVisibility() == View.VISIBLE) {
                    mPageWidget.pagefactory.is_Ad = true;
                    readBookTipTv.setVisibility(View.GONE);
                    circleProgress.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessageDelayed(TIME_COUNT_ADD, 1000); //开始广告
                    ReadManager.getInstance().saveReadBookGetMoney(true);

                } else {
                    mPageWidget.pagefactory.is_Ad = false;
                    readBookTipTv.setVisibility(View.VISIBLE);
                    circleProgress.setVisibility(View.GONE);
                    ReadManager.getInstance().saveReadBookGetMoney(false);
                }
                mHandler.sendEmptyMessageDelayed(UPDATE_AD_VIDE, 300);
                break;
            case R.id.ivBack:
                if (mTocListPopupWindow != null && mTocListPopupWindow.isShowing()) {
                    mTocListPopupWindow.dismiss();
                    gone(mTvBookReadTocTitle);
                    visible(mTvBookReadChangeSource);
                    return;
                } else {
                    finish();
                }
                break;
            case R.id.tvDownloadProgress:
                break;
            case R.id.ivBrightnessMinus:
                int curBrightness = SettingManager.getInstance().getReadBrightness();
                if (curBrightness > 5 && !SettingManager.getInstance().isAutoBrightness()) {
                    seekbarLightness.setProgress((curBrightness = curBrightness - 2));
                    ScreenUtils.saveScreenBrightnessInt255(curBrightness, ReadActivity.this);
                }
                break;
            case R.id.tvBookReadIntroduce:
                gone(rlReadAaSet, rlReadMark);
                BookDetailActivity.startActivity(mContext, bookId);
                break;
            case R.id.tvBookReadSource:
                BookSourceActivity.start(this, bookId, 1);
                break;
            case R.id.tvBookReadMode:
                gone(rlReadAaSet, rlReadMark);

                boolean isNight = !SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false);
                changedMode(isNight, -1);
                break;
            case R.id.tvBookReadDownload:
                gone(rlReadAaSet);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("缓存多少章？")
                        .setItems(new String[]{"后面五十章", "后面全部", "全部"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        DownloadBookService.post(new DownloadQueue(bookId, mChapterList, currentChapter + 1, currentChapter + 50));
                                        break;
                                    case 1:
                                        DownloadBookService.post(new DownloadQueue(bookId, mChapterList, currentChapter + 1, mChapterList.size()));
                                        break;
                                    case 2:
                                        DownloadBookService.post(new DownloadQueue(bookId, mChapterList, 1, mChapterList.size()));
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                builder.show();

                break;
            case R.id.tvBookMark:
                if (isVisible(mLlBookReadBottom)) {
                    if (isVisible(rlReadMark)) {
                        gone(rlReadMark);
                    } else {
                        gone(rlReadAaSet);
                        updateMark();
                        visible(rlReadMark);
                    }
                }
                break;
            case R.id.tvBookReadToc:
                gone(rlReadAaSet, rlReadMark);
                if (!mTocListPopupWindow.isShowing()) {
                    visible(mTvBookReadTocTitle);
                    gone(mTvBookReadChangeSource);
                    mTocListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    mTocListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    mTocListPopupWindow.show();
                    mTocListPopupWindow.setSelection(currentChapter - 1);
                    mTocListPopupWindow.getListView().setFastScrollEnabled(true);
                }
                break;
            case R.id.ivBrightnessPlus:
                int curBrightnessPlus = SettingManager.getInstance().getReadBrightness();
                if (!SettingManager.getInstance().isAutoBrightness()) {
                    seekbarLightness.setProgress((curBrightnessPlus = curBrightnessPlus + 2));
                    ScreenUtils.saveScreenBrightnessInt255(curBrightnessPlus, ReadActivity.this);
                }
                break;
            case R.id.tvFontsizeMinus:
                calcFontSize(seekbarFontSize.getProgress() - 1);
                break;
            case R.id.tvFontsizePlus:
                calcFontSize(seekbarFontSize.getProgress() + 1);

                break;
            case R.id.tvClear:
                SettingManager.getInstance().clearBookMarks(bookId);

                updateMark();

                break;
            case R.id.tvAddMark:
                int[] readPos = mPageWidget.getReadPos();
                BookMark mark = new BookMark();
                mark.chapter = readPos[0];
                mark.startPos = readPos[1];
                mark.endPos = readPos[2];
                if (mark.chapter >= 1 && mark.chapter <= mChapterList.size()) {
                    mark.title = mChapterList.get(mark.chapter - 1).title;
                }
                mark.desc = mPageWidget.getHeadLine();
                if (SettingManager.getInstance().addBookMark(bookId, mark)) {
                    ToastUtils.showSingleToast("添加书签成功");
                    updateMark();
                } else {
                    ToastUtils.showSingleToast("书签已存在");
                }
                break;
            case R.id.tvBookReadSettings:
                if (isVisible(mLlBookReadBottom)) {
                    if (isVisible(rlReadAaSet)) {
                        gone(rlReadAaSet);
                    } else {
                        visible(rlReadAaSet);
                        gone(rlReadMark);
                    }
                }
                break;

        }
    }

    private void changedMode(boolean isNight, int position) {
        SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, isNight);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);

        if (position >= 0) {
            curTheme = position;
        } else {
            curTheme = SettingManager.getInstance().getReadTheme();
        }
        gvAdapter.select(curTheme);

        mPageWidget.setTheme(isNight ? ThemeManager.NIGHT : curTheme);
        mPageWidget.setTextColor(ContextCompat.getColor(mContext, isNight ? R.color.chapter_content_night : R.color.chapter_content_day),
                ContextCompat.getColor(mContext, isNight ? R.color.chapter_title_night : R.color.chapter_title_day));

        mTvBookReadMode.setText(getString(isNight ? R.string.book_read_mode_day_manual_setting
                : R.string.book_read_mode_night_manual_setting));
        Drawable drawable = ContextCompat.getDrawable(this, isNight ? R.drawable.ic_read_menu_morning
                : R.drawable.ic_read_menu_night);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvBookReadMode.setCompoundDrawables(null, drawable, null, null);

        ThemeManager.setReaderTheme(curTheme, mRlBookReadRoot);
    }


    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter, boolean isNextPage) {
            if (iSCata) {
                currentChapter = currentNewChapter + 1;
            } else {
                currentChapter = chapter;
            }
            LogUtils.i("onChapterChanged:" + currentChapter);
            mTocListAdapter.setCurrentChapter(currentChapter);
            // 加载前一节 与 后三节
            for (int i = chapter - 1; i <= chapter + 2 && i <= mChapterList.size(); i++) {
                if (i > 0 && i != chapter
                        && CacheManager.getInstance().getChapterFile(bookId, i) == null) {
                    mPresenter.getChapterRead(mChapterList.get(i - 1).link, i);
                }
            }
            if (isNextPage && mPageWidget.pagefactory.is_Ad) {
                ad_count++;
            }
        }

        @Override
        public void onPageChanged(int chapter, int page) {
            LogUtils.i("onPageChanged:" + chapter + "-" + page);
            if (isVisible(mLlBookReadTop)) {
                hideReadBar();
            }
        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            LogUtils.i("onLoadChapterFailure:" + chapter);
            startRead = false;
            if (CacheManager.getInstance().getChapterFile(bookId, chapter) == null)
                mPresenter.getChapterRead(mChapterList.get(chapter - 1).link, chapter);
        }

        @Override
        public void onLoadAdChapterChange() {
            hideDialog();
        }

        @Override
        public void onCenterClick() {
            toggleReadBar();
        }

        @Override
        public void onFlip() {
            hideReadBar();
        }
    }

    private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar.getId() == seekbarFontSize.getId() && fromUser) {
                calcFontSize(progress);
            } else if (seekBar.getId() == seekbarLightness.getId() && fromUser
                    && !SettingManager.getInstance().isAutoBrightness()) { // 非自动调节模式下 才可调整屏幕亮度
                ScreenUtils.saveScreenBrightnessInt100(progress, ReadActivity.this);
                //SettingManager.getInstance().saveReadBrightness(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class ChechBoxChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == cbVolume.getId()) {
                SettingManager.getInstance().saveVolumeFlipEnable(isChecked);
            } else if (buttonView.getId() == cbAutoBrightness.getId()) {
                if (isChecked) {
                    startAutoLightness();
                } else {
                    stopAutoLightness();
                    ScreenUtils.saveScreenBrightnessInt255(ScreenUtils.getScreenBrightnessInt255(), AppUtils.getAppContext());
                }
            }
        }
    }

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mPageWidget != null) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int level = intent.getIntExtra("level", 0);
                    mPageWidget.setBattery(100 - level);
                } else if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                    mPageWidget.setTime(sdf.format(new Date()));
                }
            }
        }
    }

    private void startAutoLightness() {
        SettingManager.getInstance().saveAutoBrightness(true);
        ScreenUtils.startAutoBrightness(ReadActivity.this);
        seekbarLightness.setEnabled(false);
    }

    private void stopAutoLightness() {
        SettingManager.getInstance().saveAutoBrightness(false);
        ScreenUtils.stopAutoBrightness(ReadActivity.this);
        seekbarLightness.setProgress((int) (ScreenUtils.getScreenBrightnessInt255() / 255.0F * 100));
        seekbarLightness.setEnabled(true);
    }

    private void calcFontSize(int progress) {
        // progress range 1 - 10
        if (progress >= 0 && progress <= 10) {
            seekbarFontSize.setProgress(progress);
            mPageWidget.setFontSize(ScreenUtils.dpToPxInt(12 + 1.7f * progress));
        }
    }

    /**
     * 设置获取金币进度
     *
     * @param value
     */
    public void setReadBookMone(float value) {
        circleProgress.setValue(value);
    }

}
