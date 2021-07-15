package com.example.kampo.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.kampo.Activity.LoginActivity;
import com.example.kampo.Adapters.SwipePageAdapter;
import com.example.kampo.R;
import com.example.kampo.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    SwipePageAdapter swipePageAdapter;
    String prevStarted = "yes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        swipePageAdapter = new SwipePageAdapter(getSupportFragmentManager(),getLifecycle());
        binding.viewpager.setAdapter(swipePageAdapter);

    }



    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            moveToSecondary();
        }
    }

    private void moveToSecondary() {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }
}