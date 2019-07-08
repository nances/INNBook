package com.kaqi.niuniu.ireader.utils;

import android.content.Context;
import android.util.DisplayMetrics;
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

    private AdProvider() {}

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

    public FrameLayout getAdContainer(Context context, AdConfigBean configBean, int top, int padding) {
        FrameLayout adContainer = new FrameLayout(context);
        adContainer.setPadding(0,0,0, 0);
//        adContainer.setBackground(context.getDrawable(R.drawable.meinv));

        AdConfigBean.Property property = configBean.getProperty(configBean.getType());
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        adContainer.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY)
                , View.MeasureSpec.makeMeasureSpec(property.height, View.MeasureSpec.EXACTLY));
        adContainer.layout(0, top - padding / 2, displayMetrics.widthPixels, property.height + top - padding / 2 - padding);

        return adContainer;
    }
}
