package com.sar.fs.app.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sar.fs.config.FSGlobal;

import java.io.File;

/**
 * @auth: sarWang
 * @date: 2019-07-04 18:00
 * @describe
 */

public class FSImageLoader implements FSImageLoaderImpl {
    private static FSImageLoaderImpl mImageGlide;

    public FSImageLoader() {
    }

    public static FSImageLoaderImpl getInstance() {
        if (mImageGlide == null) {
            mImageGlide = new FSImageLoader();
        }

        return mImageGlide;
    }

    public void displayHeadPortraitImage(Context context, String uri, ImageView imageView) {
        Glide.with(context).load(uri).dontAnimate().placeholder(FSGlobal.img_default_avatar).fallback(FSGlobal.img_default_avatar).error(FSGlobal.img_default_avatar).into(imageView);
    }

    public void displayImage(Context context, String uri, ImageView imageView) {
        this.displayImage(context, uri, imageView, FSGlobal.img_default_load, FSGlobal.img_default_empty, FSGlobal.img_default_fail);
    }

    public void displayImage(Context context, String uri, ImageView imageView, int defaultImage) {
        Glide.with(context).load(uri).placeholder(defaultImage).fallback(defaultImage).error(defaultImage).into(imageView);
    }

    public void displayImage(Context context, String uri, ImageView imageView, int defaultLoadImage, int defaultEmptyImage, int defaultFailImage) {
        Glide.with(context).load(uri).placeholder(defaultLoadImage).fallback(defaultFailImage).error(defaultFailImage).into(imageView);
    }

    public File getDiskCacheDirectory(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }
}

