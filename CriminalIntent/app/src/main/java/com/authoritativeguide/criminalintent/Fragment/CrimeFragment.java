package com.authoritativeguide.criminalintent.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.authoritativeguide.criminalintent.Model.Crime;
import com.authoritativeguide.criminalintent.Model.CrimeLab;
import com.authoritativeguide.criminalintent.R;

import java.util.UUID;

/**
 * Created by Jrglxls on 2016/12/21.
 */

public class CrimeFragment extends Fragment {
    //Crime实例成员变量
    private Crime crime;
    //输入框
    private EditText etCrimeTitle;
    //日期按钮
    private Button btnCrimeDate;
    //复选框
    private CheckBox cbCrimeSolve;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Crime实例成员变量
//        crime = new Crime();

        //获取ID信息 方法一
//        UUID uuid = (UUID) getActivity().getIntent().getSerializableExtra("UUID");

        //获取ID信息 方法二
        UUID uuid = (UUID) getArguments().getSerializable("UUID");

        crime = CrimeLab.get(getActivity()).getCrime(uuid);
    }

    /**
     * 完成fragment实例的创建及数据的传入
     */
    public static CrimeFragment newInstance(UUID uuid){
        Bundle bundle = new Bundle();
        bundle.putSerializable("UUID",uuid);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
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
        etCrimeTitle.setText(crime.getTitle());
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
        //日期按钮
        btnCrimeDate = (Button) view.findViewById(R.id.btn_crime_date);
        //设置日期
        btnCrimeDate.setText(crime.getDate().toString());
        //设置按钮不可点击
        btnCrimeDate.setEnabled(false);
        //复选框
        cbCrimeSolve = (CheckBox) view.findViewById(R.id.cb_crime_solve);
        cbCrimeSolve.setChecked(crime.getSolve());
        //选择监听
        cbCrimeSolve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                //设置复选框已选择
                crime.setSolve(isCheck);
            }
        });
        return view;
    }

    /**
     * 通知托管activity返回结果值
     */
    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK,null);
    }
}
