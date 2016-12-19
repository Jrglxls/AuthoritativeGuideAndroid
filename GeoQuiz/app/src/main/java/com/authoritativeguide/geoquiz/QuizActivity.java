package com.authoritativeguide.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 问题页面
 */
public class QuizActivity extends Activity implements View.OnClickListener{
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
    //答案
    private Button btnAnswer;
    //数据
    private TrueFalse[] trueFalse = new TrueFalse[]{
            new TrueFalse("111111",true),new TrueFalse("222222",true),
            new TrueFalse("333333",false),new TrueFalse("444444",true),
            new TrueFalse("555555",false),new TrueFalse("666666",true)
    };
    //当前位置
    private int position = 0;
    //是否查看过答案
    private Boolean isLookAnswer = false;

    //使用注解声明版本信息
//    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState!=null){
            //获取保存的position
            position = savedInstanceState.getInt("position",0);
        }
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
        //答案
        btnAnswer = (Button) findViewById(R.id.btn_answer);
        btnAnswer.setOnClickListener(this);

        //将ActionBar置于检查Android设备版本的条件语句中
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle("这是个啥");
            }
        }
    }

    /**
     *  设备旋转前保存数据 将position保存
     *  重新创建activity获取值
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存当前position
        outState.putInt("position",position);
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
                isLookAnswer = false;
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
            //查看答案
            case R.id.btn_answer:
                Intent intent = new Intent(QuizActivity.this,CheatActivity.class);
                //获取当前问题的答案
                boolean answer = trueFalse[position].isTrueQuestion();
                //传递当前问题的答案
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TURE,answer);
                //普通跳转
//                startActivity(intent);
                //返回结果跳转
                startActivityForResult(intent,0);
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

        if (isLookAnswer){
            message = "作弊";
        }else {
            if(tureOrFalse == answer){
                message = "正确";
            }else {
                message = "错误";
            }
        }
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回结果处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }else {
            //设置是否查看过答案
            isLookAnswer = data.getBooleanExtra(CheatActivity.EXTRA_ANDWER_SHOW,false);
        }
    }
}
