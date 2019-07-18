package com.sar.fs.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.sar.fs.config.FSGlobal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:40
 * @describe
 */
public class FSFileUtil {
    public static final String TAG = "FSFileUtil";

    public FSFileUtil() {
    }

    public static void initDirPath() {
        File file = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            file = new File(Environment.getExternalStorageDirectory(), FSGlobal.cache_file);
        }

        if (!file.exists()) {
            file.mkdirs();
        }

    }

    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Exception var1) {
            var1.printStackTrace();
            return false;
        }
    }

    public static int freeSpaceOnSD() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = (double)stat.getAvailableBlocks() * (double)stat.getBlockSize() / 1048576.0D;
        return (int)sdFreeMB;
    }

    public static String getLocalPath(String fileName) {
        return String.format("%s%s", getDirPath(), fileName);
    }

    public static String getDirPath() {
        return (new File(Environment.getExternalStorageDirectory(), FSGlobal.cache_file)).getAbsolutePath();
    }

    public static String getFullFileName(URL url) {
        String file = url.getFile();
        int queryStart = file.indexOf("?");
        if (queryStart >= 0) {
            file = file.substring(0, queryStart);
        }

        int lastSlash = file.lastIndexOf("/");
        if (lastSlash >= 0) {
            file = file.substring(lastSlash + 1);
        }

        return file;
    }

    public static String getRandmonFileName(String fileSuffix) {
        return FSDateUtil.getCurrentDate("yyyyMMddHHmmss") + "." + fileSuffix;
    }

    public static long getFileSize(File f) {
        long size = 0L;
        File[] flist = f.listFiles();

        for(int i = 0; i < flist.length; ++i) {
            if (flist[i].isDirectory()) {
                size += getFileSize(flist[i]);
            } else {
                size += flist[i].length();
            }
        }

        return size;
    }

    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024L) {
            suffix = "K";
            size >>= 10;
            if (size >= 1024L) {
                suffix = "M";
                size >>= 10;
                if (size >= 1024L) {
                    suffix = "G";
                    size >>= 10;
                }
            }
        }

        return size + suffix;
    }

    public static File save(byte[] content, String fileName, String ext) {
        String mFileName = String.format("%s.%s", fileName, ext);
        String storageStatus = Environment.getExternalStorageState();
        File file = null;
        if ("mounted".equals(storageStatus)) {
            file = new File(getDirPath(), mFileName);

            try {
                file.mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    file.delete();
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(content);
                fos.flush();
                fos.close();
            } catch (IOException var7) {
                Log.e("FSFileUtil", "save: ", var7);
            }
        }

        return file;
    }

    public static File createTempFile(String fileName) {
        String storageStatus = Environment.getExternalStorageState();
        File file = null;
        if ("mounted".equals(storageStatus)) {
            file = new File(getDirPath(), fileName);
            File folder = new File(getDirPath());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            try {
                if (file.exists()) {
                    file.delete();
                }

                if (!file.exists()) {
                    file = File.createTempFile(fileName.substring(0, fileName.indexOf(".")), fileName.substring(fileName.indexOf("."), fileName.length()), folder);
                }
            } catch (IOException var5) {
                Log.e("FSFileUtil", "createTempFile: ", var5);
            }
        }

        return file;
    }

    public static FileOutputStream openFileOutputStream(String name, boolean append) {
        String storageStatus = Environment.getExternalStorageState();
        FileOutputStream outputStream = null;
        if ("mounted".equals(storageStatus)) {
            File file = new File(getDirPath(), name);
            File folder = new File(getDirPath());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            try {
                if (file.exists() && !append) {
                    file.delete();
                }

                if (!file.exists()) {
                    file = createFile(name);
                }

                outputStream = new FileOutputStream(file);
            } catch (IOException var7) {
                Log.e("FSFileUtil", "openFileOutputStream: ", var7);
            }
        }

        return outputStream;
    }

    public static File createFile(String fileName) {
        String storageStatus = Environment.getExternalStorageState();
        File file = null;
        if ("mounted".equals(storageStatus)) {
            file = new File(getDirPath(), fileName);
            File folder = new File(getDirPath());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            try {
                if (file.exists()) {
                    file.delete();
                }

                if (!file.exists()) {
                    file.createNewFile();
                    file.deleteOnExit();
                }
            } catch (IOException var5) {
                Log.e("FSFileUtil", "createFile: ", var5);
            }
        }

        return file;
    }

    public static FileInputStream getFileNameInputStream(String fileName) {
        String storageStatus = Environment.getExternalStorageState();
        FileInputStream inputStream = null;
        if ("mounted".equals(storageStatus)) {
            File file = new File(getDirPath(), fileName);

            try {
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                }
            } catch (IOException var5) {
                Log.e("FSFileUtil", "getFileNameInputStream: ", var5);
            }
        }

        return inputStream;
    }

    public static FileInputStream getFilePathInputStream(String filePath) {
        FileInputStream inputStream = null;
        File file = new File(filePath);

        try {
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            }
        } catch (IOException var4) {
            Log.e("FSFileUtil", "getFilePathInputStream: ", var4);
        }

        return inputStream;
    }

    public static String getStringForName(Context context, String fileName) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        try {
            inputStream = getInputStreamForName(context, fileName);

            int len;
            while((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }

            outputStream.close();
            inputStream.close();
        } catch (IOException var7) {
            Log.e("FSFileUtil", "getStringForName: ", var7);
        }

        return outputStream.toString();
    }

    public static InputStream getInputStreamForName(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(fileName);
        } catch (IOException var5) {
            Log.e("FSFileUtil", "getInputStreamForName: ", var5);
        }

        return inputStream;
    }

    public static Bitmap getBitmapForName(Context context, String fileName) {
        Bitmap bitmap = null;
        InputStream inputStream = null;

        try {
            inputStream = getInputStreamForName(context, fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException var5) {
            Log.e("FSFileUtil", "getBitmapForName: ", var5);
        }

        return bitmap;
    }
}

