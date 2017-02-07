package com.bignerdranch.android.criminalintent.Fragment;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.bignerdranch.android.criminalintent.Model.CrimeLab;
import com.bignerdranch.android.criminalintent.Model.Crime;
import com.bignerdranch.android.criminalintent.R;

public class CrimeFragment extends Fragment {
    //传递的Crime UUID
    public static final String EXTRA_CRIME_ID = "criminalintent.CRIME_ID";
    //传递的时间
    private static final String DIALOG_DATE = "date";
    //请求码
    private static final int REQUEST_DATE = 0;

    //Crime实例成员变量
    private Crime mCrime;
    //输入框
    private EditText etCrimeTitle;
    //日期按钮
    private Button mDateButton;
    //复选框
    private CheckBox mSolvedCheckBox;

    /**
     * 完成fragment实例的创建及数据的传入
     */
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);

        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Crime实例成员变量
//        crime = new Crime();

        //获取ID信息 方法一
//        UUID uuid = (UUID) getActivity().getIntent().getSerializableExtra("UUID");

        //获取ID信息 方法二
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        //开启选项菜单处理
        setHasOptionsMenu(true);
    }

    /**
     * 保存数据
     */
    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    public void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    /**
     * 添加视图
     */
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        //第一个参数fragment视图直接通过inflater传入资源id生成
        //第二个参数是视图的父视图，通常需要父视图类正确配置组件
        //第三个参数告知布局生成器是否将生成的视图添加给父视图，将通过activity代码的方式添加视图
        View view = inflater.inflate(R.layout.fragment_crime, parent, false);

        //启用向上导航按钮
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //判断父类是否存在
            if (NavUtils.getParentActivityName(getActivity()) != null){
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        //输入框
        etCrimeTitle = (EditText)view.findViewById(R.id.crime_title);
        etCrimeTitle.setText(mCrime.getTitle());
        //输入框监听
        etCrimeTitle.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                //设置title为输入内容
                mCrime.setTitle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {

            }

            public void afterTextChanged(Editable c) {

            }
        });
        //日期按钮
        mDateButton = (Button)view.findViewById(R.id.crime_date);
        //设置日期
        updateDate();
        //设置按钮不可点击
//        btnCrimeDate.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //fragment管理器
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                //创建DatePickerFragment对象
//                DatePickerFragment datePickerFragment = new DatePickerFragment();
                //创建DatePickerFragment对象，传递时间参数
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                //设置目标Fragment，请求代码常量
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                //显示对话框 参数一 fragment管理器 参数二 tag
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });
        //复选框
        mSolvedCheckBox = (CheckBox)view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        //选择监听
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //设置复选框已选择
                mCrime.setSolved(isChecked);
            }
        });
        
        return view;
    }

    /**
     * 响应对话框
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE) {
            //获取对话框响应的参数
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //设置时间
            mCrime.setDate(date);
            //更新时间
            updateDate();
        }
    }

    /**
     * 通知托管activity返回结果值
     */
    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK,null);
    }

    /**
     * 菜单栏点击
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //向上返回按钮
            case android.R.id.home:
                //检查元数据中是否指定了父类
                if (NavUtils.getParentActivityName(getActivity()) != null){
                    //导航至父界面
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }
}
