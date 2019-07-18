package com.sar.fs.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.BitmapFactory.Options;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:43
 * @describe
 */
public class FSImageUtil {
    public static final String TAG = "FSImageUtil";
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;

    public FSImageUtil() {
    }

    public static Bitmap getBitmap(Context context, int resId) {
        Options opt = new Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, (Rect)null, opt);
    }

    public static Bitmap getBitmap(Context context, String fileName) {
        FileInputStream fis = null;
        Bitmap bitmap = null;

        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException var15) {
            var15.printStackTrace();
        } catch (OutOfMemoryError var16) {
            var16.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception var14) {
            }

        }

        return bitmap;
    }

    public static Bitmap getBitmapByPath(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;

        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException var14) {
            var14.printStackTrace();
        } catch (OutOfMemoryError var15) {
            var15.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception var13) {
            }

        }

        return bitmap;
    }

    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, (Options)null);
    }

    public static Bitmap getBitmapByPath(String filePath, Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;

        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, (Rect)null, opts);
        } catch (FileNotFoundException var15) {
            var15.printStackTrace();
        } catch (OutOfMemoryError var16) {
            var16.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception var14) {
            }

        }

        return bitmap;
    }

    public static Bitmap scaleImg(File file, int newWidth, int newHeight) {
        Bitmap resizeBmp = null;
        if (newWidth > 0 && newHeight > 0) {
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getPath(), opts);
            int srcWidth = opts.outWidth;
            int srcHeight = opts.outHeight;
            int destWidth = srcWidth;
            int destHeight = srcHeight;
            float scale = 0.0F;
            float scaleWidth = (float)newWidth / (float)srcWidth;
            float scaleHeight = (float)newHeight / (float)srcHeight;
            if (scaleWidth > scaleHeight) {
                scale = scaleWidth;
            } else {
                scale = scaleHeight;
            }

            if (scale != 0.0F) {
                destWidth = (int)((float)srcWidth / scale);
                destHeight = (int)((float)srcHeight / scale);
            }

            opts.inPreferredConfig = Config.RGB_565;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            if (scale > 1.0F) {
                opts.inSampleSize = (int)scale;
            } else {
                opts.inSampleSize = 1;
            }

            opts.outHeight = destHeight;
            opts.outWidth = destWidth;
            opts.inJustDecodeBounds = false;
            opts.inDither = false;
            resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
            if (resizeBmp != null && scale != 1.0F) {
                resizeBmp = scaleImg(resizeBmp, scale);
            }

            return resizeBmp;
        } else {
            throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
        }
    }

    public static Bitmap scaleImg(Bitmap bitmap, float scale) {
        Bitmap resizeBmp = null;

        try {
            int bmpW = bitmap.getWidth();
            int bmpH = bitmap.getHeight();
            Matrix mt = new Matrix();
            mt.postScale(scale, scale);
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, mt, true);
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
                bitmap.recycle();
            }

        }

        return resizeBmp;
    }

    public static Bitmap scaleImg(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap resizeBmp = null;
        if (bitmap == null) {
            return null;
        } else if (newWidth > 0 && newHeight > 0) {
            int srcWidth = bitmap.getWidth();
            int srcHeight = bitmap.getHeight();
            if (srcWidth > 0 && srcHeight > 0) {
                float scale = 0.0F;
                float scaleWidth = (float)newWidth / (float)srcWidth;
                float scaleHeight = (float)newHeight / (float)srcHeight;
                if (scaleWidth > scaleHeight) {
                    scale = scaleWidth;
                } else {
                    scale = scaleHeight;
                }

                if (bitmap != null && scale != 1.0F) {
                    resizeBmp = scaleImg(bitmap, scale);
                }

                return resizeBmp;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
        }
    }

    public static Bitmap cutImg(File file, int newWidth, int newHeight) {
        Bitmap resizeBmp = null;
        if (newWidth > 0 && newHeight > 0) {
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getPath(), opts);
            int srcWidth = opts.outWidth;
            int srcHeight = opts.outHeight;
            int destWidth = 0;
            int destHeight = 0;
            int cutSrcWidth = newWidth * 2;
            int cutSrcHeight = newHeight * 2;
            double ratio = 0.0D;
            if (srcWidth >= cutSrcWidth && srcHeight >= cutSrcHeight) {
                if (srcWidth > cutSrcWidth) {
                    ratio = (double)srcWidth / (double)cutSrcWidth;
                    destWidth = cutSrcWidth;
                    destHeight = (int)((double)srcHeight / ratio);
                } else if (srcHeight > cutSrcHeight) {
                    ratio = (double)srcHeight / (double)cutSrcHeight;
                    destHeight = cutSrcHeight;
                    destWidth = (int)((double)srcWidth / ratio);
                }
            } else {
                ratio = 0.0D;
                destWidth = srcWidth;
                destHeight = srcHeight;
            }

            opts.inPreferredConfig = Config.RGB_565;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            if (ratio > 1.0D) {
                opts.inSampleSize = (int)ratio;
            } else {
                opts.inSampleSize = 1;
            }

            opts.outHeight = destHeight;
            opts.outWidth = destWidth;
            opts.inJustDecodeBounds = false;
            opts.inDither = false;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), opts);
            if (bitmap != null) {
                resizeBmp = cutImg(bitmap, newWidth, newHeight);
            }

            return resizeBmp;
        } else {
            throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
        }
    }

    public static Bitmap cutImg(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null) {
            return null;
        } else if (newWidth > 0 && newHeight > 0) {
            Bitmap resizeBmp = null;

            Object var6;
            try {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (width > 0 && height > 0) {
                    int offsetX = 0;
                    int offsetY = 0;
                    if (width > newWidth) {
                        offsetX = (width - newWidth) / 2;
                    } else {
                        newWidth = width;
                    }

                    if (height > newHeight) {
                        offsetY = (height - newHeight) / 2;
                    } else {
                        newHeight = height;
                    }

                    resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, newWidth, newHeight);
                    return resizeBmp;
                }

                var6 = null;
            } catch (Exception var11) {
                var11.printStackTrace();
                return resizeBmp;
            } finally {
                if (resizeBmp != bitmap) {
                    bitmap.recycle();
                }

            }

            return (Bitmap)var6;
        } else {
            throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
        }
    }

    public static int getByteCount(Bitmap bitmap, Bitmap.CompressFormat mCompressFormat) {
        int size = 0;
        ByteArrayOutputStream output = null;

        try {
            output = new ByteArrayOutputStream();
            bitmap.compress(mCompressFormat, 100, output);
            byte[] result = output.toByteArray();
            size = result.length;
            Object var15 = null;
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception var12) {
                    var12.printStackTrace();
                }
            }

        }

        return size;
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable mBitmapDrawable = null;

        try {
            if (bitmap == null) {
                return null;
            }

            mBitmapDrawable = new BitmapDrawable(bitmap);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return mBitmapDrawable;
    }

    public static InputStream bitmapToInputStream(Bitmap bm, int compress) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, compress, baos);
        InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
        if (!bm.isRecycled()) {
            bm.recycle();
            System.gc();
        }

        return sbs;
    }

    public static TransitionDrawable bitmapToTransitionDrawable(Bitmap bitmap) {
        TransitionDrawable mBitmapDrawable = null;

        try {
            if (bitmap == null) {
                return null;
            }

            mBitmapDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(17170445), new BitmapDrawable(bitmap)});
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return mBitmapDrawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static TransitionDrawable drawableToTransitionDrawable(Drawable drawable) {
        TransitionDrawable mBitmapDrawable = null;

        try {
            if (drawable == null) {
                return null;
            }

            mBitmapDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(17170445), drawable});
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return mBitmapDrawable;
    }

    public static Bitmap bytesToBimap(byte[] b) {
        Bitmap bitmap = null;

        try {
            if (b.length != 0) {
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap imageViewToBitmap(ImageView view) {
        Bitmap bitmap = null;

        try {
            bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return bitmap;
    }

    public static byte[] viewToBytes(View view, Bitmap.CompressFormat compressFormat) {
        byte[] b = null;

        try {
            Bitmap bitmap = view2Bitmap(view);
            b = bitmapToBytes(bitmap, compressFormat, true);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return b;
    }

    public static byte[] bitmapToBytes(Bitmap bitmap, Bitmap.CompressFormat mCompressFormat, boolean needRecycle) {
        byte[] result = null;
        ByteArrayOutputStream output = null;

        try {
            output = new ByteArrayOutputStream();
            bitmap.compress(mCompressFormat, 100, output);
            result = output.toByteArray();
            if (needRecycle) {
                bitmap.recycle();
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception var13) {
                    var13.printStackTrace();
                }
            }

        }

        return result;
    }

    public static Bitmap view2Bitmap(View view) {
        Bitmap bitmap = null;

        try {
            if (view != null) {
                view.setDrawingCacheEnabled(true);
                view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                view.buildDrawingCache();
                bitmap = view.getDrawingCache();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return bitmap;
    }

    public static Drawable view2Drawable(View view) {
        BitmapDrawable mBitmapDrawable = null;

        try {
            Bitmap newbmp = view2Bitmap(view);
            if (newbmp != null) {
                mBitmapDrawable = new BitmapDrawable(newbmp);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return mBitmapDrawable;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
        Bitmap mBitmap = null;

        try {
            Matrix m = new Matrix();
            m.setRotate(degrees % 360.0F);
            mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return mBitmap;
    }

    public static Bitmap rotateBitmapTranslate(Bitmap bitmap, float degrees) {
        Object mBitmap = null;

        try {
            Matrix matrix = new Matrix();
            int width;
            int height;
            if (degrees / 90.0F % 2.0F != 0.0F) {
                width = bitmap.getWidth();
                height = bitmap.getHeight();
            } else {
                width = bitmap.getHeight();
                height = bitmap.getWidth();
            }

            int cx = width / 2;
            int cy = height / 2;
            matrix.preTranslate((float)(-cx), (float)(-cy));
            matrix.postRotate(degrees);
            matrix.postTranslate((float)cx, (float)cy);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return (Bitmap)mBitmap;
    }

    public static void releaseBitmapArray(Bitmap[] bitmaps) {
        if (bitmaps != null) {
            try {
                Bitmap[] var1 = bitmaps;
                int var2 = bitmaps.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    Bitmap bitmap = var1[var3];
                    if (bitmap != null && !bitmap.isRecycled()) {
                        Log.d("FSImageUtil", "releaseBitmapArray() called with: bitmaps = [" + bitmaps + "]");
                        bitmap.recycle();
                    }
                }
            } catch (Exception var5) {
            }
        }

    }

    public static String getHashCode(Bitmap bitmap) {
        Bitmap temp = Bitmap.createScaledBitmap(bitmap, 8, 8, false);
        int width = temp.getWidth();
        int height = temp.getHeight();
        Log.i("th", "将图片缩小到8x8的尺寸:" + width + "*" + height);
        int[] pixels = new int[width * height];

        int avgPixel;
        for(avgPixel = 0; avgPixel < width; ++avgPixel) {
            for(int j = 0; j < height; ++j) {
                pixels[avgPixel * height + j] = rgbToGray(temp.getPixel(avgPixel, j));
            }
        }

        releaseBitmap(temp);
        avgPixel = FSMathUtil.average(pixels);
        int[] comps = new int[width * height];

        for(int i = 0; i < comps.length; ++i) {
            if (pixels[i] >= avgPixel) {
                comps[i] = 1;
            } else {
                comps[i] = 0;
            }
        }

        StringBuffer hashCode = new StringBuffer();

        for(int i = 0; i < comps.length; i += 4) {
            int result = comps[i] * (int)Math.pow(2.0D, 3.0D) + comps[i + 1] * (int)Math.pow(2.0D, 2.0D) + comps[i + 2] * (int)Math.pow(2.0D, 1.0D) + comps[i + 2];
            hashCode.append(FSMathUtil.binaryToHex(result));
        }

        String sourceHashCode = hashCode.toString();
        return sourceHashCode;
    }

    public static void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    Log.d("FSImageUtil", "releaseBitmap() called with: bitmap = [" + bitmap + "]");
                    bitmap.recycle();
                }
            } catch (Exception var2) {
            }

            bitmap = null;
        }

    }

    private static int rgbToGray(int pixels) {
        int _red = pixels >> 16 & 255;
        int _green = pixels >> 8 & 255;
        int _blue = pixels & 255;
        return (int)(0.3D * (double)_red + 0.59D * (double)_green + 0.11D * (double)_blue);
    }

    public static int[] getColorHistogram(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] areaColor = new int[64];

        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
                int pixels = bitmap.getPixel(i, j);
                int red = pixels >> 16 & 255;
                int green = pixels >> 8 & 255;
                int blue = pixels & 255;
                int redArea = 0;
                int greenArea = 0;
                int blueArea = 0;
                if (red >= 192) {
                    redArea = 3;
                } else if (red >= 128) {
                    redArea = 2;
                } else if (red >= 64) {
                    redArea = 1;
                } else if (red >= 0) {
                    redArea = 0;
                }

                if (green >= 192) {
                    greenArea = 3;
                } else if (green >= 128) {
                    greenArea = 2;
                } else if (green >= 64) {
                    greenArea = 1;
                } else if (green >= 0) {
                    greenArea = 0;
                }

                if (blue >= 192) {
                    blueArea = 3;
                } else if (blue >= 128) {
                    blueArea = 2;
                } else if (blue >= 64) {
                    blueArea = 1;
                } else if (blue >= 0) {
                    blueArea = 0;
                }

                int index = redArea * 16 + greenArea * 4 + blueArea;
                int var10002 = areaColor[index]++;
            }
        }

        return areaColor;
    }

    public static int hammingDistance(String sourceHashCode, String hashCode) {
        int difference = 0;
        int len = sourceHashCode.length();

        for(int i = 0; i < len; ++i) {
            if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
                ++difference;
            }
        }

        return difference;
    }

    public static void saveImage(Context context, String fileName, Bitmap bitmap) throws IOException {
        saveImage(context, fileName, bitmap, 100);
    }

    public static void saveImage(Context context, String fileName, Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null && fileName != null && context != null) {
            FileOutputStream fos = context.openFileOutput(fileName, 0);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            byte[] bytes = stream.toByteArray();
            fos.write(bytes);
            fos.close();
        }
    }

    public static void saveImageToSD(String filePath, Bitmap bitmap, int quality) throws IOException {
        if (bitmap != null) {
            FileOutputStream fos = new FileOutputStream(filePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            byte[] bytes = stream.toByteArray();
            fos.write(bytes);
            fos.close();
        }

    }

    public static String getImageType(File file) {
        if (file != null && file.exists()) {
            FileInputStream in = null;

            String var3;
            try {
                in = new FileInputStream(file);
                String type = getImageType((InputStream)in);
                var3 = type;
                return var3;
            } catch (IOException var13) {
                var3 = null;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var12) {
                }

            }

            return var3;
        } else {
            return null;
        }
    }

    public static String getImageType(InputStream in) {
        if (in == null) {
            return null;
        } else {
            try {
                byte[] bytes = new byte[8];
                in.read(bytes);
                return getImageType(bytes);
            } catch (IOException var2) {
                return null;
            }
        }
    }

    public static String getImageType(byte[] bytes) {
        if (isJPEG(bytes)) {
            return "image/jpeg";
        } else if (isGIF(bytes)) {
            return "image/gif";
        } else if (isPNG(bytes)) {
            return "image/png";
        } else {
            return isBMP(bytes) ? "application/x-bmp" : null;
        }
    }

    private static boolean isJPEG(byte[] b) {
        if (b.length < 2) {
            return false;
        } else {
            return b[0] == -1 && b[1] == -40;
        }
    }

    private static boolean isGIF(byte[] b) {
        if (b.length < 6) {
            return false;
        } else {
            return b[0] == 71 && b[1] == 73 && b[2] == 70 && b[3] == 56 && (b[4] == 55 || b[4] == 57) && b[5] == 97;
        }
    }

    private static boolean isPNG(byte[] b) {
        if (b.length < 8) {
            return false;
        } else {
            return b[0] == -119 && b[1] == 80 && b[2] == 78 && b[3] == 71 && b[4] == 13 && b[5] == 10 && b[6] == 26 && b[7] == 10;
        }
    }

    private static boolean isBMP(byte[] b) {
        if (b.length < 2) {
            return false;
        } else {
            return b[0] == 66 && b[1] == 77;
        }
    }
}

