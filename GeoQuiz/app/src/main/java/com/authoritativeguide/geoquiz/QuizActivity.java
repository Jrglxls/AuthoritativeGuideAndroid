package com.authoritativeguide.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{
    //确定
    private Button btnSure;
    //取消
    private Button btnFalse;


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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //确定
            case R.id.btn_true:
                Toast.makeText(this,"你是傻逼",Toast.LENGTH_SHORT).show();
                break;
            //取消
            case R.id.btn_false:
                Toast.makeText(this,"你不是傻逼",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
