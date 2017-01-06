package com.authoritativeguide.criminalintent.Model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Jrglxls on 2016/12/20.
 *  模型
 */

public class Crime {
    //设备唯一标识符
    private UUID uuid;
    //标题
    private String title;
    //日期
    private Date date;
    //是否解决
    private Boolean isSolve;

    public Crime(){
        //生成唯一标识符
        uuid = UUID.randomUUID();
        //当前默认日期
        date = new Date();
    }

    //覆盖前直接返回对象类名和内存地址的字符串信息
    @Override
    public String toString() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSolve() {
        return isSolve;
    }

    public void setSolve(Boolean solve) {
        isSolve = solve;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
