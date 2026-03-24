package com.is.mediaPlayer.player;

import android.content.Context;
import android.net.Uri;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

/**
 * Media3的封装。
 */
public class MediaPLayerManager {
    //volatile是保证线程安全
    private static volatile MediaPLayerManager instance;

    private ExoPlayer mPlayer;

    private MediaPLayerManager(Context context) {
        //这里的上下文绑定的是 Application.跟app的生命周期绑定。可以给多个activity使用，单一数据模式
        mPlayer = new ExoPlayer.Builder(context.getApplicationContext()).build();
    }

    public static MediaPLayerManager getInstance(Context context) {
        if (instance == null) {
            /**
             * Synchronized是 Java 中的同步关键字，用于控制多个线程对共享资源的访问，确保线程安全。
             * 1. 基本概念
             * synchronized是 Java 中的内置锁，可以保证：
             * 原子性：确保操作不会被其他线程中断
             * 可见性：确保一个线程修改共享变量后，其他线程能立即看到
             * 有序性：防止指令重排序
             */
            synchronized (MediaPLayerManager.class) {
                if (instance == null) {
                    instance = new MediaPLayerManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 绑定播放视图
     */
    public void bindPLayerView(PlayerView playerView) {
        if (mPlayer != null && playerView != null) {
            playerView.setPlayer(mPlayer);
        }
    }

    public ExoPlayer getPlayer() {
        return mPlayer;
    }

    /**
     * 播放指定URL
     */
    public void play(String url) {
        if (mPlayer != null) {
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
            mPlayer.setMediaItem(mediaItem);//要没有带s的，细心一点
            mPlayer.prepare();
            mPlayer.play();

        }
    }

    /**
     * 暂停播放
     */
    public void pauser(){
        if (mPlayer !=null){
            mPlayer.pause();
        }
    }

    /**
     * true: 如果所有其他的条件（如缓存完成、音视频轨道就绪等）满足，播放器就会开始播放
     * false:播放器会进入 暂停状态；但不会释放资源 恢复后可以继续播放
     * @param playWhenReady
     */
    public void setPlayWhenReady(boolean playWhenReady){
        if (mPlayer !=null){
            mPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    public void release(){
        if (mPlayer!=null){
            mPlayer.release();
            mPlayer = null;
            instance =null;
        }
    }

}
