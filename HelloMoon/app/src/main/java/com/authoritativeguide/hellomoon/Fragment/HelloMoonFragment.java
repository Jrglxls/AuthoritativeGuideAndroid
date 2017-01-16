package com.authoritativeguide.hellomoon.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.authoritativeguide.hellomoon.AudioPlayer;
import com.authoritativeguide.hellomoon.R;

/**
 * Created by Jrglxls on 2017/1/13.
 */

public class HelloMoonFragment extends Fragment{
    //创建AudioPlayer对象，实现媒体管理
    private AudioPlayer audioPlayer = new AudioPlayer();
    //播放
    private Button btnPlay;
    //暂停
    private Button btnStop;

    /**
     * 确保MediaPlayer实例一直存在
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    /**
     * 设置布局
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置布局
        View view = inflater.inflate(R.layout.fragment_hello_moon,null);
        //播放
        btnPlay = (Button) view.findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.play(getActivity());
            }
        });
        //暂停
        btnStop = (Button) view.findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.stop();
            }
        });
        return view;
    }

    /**
     * fragment销毁时 防止不停播放
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        audioPlayer.stop();
    }
}
