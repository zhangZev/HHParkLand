package com.henghao.parkland.service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

public class UpdataBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        SharedPreferences sPreferences = context.getSharedPreferences("downloadcomplete", 0);
        long downloadId = sPreferences.getLong("downloadId", 0);
        if (downloadId == myDwonloadID) {
            String serviceString = Context.DOWNLOAD_SERVICE;
            DownloadManager dManager = (DownloadManager) context.getSystemService(serviceString);
            Intent install = new Intent(Intent.ACTION_VIEW);
            Uri downloadFileUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);
            }
            else {
                Cursor c = dManager.query(new DownloadManager.Query().setFilterById(myDwonloadID));
                c.moveToFirst();
                String str = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                if (str != null) {
                    downloadFileUri = Uri.parse(str);
                }
                else {
                    downloadFileUri = null;
                }
            }
            if (downloadFileUri != null) {
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }
        }
    }
}
