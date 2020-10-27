package com.example.mapaturisticodeloja;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splash_Activity extends Activity {
    private final int DURACION_SPLASE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent(splash_Activity.this, MainActivity.class );
                startActivity(intent);
                finish();


            };
        }, DURACION_SPLASE);
    }
}