package com.henghao.parkland.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.benefit.buy.library.utils.tools.ToolsDate;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageCropUtils {

    /*
     * 剪切图片
     */
    public static void crop(ActivityFragmentSupport mContext, File mFile, int mPhotoCut, Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(mFile), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 2000);
        intent.putExtra("outputY", 1000);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //        intent.putExtra("output", Uri.parse("file:/" + mFile.getAbsolutePath()));
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        mContext.startActivityForResult(intent, mPhotoCut);
    }

    // 保存到sdcard
    // savePic(getViewBitmap(v), "sdcard/xx.png");
    public static File savePic(Bitmap b) {
        long imageName = ToolsDate.getCurrentTime();
        File file = new File(Constant.CACHE_DIR_PATH + "/" + imageName + ".jpeg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
