package com.xiaoguang.happytoplay.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

/**
 * 选择图片的工具类
 * 分为拍照和从图库中选择
 * Created by 11655 on 2016/10/5.
 */

public class ImageChooseUtils {

    /**
     * 图片的Uri
     */
    private static Uri imageUri;

    /**
     * 调用系统中的相机进行拍照
     */
    public static Intent takePhoto() {
        //初始化图片的Uri
        imageUri = getImageUri();
        if (isHasSdcard()) {//判断SD卡的状态
            Intent takePhotoIntent = new Intent(
                    "android.media.action.IMAGE_CAPTURE");
            takePhotoIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    imageUri);
            return takePhotoIntent;
        }

        ToastUtils.toastShort("未检测到sd卡");
        return null;
    }

    /**
     * 从系统图库中选择照片
     */
    public static Intent choosePhoto() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * 获取图片的路径
     */
    public static Uri getImageUri() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "icoImage.jpg");
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(outputImage);
    }

    ;

    /**
     * 判断sd卡是否存在
     */
    public static boolean isHasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
