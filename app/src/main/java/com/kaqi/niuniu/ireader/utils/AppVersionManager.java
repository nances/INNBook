package com.kaqi.niuniu.ireader.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.kaqi.niuniu.ireader.R;

public class AppVersionManager {

    public static void upgradeApp(Context mContext, String title, String content,
                                  String appSize, String downloadUrl) {

        UIData uiData = UIData.create().setTitle(title)
                .setContent(content).setDownloadUrl(downloadUrl);
//        uiData.getVersionBundle().putString("appSize", appSize);
        AllenVersionChecker.getInstance()
                .downloadOnly(uiData)
                .setCustomVersionDialogListener((context, versionBundle) -> {
                    Dialog versionDialog = new Dialog(
                            context, R.style.dialog);
                    View contentView = LayoutInflater.from(mContext)
                            .inflate(R.layout.dialog_upgrade_app, null);
                    TextView contentTv = contentView.findViewById(R.id.tv_content);
                    TextView titleTv = contentView.findViewById(R.id.tv_title);
                    TextView sizeTv = contentView.findViewById(R.id.tv_size);
                    sizeTv.setText("新版本大小：" + appSize);
                    titleTv.setText(versionBundle.getTitle());
                    contentTv.setText(versionBundle.getContent());
                    versionDialog.setContentView(contentView);
                    return versionDialog;
                })
                .setCustomDownloadingDialogListener(new CustomDownloadingDialogListener() {
                    @Override
                    public Dialog getCustomDownloadingDialog(Context context,
                                                             int progress, UIData versionBundle) {
                        Dialog versionDialog = new Dialog(context, R.style.dialog);
                        View contentView = LayoutInflater.from(mContext)
                                .inflate(R.layout.dialog_download_apk, null);
                        TextView contentTv = contentView.findViewById(R.id.tv_content);
                        TextView titleTv = contentView.findViewById(R.id.tv_title);
                        TextView sizeTv = contentView.findViewById(R.id.tv_size);
                        titleTv.setText(versionBundle.getTitle());
                        contentTv.setText(versionBundle.getContent());
                        sizeTv.setText("新版本大小：" + appSize);
                        versionDialog.setContentView(contentView);
                        return versionDialog;
                    }

                    @Override
                    public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                        ProgressBar progressBar = dialog.findViewById(R.id.downloadProgressBar);
                        progressBar.setProgress(progress);
                    }
                }).executeMission(mContext);
    }
}
