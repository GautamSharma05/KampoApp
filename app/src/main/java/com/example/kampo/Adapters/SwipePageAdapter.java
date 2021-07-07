package com.example.kampo.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kampo.SplashScreen.SplashScreen_1;
import com.example.kampo.SplashScreen.SplashScreen_2;
import com.example.kampo.SplashScreen.SplashScreen_3;

import org.jetbrains.annotations.NotNull;

public class SwipePageAdapter extends FragmentStateAdapter {


    public SwipePageAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle); }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new SplashScreen_1();
        }
        else if (position == 1) {
            return new SplashScreen_2();
        }
        else {
            return new SplashScreen_3();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

