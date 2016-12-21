package com.authoritativeguide.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Jrglxls on 2016/12/21.
 */

public class CrimeFragment extends Fragment {
    //Crime实例成员变量
    private Crime crime;
    //输入框
    private EditText etCrimeTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Crime实例成员变量
        crime = new Crime();
    }

    /**
     * 添加视图
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //第一个参数fragment视图直接通过inflater传入资源id生成
        //第二个参数是视图的父视图，通常需要父视图类正确配置组件
        //第三个参数告知布局生成器是否将生成的视图添加给父视图，将通过activity代码的方式添加视图
        View view = inflater.inflate(R.layout.fragment_crime,container,false);
        //输入框
        etCrimeTitle = (EditText) view.findViewById(R.id.et_crime_title);
        //输入框监听
        etCrimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //设置title为输入内容
                crime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}
