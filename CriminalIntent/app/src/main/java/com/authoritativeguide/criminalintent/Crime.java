package com.authoritativeguide.criminalintent;

import java.util.UUID;

/**
 * Created by Jrglxls on 2016/12/20.
 *  模型
 */

public class Crime {
    private UUID uuid;
    private String title;

    public Crime(){
        //生成唯一标识符
        uuid = UUID.randomUUID();
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
