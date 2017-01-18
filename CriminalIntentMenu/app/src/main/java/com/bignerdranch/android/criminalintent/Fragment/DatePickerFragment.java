package com.bignerdranch.android.criminalintent.Fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.bignerdranch.android.criminalintent.R;

public class DatePickerFragment extends DialogFragment {
    //传递参数
    public static final String EXTRA_DATE = "criminalintent.DATE";
    //时间
    private Date mDate;

    /**
     * 创建DatePickerFragment实例
     */
    public static DatePickerFragment newInstance(Date date) {
        //设置bundle数据
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        //传递数据
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * 发送设置的时间结果
     */
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) 
            return;

        Intent i = new Intent();
        //设置传递的时间参数
        i.putExtra(EXTRA_DATE, mDate);
        //传递结果
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    /**
     * 创建一个带标题栏和ok按钮的AlertDialog
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //获取时间
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        //时间转换
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH);
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //设置布局
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        //创建DatePicker
        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                //设置时间
                mDate = new GregorianCalendar(year, month, day).getTime();
                //保存时间
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        //以流接口方式创建AlertDialog
        return new AlertDialog.Builder(getActivity())
             //设置布局
            .setView(v)
             //设置标题
            .setTitle(R.string.date_picker_title)
             //设置确定按钮 设置监听器
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //向目标fragment发送结果
                    sendResult(Activity.RESULT_OK);
                }
            })
            .create();
    }
}
