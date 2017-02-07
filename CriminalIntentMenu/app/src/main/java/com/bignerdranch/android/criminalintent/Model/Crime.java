package com.bignerdranch.android.criminalintent.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Crime {
    //UUID常量
    private static final String JSON_UUID = "UUID";
    //标题常量
    private static final String JSON_TITLE = "TITLE";
    //日期常量
    private static final String JSON_DATE = "DATE";
    //解决常量
    private static final String JSON_SOLVED = "SOLVED";

    //设备唯一标识符
    private UUID mId;
    //标题
    private String mTitle;
    //日期
    private Date mDate;
    //是否解决
    private boolean mSolved;

    /**
     * 无参数构造方法
     */
    public Crime() {
        //生成唯一标识符
        mId = UUID.randomUUID();
        //当前默认日期
        mDate = new Date();
    }

    /**
     * JSONObject构造方法
     */
    public Crime(JSONObject jsonObject){
        try {
            //设置唯一标识符
            mId = UUID.fromString(jsonObject.getString(JSON_UUID));
            //标题不为空设置标题
            if (jsonObject.has(JSON_TITLE)){
                mTitle = jsonObject.getString(JSON_TITLE);
            }
            //设置是否解决
            mSolved = jsonObject.getBoolean(JSON_SOLVED);
            //设置时间
            mDate = new Date(jsonObject.getLong(JSON_DATE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 覆盖前直接返回对象类名和内存地址的字符串信息
     */
    @Override
    public String toString() {
        return mTitle;
    }

    /**
     * 以json格式保存Crime
     */
    public JSONObject toJson(){
        //创建JSONObject对象
        JSONObject jsonObject = new JSONObject();
        try {
            //UUID
            jsonObject.put(JSON_UUID,mId.toString());
            //标题
            jsonObject.put(JSON_TITLE,mTitle);
            //日期
            jsonObject.put(JSON_DATE,mDate.getTime());
            //解决
            jsonObject.put(JSON_SOLVED,mSolved);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
