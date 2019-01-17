package com.huanxi.renrentoutiao.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

class WechatTPNative {
    private Context activity;

    private String packageName, appId;
    private int sdkVersion = 553910273;

    WechatTPNative(Context a) {
        activity = a;

        String[] packageNames = new String[]{"com.tencent.mobileqq", "com.tencent.mtt", "com.tencent.qqmusic"};
        String[] appIds = new String[]{"wxf0a80d0ac2e82aa7", "wx64f9cf5b17af074d", "wx5aa333606550dfd5"};
        for (int i = 0; i < packageNames.length; i++) {
            if (Utils.isAppInstalled(activity, packageNames[i])) {
                packageName = packageNames[i];
                appId = appIds[i];
                break;
            }
        }

        if (!TextUtils.isEmpty(appId)) {
            String wxClass = "com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_REGISTER";
            String wxScheme = "weixin://registerapp?appid=" + appId;
            String wxPermission = "com.tencent.mm.permission.MM_MESSAGE";
            Intent intent = new Intent(wxClass);
            intent.putExtra("_mmessage_sdkVersion", sdkVersion);
            intent.putExtra("_mmessage_appPackage", packageName);
            intent.putExtra("_mmessage_content", wxScheme);
            intent.putExtra("_mmessage_checksum", checkSum(wxScheme, sdkVersion, packageName));
            activity.sendBroadcast(intent, wxPermission);
        }
    }

    void sendReq(Bundle bundle) {
        bundle.putInt("_wxobject_sdkVer", sdkVersion);
        bundle.putString("_wxapi_basereq_transaction", String.valueOf(System.currentTimeMillis()));

        String wxScheme = "weixin://sendreq?appid=" + appId;

        Intent intent = new Intent();
        intent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
        intent.putExtras(bundle);
        intent.putExtra("_mmessage_sdkVersion", sdkVersion);
        intent.putExtra("_mmessage_appPackage", packageName);
        intent.putExtra("_mmessage_content", wxScheme);
        intent.putExtra("_mmessage_checksum", checkSum(wxScheme, sdkVersion, packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {
            activity.startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    boolean isTPExist() {
        if (TextUtils.isEmpty(appId)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setTitle("分享失败");
            dialog.setMessage("您没有安装所需的分享工具,无法分享到微信渠道！请点击马上安装按钮，下载并安装QQ浏览器！");
            dialog.setPositiveButton("马上安装", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri downUri = Uri.parse("http://app4huoniu.applinzi.com/qbd.php?platform=android");
                    Intent intent = new Intent(Intent.ACTION_VIEW, downUri);
                    activity.startActivity(intent);
                    dialogInterface.dismiss();
                }
            });
            dialog.setNegativeButton("放弃分享", null);
            dialog.show();
            return false;
        } else {
            return true;
        }
    }

    private byte[] checkSum(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
        }
        sb.append(i);
        sb.append(str2);
        sb.append("mMcShCsTr");
        return Utils.getHash(sb.toString().substring(1, 9), Utils.HASH_ALGORITHM.MD5).getBytes();
    }
}
