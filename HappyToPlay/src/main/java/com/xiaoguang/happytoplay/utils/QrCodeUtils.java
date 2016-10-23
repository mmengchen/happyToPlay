package com.xiaoguang.happytoplay.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.xiaoguang.happytoplay.bean.User;

import java.util.Hashtable;

/**
 * 根据用户信息生成二维码
 * Created by 11655 on 2016/10/12.
 */

public class QrCodeUtils {
    public static final int QR_WIDTH = 80;
    public static final int QR_HEIGHT = 80;
    public  static Bitmap createQrCode(User user){
        Bitmap bitmap = null;

        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();
            String text = "乐玩旅行APP 用户信息:  "+"   昵称："+user.getNickName()+" 账号："+user.getUsername();
            if (text == null || "".equals(text) || text.length() < 1) {
                return null;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:"
                    + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
    // 解析QR图片
//    public static void scanningImage() {
//
//        Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
//        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
//
//        // 获得待解析的图片
//        Bitmap bitmap = ((BitmapDrawable) qr_image.getDrawable()).getBitmap();
//        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
//        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//        QRCodeReader reader = new QRCodeReader();
//        Result result;
//        try {
//            result = reader.decode(bitmap1, hints);
//            // 得到解析后的文字
//            qr_result.setText(result.getText());
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (ChecksumException e) {
//            e.printStackTrace();
//        } catch (FormatException e) {
//            e.printStackTrace();
//        }
//    }
}
