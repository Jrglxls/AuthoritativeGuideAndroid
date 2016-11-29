package com.authoritativeguide.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{
    //确定
    private Button btnSure;
    //取消
    private Button btnFalse;
    //内容
    private TextView tvContent;
    //下一个
    private Button btnNext;
    //上一个
    private Button btnPre;
    //数据
    private TrueFalse[] trueFalse = new TrueFalse[]{
            new TrueFalse("111111",true),new TrueFalse("222222",true),
            new TrueFalse("333333",false),new TrueFalse("444444",true),
            new TrueFalse("555555",false),new TrueFalse("666666",true)
    };
    //当前位置
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //确定
        btnSure = (Button) findViewById(R.id.btn_true);
        btnSure.setOnClickListener(this);
        //取消
        btnFalse = (Button) findViewById(R.id.btn_false);
        btnFalse.setOnClickListener(this);
        //内容
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContent.setOnClickListener(this);
        setText();
        //下一个
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        //上一个
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnPre.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //确定
            case R.id.btn_true:
//                Toast.makeText(this,"你是傻逼",Toast.LENGTH_SHORT).show();
                checkAnswer(true);
                break;
            //取消
            case R.id.btn_false:
//                Toast.makeText(this,"你不是傻逼",Toast.LENGTH_SHORT).show();
                checkAnswer(false);
                break;
            //下一个
            case R.id.tv_content:
            case R.id.btn_next:
                position = (position + 1)%trueFalse.length;
                setText();
                break;
            //上一个
            case R.id.btn_pre:
                if (position==0){
                    Toast.makeText(this,"已是第一个问题",Toast.LENGTH_SHORT).show();
                }else {
                    position = (position - 1)%trueFalse.length;
                    setText();
                }
                break;
        }
    }

    /**
     * 设置文本
     */
    private void setText(){
        String question = trueFalse[position].getQuestion();
        tvContent.setText(question);
    }

    /**
     * 检查答案
     */
    private void checkAnswer(boolean tureOrFalse){
        //获取当前问题答案
        boolean answer = trueFalse[position].isTrueQuestion();
        //显示信息
        String message ;

        if(tureOrFalse == answer){
            message = "正确";
        }else {
            message = "错误";
        }

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
