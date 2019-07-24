package com.kaqi.niuniu.ireader.utils;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareUtils {

    private ShareContentBean shareContent;
    private PlatformActionListener mListener;

    public ShareUtils(ShareContentBean shareContent, PlatformActionListener mListener) {
        this.shareContent = shareContent;
        this.mListener = mListener;
    }


    public void shareToWechat() {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        //微信分享网页的参数严格对照列表中微信分享网页的参数要求
        sp.setTitle(shareContent.title);
        sp.setText(shareContent.text);
        /*Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        sp.setImageData(imageData);*/
        sp.setImageUrl(shareContent.imageUrl);
        sp.setUrl(shareContent.url);
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Log.d("ShareSDK", sp.toMap().toString());
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareSDK", "onComplete ---->  分享成功");
                mListener.onCancel(platform,i);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                mListener.onCancel(platform,i);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                mListener.onCancel(platform,i);
                Log.d("ShareSDK", "onCancel ---->  分享取消");
            }
        });
        // 执行图文分享
        wechat.share(sp);

    }

    public void shareToWechatMoments() {
        Wechat.ShareParams params = new Wechat.ShareParams();
        params.setShareType(Wechat.SHARE_WEBPAGE);
        params.setTitle(shareContent.title);
        params.setText(shareContent.text);
        params.setImageUrl(shareContent.imageUrl);
        params.setUrl(shareContent.url);
        Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareSDK", "onComplete ---->  分享成功");
                mListener.onCancel(platform,i);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                mListener.onCancel(platform,i);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                mListener.onCancel(platform,i);
                Log.d("ShareSDK", "onCancel ---->  分享取消");
            }
        });
        platform.share(params);
    }

    public void shareToSinaWeibo() {
        SinaWeibo.ShareParams params = new SinaWeibo.ShareParams();
        params.setShareType(SinaWeibo.SHARE_WEBPAGE);
        params.setTitle(shareContent.title);
        params.setText(shareContent.text);
        params.setImageUrl(shareContent.imageUrl);
        params.setUrl(shareContent.url);
        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareSDK", "onComplete ---->  分享成功");
                mListener.onCancel(platform,i);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                mListener.onCancel(platform,i);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                mListener.onCancel(platform,i);
                Log.d("ShareSDK", "onCancel ---->  分享取消");
            }
        });
        platform.share(params);
    }

    public void shareToQQ() {
        QQ.ShareParams params = new QQ.ShareParams();
        params.setShareType(QQ.SHARE_WEBPAGE);
        params.setTitle(shareContent.title);
        params.setText(shareContent.text);
        params.setImageUrl(shareContent.imageUrl);
        params.setUrl(shareContent.url);
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareSDK", "onComplete ---->  分享成功");
                mListener.onCancel(platform,i);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                mListener.onCancel(platform,i);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                mListener.onCancel(platform,i);
                Log.d("ShareSDK", "onCancel ---->  分享取消");
            }
        });
        platform.share(params);
    }

    public void shareToQQZone() {
        QZone.ShareParams params = new QZone.ShareParams();
        params.setShareType(QZone.SHARE_WEBPAGE);
        params.setTitle(shareContent.title);
        params.setText(shareContent.text);
        params.setImageUrl(shareContent.imageUrl);
        params.setUrl(shareContent.url);
        Platform platform = ShareSDK.getPlatform(QZone.NAME);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("ShareSDK", "onComplete ---->  分享成功");
                mListener.onCancel(platform,i);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
                mListener.onCancel(platform,i);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                mListener.onCancel(platform,i);
                Log.d("ShareSDK", "onCancel ---->  分享取消");
            }
        });
        platform.share(params);

    }


    public static class ShareContentBean implements Serializable {
        public String title;
        public String text;
        public String imageUrl;
        public String url;

        public ShareContentBean(String title, String text, String imageUrl, String url) {
            this.title = title;
            this.text = text;
            this.imageUrl = imageUrl;
            this.url = url;
        }
    }
}
