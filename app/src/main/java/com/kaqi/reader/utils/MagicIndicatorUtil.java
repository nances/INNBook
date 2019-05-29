package com.kaqi.reader.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.kaqi.reader.R;
import com.kaqi.reader.view.magicindicator.BoldPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.List;

public class MagicIndicatorUtil {
    public static void init(final Context mContext, MagicIndicator magicIndicator,
                            final ViewPager mViewPager, final List<String> mTitleList) {
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setScrollPivotX(0.65f);
        // ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdjustMode(false);
        Log.v("Nancys","mTitls list is value : " + mTitleList.size());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList != null ? mTitleList.size() : 0;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                BoldPagerTitleView titleView = new BoldPagerTitleView(mContext);
                titleView.setNormalSize(18);
                titleView.setSelectedSize(22);
                titleView.setNormalColor(ContextCompat.getColor(mContext, R.color.common_h1));
                titleView.setSelectedColor(ContextCompat.getColor(mContext, R.color.yellow_50));
                titleView.setText(mTitleList.get(i));
                titleView.setOnClickListener(v -> mViewPager.setCurrentItem(i));
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(mContext);
                indicator.setMode(LinePagerIndicator.NOT_FOCUSABLE);
                indicator.setLineHeight(ScreenUtils.dpToPxInt(4f));
                indicator.setLineWidth(ScreenUtils.dpToPxInt(20f));
                indicator.setRoundRadius(ScreenUtils.dpToPxInt(2f));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEnabled(false);
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(mContext, R.color.yellow_50));
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
