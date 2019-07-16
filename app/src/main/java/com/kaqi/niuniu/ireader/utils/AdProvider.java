package com.kaqi.niuniu.ireader.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.kaqi.niuniu.ireader.model.bean.AdConfigBean;

public class AdProvider {

    private static volatile AdProvider instance;

    public static AdProvider getInstance() {
        if (instance == null) {
            synchronized (AdProvider.class) {
                if (instance == null) {
                    instance = new AdProvider();
                }
            }
        }
        return instance;
    }

    private AdProvider() {
    }

    public AdConfigBean getAdConfig(int pagePosition) {
        if (pagePosition % 2 == 0) {
            AdConfigBean adConfigBean = new AdConfigBean();
            return adConfigBean;
        }
        return null;
    }

    //    public void initAdProperty(AdConfigBean adConfigBean, ) {
//
//    }
    FrameLayout adContainer;
    FrameLayout.LayoutParams params;

    /**
     * 初始化
     *
     * @param context
     */
    public void initAdContainer(Context context) {
        adContainer = new FrameLayout(context);
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public FrameLayout getAdContainer(Context context, AdConfigBean configBean, int top, int padding, View adView) {
        UtilsView.removeParent(adView);
        FrameLayout adContainer = new FrameLayout(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        adContainer.setLayoutParams(params);
        adContainer.setDrawingCacheEnabled(true);
        adContainer.setPadding(0, padding, 0, padding);
        adContainer.addView(adView);
        Log.v("NancysTVid", "========= ad view +=======" + adView);
        AdConfigBean.Property property = configBean.getProperty(configBean.getType());
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        adContainer.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY)
                , View.MeasureSpec.makeMeasureSpec(property.height, View.MeasureSpec.EXACTLY));
        adContainer.layout(0, top, displayMetrics.widthPixels, property.height + top);
        return adContainer;
    }
}
