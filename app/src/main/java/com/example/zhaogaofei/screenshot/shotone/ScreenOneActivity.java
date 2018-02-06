package com.example.zhaogaofei.screenshot.shotone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhaogaofei.screenshot.R;

public class ScreenOneActivity extends AppCompatActivity {
    public static final int REQUEST_MEDIA_PROJECTION = 0x2893;

    private ImageView imageView;

    public static void start(Context context) {
        Intent intent = new Intent(context, ScreenOneActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_one);

        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.iv_screen_one_show);
        Button button = (Button) findViewById(R.id.bt_screen_one);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestScreenShot();
            }
        });
    }

    public void requestScreenShot() {
        if (Build.VERSION.SDK_INT >= 21) {
            startActivityForResult(
                    ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE)).createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION
            );
        } else {
            toast("版本过低,无法截屏");
        }
    }

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION: {

                final ScreenPage screenPage = new ScreenPage(this, data);
                // 截屏时会有系统提示，如果立刻截图会将系统提示框的半透明的背景带入，因此延后截屏，等弹出框消失后。
                imageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (resultCode == -1 && data != null) {
                            screenPage.startScreenShot(new ScreenPage.OnShotListener() {
                                @Override
                                public void onFinish(Bitmap bitmap) {
                                    toast("shot finish!");
                                    Log.e("=====", "====" + (bitmap == null));
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }
                }, 100);
            }
        }
    }

}
