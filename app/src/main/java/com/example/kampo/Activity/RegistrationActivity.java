package com.example.kampo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kampo.Fragments.SignUpFragment;
import com.example.kampo.R;
import com.example.kampo.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {
    ActivityRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.registrationContainer,new SignUpFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();}

}