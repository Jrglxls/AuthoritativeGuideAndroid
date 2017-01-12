package com.authoritativeguide.criminalintent.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import com.authoritativeguide.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jrglxls on 2017/1/11.
 */

public class DatePickerFragment extends DialogFragment {
    //时间
    private Date date;

    @NonNull
    @Override
    /**
     * 创建一个带标题栏和ok按钮的AlertDialog
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //获取时间
        date = (Date)getArguments().getSerializable("DATE");

        //时间转换
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH);
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //设置布局
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);
        //创建DatePicker
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.dp_choose_time);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                //设置时间
                date = new GregorianCalendar(year,month,day).getTime();
                //保存时间
                getArguments().putSerializable("DATE",date);
            }
        });

        //以流接口方式创建AlertDialog
        return new AlertDialog.Builder(getActivity()).
                //设置布局
                setView(view).
                //设置标题
                setTitle("时间").
                //设置确定按钮 设置监听器
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //向目标fragment发送结果
                        sendResult(Activity.RESULT_OK);
                    }
                }).create();
    }

    /**
     * 创建DatePickerFragment实例
     */
    public static DatePickerFragment newInstance(Date date){
        //设置bundle数据
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATE",date);
        //传递数据
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    /**
     * 发送设置的时间结果
     */
    private void sendResult(int resultCode){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        //设置传递的时间参数
        intent.putExtra("DATE",date);
        //传递结果
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
