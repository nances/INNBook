package com.kaqi.niuniu.ireader.widget.page;

import android.view.View;

import com.kaqi.niuniu.ireader.model.bean.AdConfigBean;

import java.util.List;

/**
 * Created by newbiechen on 17-7-1.
 */

public class TxtPage {
    int position;
    String title;
    int titleLines; //当前 lines 中为 title 的行数。
    List<String> lines;
    boolean isCustomView;//标记当前页是否是自填充view
    boolean hasDrawAd;//标记是否已经添加过广告
    String pageType = VALUE_STRING_AD_TYPE;//标记是封面view还是广告view
    public View adView = null;
    public AdConfigBean adConfigBean;

    public boolean hasAd() {
        return adConfigBean != null;
    }

    public static final String VALUE_STRING_COVER_TYPE = "cover", VALUE_STRING_AD_TYPE = "ad";
}
