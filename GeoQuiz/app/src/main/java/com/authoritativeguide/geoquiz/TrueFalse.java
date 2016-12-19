package com.authoritativeguide.geoquiz;

/**
 * Created by Jrglxls on 2016/11/24.
 * 实体模型
 */

public class TrueFalse {
    //问题
    private String question;
    //是否正确
    private boolean trueQuestion;

    //构造方法
    public TrueFalse(String question,boolean trueQuestion){
        this.question = question;
        this.trueQuestion = trueQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isTrueQuestion() {
        return trueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        this.trueQuestion = trueQuestion;
    }
}
