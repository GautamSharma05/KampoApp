package com.example.kampo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.kampo.Fragments.AppointmentFragment;
import com.example.kampo.Fragments.CategoryFragment;
import com.example.kampo.Fragments.HomeFragment;
import com.example.kampo.Fragments.ProfileFragment;
import com.example.kampo.R;
import com.example.kampo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment()).commit();
        binding.mainScreenBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()){
                    case R.id.Home: temp = new HomeFragment();
                        break;
                    case R.id.Appointment: temp = new AppointmentFragment();
                        break;
                    case R.id.Categories: temp = new CategoryFragment();
                        break;
                    case R.id.Profile: temp = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,temp).commit();
                return false;
            }
        });
    }
}