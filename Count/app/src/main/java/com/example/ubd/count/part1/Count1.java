package com.example.ubd.count.part1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ubd.count.R;

public class Count1 extends AppCompatActivity {

    //判断当前输入位置
    int sign1 = 0;
    //判断当前位于哪一个计算步骤
    int signForStep = 0;
    //工具类实例
    P1_Tool p1_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count1);

        p1_tool = new P1_Tool();

        //各控件
        //angle
        final EditText editText1 = findViewById(R.id.P1_ET1);
        //location
        final EditText editText2 = findViewById(R.id.P1_ET2);
        TextView textView = findViewById(R.id.P1_TV);
        Button button1 = findViewById(R.id.P1_B1);
        Button button2 = findViewById(R.id.P1_B2);
        RecyclerView recyclerView = findViewById(R.id.P1_RV);
        //添加
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign1++;
                p1_tool.changeToSecond(editText1.getText().toString(),editText2.getText().toString(),sign1);
            }
        });
        //下一步
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signForStep++;
                p1_tool.stepAll(signForStep);
            }
        });
    }
}
