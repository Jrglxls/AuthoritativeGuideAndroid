package com.authoritativeguide.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Jrglxls on 2017/1/13.
 */

public class AudioPlayer {
    //媒体控制器
    private MediaPlayer mediaPlayer;

    /**
     * 播放
     */
    public void play(Context context){
        //避免用户多次单机Play按钮创建多个MediaPlayer实例的情况发生
        stop();
        //设置播放资源
        mediaPlayer = MediaPlayer.create(context,R.raw.one_small_step);
        //完整播放监听器
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //尽可能快的释放MediaPlayer实例以及其占用的资源
                stop();
            }
        });
        //开始播放
        mediaPlayer.start();
    }

    /**
     * 暂停
     */
    public void stop(){
        if (mediaPlayer != null){
            //销毁实例
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
