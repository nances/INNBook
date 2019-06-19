package com.kaqi.reader.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.kaqi.reader.R;
import com.kaqi.reader.ReaderApplication;
import com.kaqi.reader.component.AppComponent;
import com.kaqi.reader.utils.AppManager;
import com.kaqi.reader.utils.SharedPreferencesUtil;
import com.kaqi.reader.utils.StatusBarCompat;
import com.kaqi.reader.view.loadding.CustomDialog;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

//    public Toolbar mCommonToolbar;

    protected Context mContext;
    protected int statusBarColor = 0;
    protected View statusBarView = null;
    private boolean mNowMode;
    private CustomDialog dialog;//进度条
    protected BaseHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        mHandler = new BaseHandler(this);
        ButterKnife.bind(this);
        setupActivityComponent(ReaderApplication.getsInstance().getAppComponent());
        setStatusBar();
        initToolBar();
        initDatas();
        configViews();
        mNowMode = SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT);
    }

    /**
     * 设置状态来，需在setContentView初始化布局之后
     */
    protected void setStatusBar() {
        // 默认着色状态栏，MainActivity需处理多fragment布局，状态栏逻辑在子类中处理。
        SetStatusBarColor();
        StatusBarCompat.setWhiteStatus(this);
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void TranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    protected void SetTranslanteBar() {
        StatusBarCompat.setTransparentStatus(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false) != mNowMode) {
            if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        ButterKnife.unbind(this);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        dismissDialog();
    }

    public abstract int getLayoutId();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public abstract void initToolBar();

    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
        getDialog().show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if(statusBarView != null){
            statusBarView = StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if(statusBarView != null){
            statusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_30));
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    protected void dispatchHandler(Message msg) {

    }

    public static class BaseHandler extends Handler {

        private WeakReference<BaseActivity> reference;

        public BaseHandler(BaseActivity context) {
            reference = new WeakReference<BaseActivity>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = reference.get();
            if (activity != null) {
                activity.dispatchHandler(msg);
            }
        }
    }

}
