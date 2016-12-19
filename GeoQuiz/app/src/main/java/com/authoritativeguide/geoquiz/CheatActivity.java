package com.authoritativeguide.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jrglxls on 2016/12/1.
 * 答案页面
 */

public class CheatActivity extends Activity{
    //传递key值
    public static final String EXTRA_ANSWER_IS_TURE = "extra_answer_is_true";
    //是否查看答案
    public static final String EXTRA_ANDWER_SHOW = "extra_answer_show";
    //问题答案
    private boolean answer;
    //答案显示文本
    private TextView tvAnswer;
    //答案显示按钮
    private Button btnAnswer;
    //设备版本号
    private TextView tvApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        //默认为答案未看过
        setAnswerShownResult(false);

        //获取传递的答案
        answer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TURE,false);
        //答案显示文本
        tvAnswer = (TextView) findViewById(R.id.tv_answer);
        //答案显示按钮
        btnAnswer = (Button) findViewById(R.id.btn_answer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示答案
                if (answer){
                    tvAnswer.setText("正确");
                }else {
                    tvAnswer.setText("错误");
                }
                //设置答案已看过
                setAnswerShownResult(true);
            }
        });
        //设备API
        tvApi = (TextView) findViewById(R.id.tv_api);
        tvApi.setText("API Lever  "+String.valueOf(Build.VERSION.SDK_INT));
    }

    /**
     *  设置是否查看过答案
     */
    private void setAnswerShownResult(Boolean isAnswerShown){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANDWER_SHOW,isAnswerShown);
        //返回结果
        setResult(RESULT_OK,intent);
    }
}
