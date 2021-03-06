package com.authoritativeguide.criminalintent.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.authoritativeguide.criminalintent.R;

/**
 * Created by Jrglxls on 2016/12/22.
 * 抽象类
 */

public abstract class SingleFragmentActivity extends FragmentActivity{
    //抽象方法，可以用来实例化新的fragment
    //本类的子类会实现此方法返回一个有activity托管的fragment实例
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //获取Fragment
        Fragment fragment = fragmentManager.findFragmentById(R.id.crime_fragment);
        //如果fragment不存在，创建并提交新的fragment事务
        if (fragment == null){
            fragment = createFragment();
            //创建一个新的fragment事务，加入一个添加操作，然后提交该事物
            //创建并返回FragmentTransaction实例，使用了一个fluent interface接口方法
            fragmentManager.beginTransaction()
                    //核心，参数前者为容器视图资源id，后者为新创建的CrimeFragment
                    .add(R.id.crime_fragment,fragment).commit();
        }
    }
}
