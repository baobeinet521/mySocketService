package com.zdmysocketservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zd.mysocketservice.R;

public class MainActivity extends AppCompatActivity {

    private Button mServiceTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mServiceTestBtn = findViewById(R.id.go_to_service);
        mServiceTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ServicesTestTwoActivity.class);
                startActivity(intent);

            }
        });
    }
}
