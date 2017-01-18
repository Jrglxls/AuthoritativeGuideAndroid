package com.bignerdranch.android.criminalintent.Model;

import java.util.Date;
import java.util.UUID;

public class Crime {
    //设备唯一标识符
    private UUID mId;
    //标题
    private String mTitle;
    //日期
    private Date mDate;
    //是否解决
    private boolean mSolved;

    public Crime() {
        //生成唯一标识符
        mId = UUID.randomUUID();
        //当前默认日期
        mDate = new Date();
    }

    //覆盖前直接返回对象类名和内存地址的字符串信息
    @Override
    public String toString() {
        return mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }


}
