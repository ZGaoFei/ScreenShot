package com.example.zhaogaofei.screenshot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zhaogaofei.screenshot.shotone.ScreenOneActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Button btOne = (Button) findViewById(R.id.bt_one);
        btOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_one:
                ScreenOneActivity.start(this);
                break;
        }
    }
}
