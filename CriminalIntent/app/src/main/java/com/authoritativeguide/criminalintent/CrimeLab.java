package com.authoritativeguide.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jrglxls on 2016/12/21.
 */

public class CrimeLab {
    //创建crimeLab对象
    private static CrimeLab crimeLab;
    //上下文
    private Context context;
    //ArrayList用来保存Crime
    private ArrayList<Crime> crimes;

    /**
     * 私有构造方法
     */
    private CrimeLab(Context context){
        this.context = context;
        this.crimes = new ArrayList<Crime>();
        //存入100个Crime对象
        for (int i = 0;i<100;i++){
            //创建Crime对象
            Crime crime = new Crime();
            //设置标题
            crime.setTitle("Crime NO."+i);
            //设置是否解决
            crime.setSolve(i%2==0);
            //列表添加Crime对象
            crimes.add(crime);
        }

    }

    /**
     * 单例
     */
    public static CrimeLab get(Context context){
        //如果实例不存在
        if (crimeLab == null){
            //调用构造方法来创建
            crimeLab = new CrimeLab(context.getApplicationContext());
        }
        return crimeLab;
    }

    /**
     * 返回数组列表
     */
    public ArrayList<Crime> getCrimes() {
        return crimes;
    }

    /**
     * 返回带有指定ID的Crime对象
     */
    public Crime getCrime(UUID uuid){
        //遍历crimes数组 循环变量类型 循环变量名称：要被遍历的对象
        for (Crime crime:crimes){
            //返回对应uuid的crime
            if (crime.getUuid().equals(uuid)){
                return crime;
            }
        }
        return null;
    }
}
