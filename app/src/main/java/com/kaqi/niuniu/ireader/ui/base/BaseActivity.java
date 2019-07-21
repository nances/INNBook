package com.kaqi.niuniu.ireader.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.utils.StatusBarCompat;
import com.yirong.library.manager.NetworkManager;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by PC on 2016/9/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final int INVALID_VAL = -1;
    protected BaseHandler mHandler;
    protected CompositeDisposable mDisposable;
    //ButterKnife
    private Toolbar mToolbar;

    private Unbinder unbinder;

    /****************************abstract area*************************************/

    @LayoutRes
    protected abstract int getContentId();

    /************************init area************************************/
    protected void addDisposable(Disposable d) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     *
     * @param toolbar
     */
    protected void setUpToolbar(Toolbar toolbar) {
    }

    protected void initData(Bundle savedInstanceState) {
    }

    protected void dispatchHandler(Message msg) {

    }

    /**
     * 初始化零件
     */
    protected void initWidget() {

    }

    /**
     * 初始化点击事件
     */
    protected void initClick() {
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic() {
    }

    /*************************lifecycle area*****************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        unbinder = ButterKnife.bind(this);
        mHandler = new BaseHandler(this);
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();

        initData(savedInstanceState);
        //注册广播
        NetworkManager.getDefault().registerObserver(this);
        initToolbar();
        initWidget();
        initClick();
        processLogic();

    }

    private void initToolbar() {
        //更严谨是通过反射判断是否存在Toolbar
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar != null) {
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 非必加
        // 如果你的app可以横竖屏切换，适配了华为emui3系列系统手机，并且navigationBarWithEMUI3Enable为true，
        // 请在onResume方法里添加这句代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、华为emui3系列系统手机；3、navigationBarWithEMUI3Enable为true）
        // 否则请忽略
        if (OSUtils.isEMUI3_x()) {
            ImmersionBar.with(this).init();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        //注销目标广播
        NetworkManager.getDefault().unRegisterObserver(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏

    }

    /**************************used method area*******************************************/

    protected void startActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected ActionBar supportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }

    protected void setStatusBarColor(int statusColor) {
        StatusBarCompat.compat(this, ContextCompat.getColor(this, statusColor));
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
