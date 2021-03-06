package com.bignerdranch.android.criminalintent.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
    //保存文件名称常量
    private static final String FILENAME = "crimes.json";

    //ArrayList用来保存Crime
    private ArrayList<Crime> mCrimes;
    //创建crimeLab对象
    private static CrimeLab sCrimeLab;
    //上下文
    private Context mAppContext;
    //创建CriminalIntentJsonSerializer对象
    private CriminalIntentJsonSerializer criminalIntentJsonSerializer;

    /**
     * 私有构造方法
     */
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
//        mCrimes = new ArrayList<Crime>();
        criminalIntentJsonSerializer = new CriminalIntentJsonSerializer(mAppContext,FILENAME);
        try {
            //加载Crimes数据
            mCrimes = criminalIntentJsonSerializer.loadCrimes();
            System.out.println("jjjjjjj 加载成功   ");
        } catch (IOException e) {
            //加载失败，新建一个空数据列表
            mCrimes = new ArrayList<Crime>();
            System.out.println("jjjjjjj e.toString()   "+e.toString());
            e.printStackTrace();
        }

        //存入100个Crime对象
//        for (int i = 0;i<100;i++){
//            //创建Crime对象
//            Crime crime = new Crime();
//            //设置标题
//            crime.setTitle("Crime NO."+i);
//            //设置是否解决
//            crime.setSolved(i%2==0);
//            //列表添加Crime对象
//            mCrimes.add(crime);
//        }
    }

    /**
     * 单例
     */
    public static CrimeLab get(Context c) {
        //如果实例不存在
        if (sCrimeLab == null) {
            //调用构造方法来创建
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    /**
     * 返回带有指定ID的Crime对象
     */
    public Crime getCrime(UUID id) {
        //遍历crimes数组 循环变量类型 循环变量名称：要被遍历的对象
        for (Crime c : mCrimes) {
            //返回对应uuid的crime
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    /**
     * 新建
     */
    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    /**
     * 返回数组列表
     */
    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    /**
     * 保存Crimes
     */
    public boolean saveCrimes(){
        try {
            criminalIntentJsonSerializer.saveCrimes(mCrimes);
            System.out.println("jjjjjjjjjj   保存成功");
            return true;
        }catch (Exception exception){
            System.out.println("jjjjjjjjjj   保存失败"+exception.toString());
            return false;
        }
    }

    /**
     *  删除Crime
     */
    public void deleteCrime(Crime c) {
        mCrimes.remove(c);
    }
}

