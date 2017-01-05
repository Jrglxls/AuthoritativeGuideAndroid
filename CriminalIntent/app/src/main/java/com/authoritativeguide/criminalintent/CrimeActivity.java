package com.authoritativeguide.criminalintent;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
//public class MainActivity extends FragmentActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//
//        //获取FragmentManager
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        //获取Fragment
//        Fragment fragment = fragmentManager.findFragmentById(R.id.crime_fragment);
//        //如果fragment不存在，创建并提交新的fragment事务
//        if (fragment == null){
//            fragment = new CrimeFragment();
//            //创建一个新的fragment事务，加入一个添加操作，然后提交该事物
//            //创建并返回FragmentTransaction实例，使用了一个fluent interface接口方法
//            fragmentManager.beginTransaction()
//                    //核心，参数前者为容器视图资源id，后者为新创建的CrimeFragment
//                    .add(R.id.crime_fragment,fragment).commit();
//        }
//    }

    @Override
    protected Fragment createFragment() {
//        return new CrimeFragment();
        UUID uuid = (UUID) getIntent().getSerializableExtra("UUID");

        return CrimeFragment.newInstance(uuid);
    }

}
