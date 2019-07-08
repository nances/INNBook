package com.kaqi.niuniu.ireader.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaqi.niuniu.ireader.R;
import com.kaqi.niuniu.ireader.view.banner.BannerEntity;
import com.kaqi.niuniu.ireader.view.banner.holder.MZViewHolder;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.List;

/**
 * des:首页Banner
 * Created by niqiao
 * on 2017年06月08日13:25:46
 */
public class ViewPagerHolder implements MZViewHolder<BannerEntity> {
    private ImageView mImageView;
    List<BannerEntity> bannerInfoBeanList;

    @Override
    public View createView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.normal_banner_item, null);
        mImageView = (ImageView) view.findViewById(R.id.normal_banner_image);
        return view;
    }

    public void setData(List<BannerEntity> bannerInfoBeanList) {
        this.bannerInfoBeanList = bannerInfoBeanList;
    }

    public List<BannerEntity> getData() {
        return this.bannerInfoBeanList;
    }

    @Override
    public void onBind(final Context context, final int position, final BannerEntity data) {
        if (context == null) {
            return;
        }
        Glide.with(context).load(data.img)
                .placeholder(R.drawable.bannler_defalue_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.bannler_defalue_icon)
                .transform(new GlideRoundTransform(context, 6))
                .into(mImageView);

    }

}
