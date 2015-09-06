package com.stkj.xtools;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by jarrah on 2015/8/19.
 */
public class UniversalImageHelper {


    private static ImageLoader sImageLoader;

    public static ImageLoader getImageLoader() {
        return sImageLoader;
    }

    public static void initImageLoader(Context context) {
        try {
            String CACHE_DIR = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/.temp_tmp";

            new File(CACHE_DIR).mkdirs();
            File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                    CACHE_DIR);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                    .cacheInMemory(false)
                    .showImageForEmptyUri(R.drawable.no_media)
                    .showImageOnLoading(R.drawable.no_media)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    context)
                    .defaultDisplayImageOptions(defaultOptions)
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .memoryCache(new WeakMemoryCache());

            ImageLoaderConfiguration config = builder.build();
            sImageLoader = ImageLoader.getInstance();
            sImageLoader.init(config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayDrawable(ImageView iv, int resId) {
        getImageLoader().displayImage("drawable://" + resId, iv);
    }

    public static void displayAsset(ImageView iv, String filePath) {
        getImageLoader().displayImage("assets://" + filePath, iv);
    }

    public static void displayNetwork(ImageView iv, String url) {
        getImageLoader().displayImage(url, iv);
    }
}
