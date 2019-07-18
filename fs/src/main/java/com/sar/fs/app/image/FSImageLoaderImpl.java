package com.sar.fs.app.image;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * @auth: sarWang
 * @date: 2019-07-04 18:01
 * @describe
 */
public interface FSImageLoaderImpl {
    void displayHeadPortraitImage(Context var1, String var2, ImageView var3);

    void displayImage(Context var1, String var2, ImageView var3);

    void displayImage(Context var1, String var2, ImageView var3, int var4);

    void displayImage(Context var1, String var2, ImageView var3, int var4, int var5, int var6);

    File getDiskCacheDirectory(Context var1);

    void clearDiskCache(Context var1);

    void clearMemoryCache(Context var1);
}
