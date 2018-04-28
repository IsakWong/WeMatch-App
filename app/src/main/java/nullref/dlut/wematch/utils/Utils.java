package nullref.dlut.wematch.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ScrollView;

import com.nineoldandroids.animation.ValueAnimator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IsakWong on 2017/7/1.
 */

public class Utils {


    private static final float BITMAP_SCALE = 0.4f;

    public static void smoothScroll(final ScrollView scroll, int Y, int duration) {

        ValueAnimator intAnimator = ValueAnimator.ofInt(scroll.getScrollY(), Y);

        intAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                          @Override
                                          public void onAnimationUpdate(ValueAnimator animation) {
                                              Integer animatedValue = (Integer) ((ValueAnimator) animation).getAnimatedValue();
                                              if (scroll != null) {
                                                  scroll.scrollTo(0, animatedValue);
                                              }
                                          }
                                      }
        );
        intAnimator.setDuration(duration);
        intAnimator.start();
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            Log.e("error", "toURLEncoded error:" + paramString);
        }

        return "";
    }

    public static String toURLDecoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        } catch (Exception localException) {

        }

        return "";
    }

    /**
     * 将字符串转换为MD5码
     *
     * @param string
     * @return 返回MD5码
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Bitmap scaleBitmap(Bitmap bitmap,int width,int height){
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
        return scaledBitmap;
    }
    /**
     * bitmap转为base64
     *
     * @param bitmap 被转换的位图
     * @return 返回jpg格式的base64字符串
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64&#x8f6c;&#x4e3a;bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 模糊图片的具体方法
     *
     * @param context 上下文对象
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {

        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);

        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        blurScript.setRadius(blurRadius);

        blurScript.setInput(tmpIn);

        blurScript.forEach(tmpOut);

        tmpOut.copyTo(outputBitmap);
        return outputBitmap;


    }

    public static long getTimeStamp(String timeStr) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.mm.dd.hh.mm", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(timeStr);
            long l = date.getTime();
            return l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
