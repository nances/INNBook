package com.kaqi.niuniu.ireader.utils;

import java.io.Serializable;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
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
        Wechat.ShareParams params = new Wechat.ShareParams();
        params.setShareType(Wechat.SHARE_WEBPAGE);
        params.setTitle(shareContent.title);
        params.setText(shareContent.text);
        params.setImageUrl(shareContent.imageUrl);
        params.setUrl(shareContent.url);
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        platform.share(params);
    }

    public void shareToWechatMoments() {
        Wechat.ShareParams params = new Wechat.ShareParams();
        params.setShareType(Wechat.SHARE_WEBPAGE);
        params.setTitle(shareContent.title);
        params.setText(shareContent.text);
        params.setImageUrl(shareContent.imageUrl);
        params.setUrl(shareContent.url);
        Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
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
        platform.share(params);
    }

    private void shareToQQ() {
        QQ.ShareParams params = new QQ.ShareParams();
        params.setShareType(QQ.SHARE_WEBPAGE);
        params.setTitle(shareContent.title);
        params.setText(shareContent.text);
        params.setImageUrl(shareContent.imageUrl);
        params.setUrl(shareContent.url);
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
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
