package com.wam.zgame.jff.warriorandmonster.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片操作相关, 像素与dp, sp的互相转换
 */
public class B {

    private static Map<String, Bitmap> bitmaps = new HashMap<>();

    public static void close() {
        bitmaps.clear();
    }


    /**
     * 获取圆形图片方法
     *
     * @param bitmap_src
     * @return Bitmap
     * @author caizhiming
     */
    public static Bitmap getBitmap_circle(Bitmap bitmap_src) {
        if (bitmap_src == null) {
            return null;
        }
        Paint paint = new Paint();
        int w_bitmap = bitmap_src.getWidth();
        int h_bitmap = bitmap_src.getHeight();
        int doubleRadius = w_bitmap;
        if (w_bitmap > h_bitmap) {
            doubleRadius = h_bitmap;
        }
        Bitmap bitmap_des = Bitmap.createBitmap(w_bitmap, h_bitmap, Bitmap.Config.ARGB_8888);
        Canvas canvas_des = new Canvas(bitmap_des);
        final Rect rect = new Rect(0, 0, bitmap_src.getWidth(), bitmap_src.getHeight());
        paint.setAntiAlias(true);
        canvas_des.drawARGB(0, 0, 0, 0);

        canvas_des.drawCircle(w_bitmap / 2, h_bitmap / 2, doubleRadius / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas_des.drawBitmap(bitmap_src, rect, rect, paint);

        //二次裁剪把图片裁剪为一个正方形
        Bitmap bitmap_des_final = Bitmap.createBitmap(bitmap_des, (w_bitmap - doubleRadius) / 2, (h_bitmap - doubleRadius) / 2, doubleRadius, doubleRadius);
        bitmap_des.recycle();
        return bitmap_des_final;
    }

    /**
     * 产生一个圆形带描边的图片
     *
     * @param bitmap_src
     * @return
     */
    public static Bitmap getBitmap_circle_addBorder(Bitmap bitmap_src) {
        if (bitmap_src == null) {
            return null;
        }
        Paint paint = new Paint();
        int w_bitmap = bitmap_src.getWidth();
        int h_bitmap = bitmap_src.getHeight();
        int doubleRadius = w_bitmap;
        if (w_bitmap > h_bitmap) {
            doubleRadius = h_bitmap;
        }
        Bitmap bitmap_des = Bitmap.createBitmap(w_bitmap, h_bitmap, Bitmap.Config.ARGB_8888);
        Canvas canvas_des = new Canvas(bitmap_des);
        Rect rect_src = new Rect();
        rect_src.set(0, 0, w_bitmap, h_bitmap);
        Rect rect_des = new Rect();
        rect_des.set(0, 0, w_bitmap, h_bitmap);
        canvas_des.drawBitmap(bitmap_src, rect_src, rect_des, paint);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        canvas_des.drawCircle(w_bitmap / 2, h_bitmap / 2, doubleRadius / 2, paint);
        return bitmap_des;
    }

    /**
     * 根据矩阵缩放并变换指定比例的图片
     *
     * @param bitmap_src 目标图片
     * @param rect_src   图片被指定部分的矩形区域
     * @param rect_des   图片要转换成的矩形区域
     * @return
     */
    public static Bitmap getBitmap_rect(Bitmap bitmap_src, Rect rect_src, Rect rect_des) {
        if (bitmap_src == null) {
            return null;
        }
        Bitmap bitmap_des = Bitmap.createBitmap(rect_des.width(), rect_des.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas_des = new Canvas(bitmap_des);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        canvas_des.drawBitmap(bitmap_src, rect_src, rect_des, p);
        return bitmap_des;
    }

    /**
     * 产生一个多边形的图片
     *
     * @param bitmap_src
     * @param path
     * @return
     */
    public static Bitmap getBitmap_polygon(Bitmap bitmap_src, Path path) {
        return getBitmap_polygon(bitmap_src, path, 0, 0);
    }

    /**
     * 产生一个多边形的图片
     *
     * @param bitmap_src
     * @param path
     * @return
     */
    public static Bitmap getBitmap_polygon(Bitmap bitmap_src, Path path, int x_scale, int y_scale) {
        if (bitmap_src == null) {
            return null;
        }
        Bitmap bitmap_des = Bitmap.createBitmap(bitmap_src.getWidth(), bitmap_src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas_des = new Canvas(bitmap_des);
        Paint p = new Paint();
        p.setAntiAlias(true);
        path.close();
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(3);
        canvas_des.save();
        canvas_des.translate(-x_scale, -y_scale);
        canvas_des.drawPath(path, p);
        canvas_des.restore();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas_des.drawBitmap(bitmap_src, 0, 0, p);
        p.setXfermode(null);
        return bitmap_des;
    }

    /**
     * 产生一个多边形的图片
     *
     * @param bitmap_src
     * @param positions
     * @return
     */
    public static Bitmap getBitmap_polygon(Bitmap bitmap_src, int[][] positions) {
        if (bitmap_src == null) {
            return null;
        }
        Bitmap bitmap_des = Bitmap.createBitmap(bitmap_src.getWidth(), bitmap_src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas_des = new Canvas(bitmap_des);
        Paint p = new Paint();
        p.setAntiAlias(true);
        Path path = new Path();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i].length > 1) {
                float x = positions[i][0];
                float y = positions[i][1];
                if (i == 0) {
                    path.moveTo(x, y);
                } else if (i == (positions.length - 1)) {
                    path.lineTo(x, y);
                }
            }
        }
        path.close();
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(3);
        canvas_des.drawPath(path, p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas_des.drawBitmap(bitmap_src, 0, 0, p);
        p.setXfermode(null);
        return bitmap_des;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap 转 drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(bitmap);
    }

    /**
     * 放大或者缩小bitmap
     *
     * @param bitmap
     * @param percent
     * @return
     */
    public static Bitmap getBitmapByPercent(Bitmap bitmap, float percent) {
        return getBitmapByPercent(bitmap, percent, percent);
    }

    /**
     * 给定长宽百分比缩放bitmap
     *
     * @param bitmap
     * @param percent_w
     * @param percent_h
     * @return
     */
    public static Bitmap getBitmapByPercent(Bitmap bitmap, float percent_w, float percent_h) {
        Matrix matrix = new Matrix();
        matrix.postScale(percent_w, percent_h); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 给定长宽缩放bitmap
     *
     * @param bitmap
     * @param w_des
     * @param h_des
     * @return
     */
    public static Bitmap getBitmapByWH(Bitmap bitmap, int w_des, int h_des) {
        int w_bitmap = bitmap.getWidth();
        int h_bitmap = bitmap.getHeight();
        float percnet_w = (float) w_des / w_bitmap;
        float percnet_h = (float) h_des / h_bitmap;
        return getBitmapByPercent(bitmap, percnet_w, percnet_h);
    }

    /**
     * 根据给定缩放比例获取资源
     *
     * @param context
     * @param drawableId
     * @param num
     * @return
     */
    public static Bitmap getBitmapById_Percent(Context context, int drawableId, int num) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inPreferredConfig = Bitmap.Config.RGB_565;
        option.inPurgeable = true;
        option.inInputShareable = true;
        if (num > 0) {
            option.inSampleSize = num;
        }
        if (bitmaps.containsKey(String.valueOf(drawableId))) {
            return bitmaps.get(String.valueOf(drawableId));
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId, option);
            bitmaps.put(String.valueOf(drawableId), bitmap);
            return bitmap;
        }
    }

    public static Drawable getDrawableById(Context context, int drawableId) {
        return bitmapToDrawable(getBitmapById_Percent(context, drawableId, 1));
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param angle
     * @return
     */
    public static Bitmap rotate(Bitmap bitmap, float angle) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    public static Drawable rotate(Drawable drawable, float angle) {
        if (drawable == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        Bitmap bitmap = drawableToBitmap(drawable);
        return B.bitmapToDrawable(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false));
    }

    public static int getIdByName(Context context, String name) {
        int resID = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return resID;
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmapById(Context context, int drawableId) {
        return getBitmapById_Percent(context, drawableId, 1).copy(Bitmap.Config.ARGB_8888, true);
    }

    /**
     * 水平翻转图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap overTurnBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bitmap2 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Matrix matrix = canvas.getMatrix();
        matrix.postScale(-1, 1);
        matrix.postTranslate(w, 0);//平移
        canvas.drawBitmap(bitmap, matrix, null);
        return bitmap2;
    }

    /**
     * 获取文字绘制区域长宽
     * @param text
     * @param textsize
     * @return
     */
    public static float[] getTextWH(String text, float textsize) {
        Paint p = new Paint();
        p.setTextSize(textsize);
        float textWidth = p.measureText(text);
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        // top绝对值,最外层top,负数
        float top = Math.abs(fontMetrics.top);
        // ascent绝对值,文字上部,负数
        float ascent = Math.abs(fontMetrics.ascent);
        // descent，正值,文字下部
        float descent = fontMetrics.descent;
        // bottom，正值,最外层bottom
        float bottom = fontMetrics.bottom;
        float[] arr = new float[2];
        arr[0] = textWidth;
        arr[1] = Math.abs(descent - ascent);
        return arr;
    }
}
