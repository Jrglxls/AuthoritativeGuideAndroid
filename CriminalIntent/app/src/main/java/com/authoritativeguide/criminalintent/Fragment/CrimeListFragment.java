package com.authoritativeguide.criminalintent.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.authoritativeguide.criminalintent.Activity.CrimeActivity;
import com.authoritativeguide.criminalintent.Activity.CrimePagerActivity;
import com.authoritativeguide.criminalintent.Model.Crime;
import com.authoritativeguide.criminalintent.Model.CrimeLab;
import com.authoritativeguide.criminalintent.R;

import java.util.ArrayList;

/**
 * Created by Jrglxls on 2016/12/22.
 */

public class CrimeListFragment extends ListFragment{
    //创建列表对象
    private ArrayList<Crime> crimes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        getActivity().setTitle("Crime");
        //先获取CrimeLab单例再获取其中的Crimes列表
        crimes = CrimeLab.get(getActivity()).getCrimes();

        /**
         * 方法一 默认
         */
        //创建一个默认的ArrayAdapter<T>类实现
        //第一个参数Context 第二个参数资源id，view对象，第三个参数数据集对象
//        ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),R.layout.simple_list_item,crimes);
//        //为内置的listview设置adapter
//        setListAdapter(adapter);

        /**
         * 方法二 自定义
         */
        CrimeAdapter crimeAdapter = new CrimeAdapter(crimes);
        setListAdapter(crimeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新列表
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //方法一 默认
        Crime crime = (Crime) getListAdapter().getItem(position);
        // TODO: 2017/1/4 自定义
//        Crime crime = (CrimeAdapter) getListAdapter().getItem(position);
        System.out.println("jjjjjjjjjj  "+crime.getTitle()+"被点击");
        //跳转到CrimeActivity
//        Intent intent = new Intent(getActivity(),CrimeActivity.class);
        //跳转到CrimePagerActivity
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        //传递ID
        intent.putExtra("UUID",crime.getUuid());
        startActivity(intent);
    }

    /**
     * 返回结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 列表适配器
     */
    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            //调用超类的构造方法来绑定Crime对象的数组列表
            //不使用预定义布局，传入0作为布局ID的参数
            super(getActivity(),0,crimes);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //自定义布局
            if (convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_crime_item,null);
            }

            //获取当前列表中的Crime对象
            Crime crime = getItem(position);

            //标题
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvTitle.setText(crime.getTitle());
            //时间
            TextView tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            tvTime.setText(crime.getDate().toString());
            //是否选择
            CheckBox cbCrime = (CheckBox) convertView.findViewById(R.id.cb_crime);
            cbCrime.setChecked(crime.getSolve());

            return convertView;
        }
    }
}
