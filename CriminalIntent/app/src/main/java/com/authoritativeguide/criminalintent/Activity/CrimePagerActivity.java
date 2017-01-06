package com.authoritativeguide.criminalintent.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.authoritativeguide.criminalintent.Fragment.CrimeFragment;
import com.authoritativeguide.criminalintent.Model.Crime;
import com.authoritativeguide.criminalintent.Model.CrimeLab;
import com.authoritativeguide.criminalintent.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jrglxls on 2017/1/5.
 */

public class CrimePagerActivity extends FragmentActivity {
    private ViewPager viewPager;
    //Crime列表
    private ArrayList<Crime> crimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化ViewPager类
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        //设置布局
        setContentView(viewPager);

        //Crime列表
        crimes = CrimeLab.get(this).getCrimes();

        //获取activity的FragmentManager实例
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                //获取指定位置的Crime实例
                Crime crime = crimes.get(position);
                //根据实例返回一个有效配置的CrimeFragment
                return CrimeFragment.newInstance(crime.getUuid());
            }

            @Override
            public int getCount() {
                //返回列表项目
                return crimes.size();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //获取当前的crime对象
                Crime crime = crimes.get(position);
                if (crime.getTitle()!=null){
                    //设置导航标题
                    setTitle(crime.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //获取传递的ID
        UUID uuid = (UUID) getIntent().getSerializableExtra("UUID");
        //遍历Crime列表
        for (int i = 0;i < crimes.size();i++){
            //找到对应的Crime对象
            if (crimes.get(i).getUuid().equals(uuid)){
                //设置为当前页
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
