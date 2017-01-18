package com.bignerdranch.android.criminalintent.Activity;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;

import com.bignerdranch.android.criminalintent.Fragment.CrimeFragment;
import com.bignerdranch.android.criminalintent.Model.Crime;
import com.bignerdranch.android.criminalintent.Model.CrimeLab;
import com.bignerdranch.android.criminalintent.R;

public class CrimePagerActivity extends FragmentActivity {
    //ViewPager
    private ViewPager mViewPager;
    //Crimes列表
    private ArrayList<Crime> crimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化ViewPager类
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        //设置布局
        setContentView(mViewPager);

        //Crimes列表
        crimes = CrimeLab.get(this).getCrimes();

        //获取activity的FragmentManager实例
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                //返回列表项目
                return crimes.size();
            }

            @Override
            public Fragment getItem(int position) {
                //获取指定位置的Crime对象的UUID
                UUID crimeId =  crimes.get(position).getId();
                //根据实例返回一个有效配置的CrimeFragment
                return CrimeFragment.newInstance(crimeId);
            }
        });

        //设置滑动监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                //获取当前的crime对象
                Crime crime = crimes.get(position);
                if (crime.getTitle()!=null){
                    //设置导航标题
                    setTitle(crime.getTitle());
                }
            }

            public void onPageScrollStateChanged(int state) {

            }
        });

        //获取传递的UUID
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        //遍历Crime列表
        for (int i = 0; i < crimes.size(); i++) {
            //找到对应的Crime对象
            if (crimes.get(i).getId().equals(crimeId)) {
                //设置为当前页
                mViewPager.setCurrentItem(i);
                break;
            } 
        }
    }
}
