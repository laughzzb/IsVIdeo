package com.is.libbase.utils;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.os.storage.StorageManager;

import com.is.libbase.base.BaseApplication;

import java.io.File;
import java.util.UUID;

public class CacheUtils {


    //获取应用缓存大小（不包含外部缓存）
    public static String getTotalCacheSize() {
        Context context = BaseApplication.getContext();
        //安卓8.0以上的系统，可以用StorageStatsManager计算缓存大小，更贴近系统设置显示的缓存大小
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);
                String packageName = context.getPackageName();
                StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
                UUID uuid = storageManager.getUuidForPath(context.getFilesDir());
                StorageStats storageStats = storageStatsManager.queryStatsForPackage(uuid, packageName, Process.myUserHandle());

                long cacheSize = storageStats.getCacheBytes();//缓存大小
                return formatSize(cacheSize);
            } catch (Exception e) {
                e.printStackTrace();
                return "0B";
            }
        } else {
            //这种形式的原理是手动计算缓存目录的大小，但是不容易贴近系统缓存
            //因为在app内无法统计到一些缓存数据，比如系统设置，系统层面管理的缓存等等
            try {
                long cacheSize = getFolderSize(context.getCacheDir());
                return formatSize(cacheSize);
            } catch (Exception e) {
                e.printStackTrace();
                return "0B";
            }
        }
    }

    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        size += getFolderSize(f);
                    } else {
                        size += f.length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return size;
    }

    /**
     * 格式化文件大小（B->KB->MB->GB->TB）
     */
    private static String formatSize(long size) {
        double kiloByte = size / 1024.0;
        if (kiloByte < 1) {
            return size + "B";
        }
        double megaByte = kiloByte / 1024.0;
        if (megaByte < 1) {
            return String.format("%.2fKB", kiloByte);
        }
        double gigaByte = megaByte / 1024.0;
        if (gigaByte < 1) {
            return String.format("%.2fMB", megaByte);
        }
        double teraBytes = gigaByte / 1024.0;
        if (teraBytes < 1) {
            return String.format("%.2fGB", gigaByte);
        }
        return String.format("%2.fTB", teraBytes);
    }

    /**
     * 清除缓存 原理是删除app内的缓存目录
     */
    public static boolean cleanAppCache() {
        boolean success = false;
        Context context = BaseApplication.getContext();
        try {
            File cacheDir = context.getCacheDir();
            success = delectDir(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 清除指定目录的缓存
     * 如果需要删除缓存目录以外的目录，比如外部储存的一些数据，那就自行处理
     */
    public static boolean clearExternalCache() {
        boolean isSuccess = false;
        Context context = BaseApplication.getContext();
        try {
            File externalCacheDir = context.getExternalCacheDir();
            delectDir(externalCacheDir);
            isSuccess = delectDir(externalCacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    private static boolean delectDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] chindren = dir.list();
            for (String child : chindren) {
                boolean success = delectDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        }
        return false;
    }


}
