package com.example.kampo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.kampo.R;

public class loaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loader);
        new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(),MainActivity.class)),3000);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, loaderActivity.class));
        finish();
    }
}