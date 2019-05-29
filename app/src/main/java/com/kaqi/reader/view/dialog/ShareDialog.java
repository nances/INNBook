package com.kaqi.reader.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kaqi.reader.R;
import com.kaqi.reader.utils.ShareUtils;

import cn.sharesdk.framework.PlatformActionListener;

public class ShareDialog {


    public static void showDialog(Context mContext, ShareUtils.ShareContentBean shareContent,
                                  PlatformActionListener mListener) {
        ShareUtils shareUtils = new ShareUtils(shareContent, mListener);
        Dialog dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_share, null);

        LinearLayout wechatMomentsBtn = contentView.findViewById(R.id.wechat_moments_btn);
        LinearLayout sinaBtn = contentView.findViewById(R.id.sina_btn);
        LinearLayout wechatBtn = contentView.findViewById(R.id.wechat_btn);
        Button cancelBtn = contentView.findViewById(R.id.cancel_btn);
        wechatMomentsBtn.setOnClickListener(view -> {
            shareUtils.shareToWechatMoments();
        });
        wechatBtn.setOnClickListener(view -> {
            shareUtils.shareToWechat();
        });
        sinaBtn.setOnClickListener(view -> {
            shareUtils.shareToSinaWeibo();
        });
        cancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        dialog.show();
    }
}
