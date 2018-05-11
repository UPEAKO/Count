package com.example.ubd.count;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ubd.count.part1.Count1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //启动不同的部分
        Button button1 = findViewById(R.id.activityPart1);
        //Button button2 = findViewById(R.id.activityPart2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Count1.class);
                startActivity(intent);
            }
        });
    }
}
