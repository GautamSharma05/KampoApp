package com.example.kampo.SplashScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.kampo.R;
import com.example.kampo.databinding.FragmentSplashScreen2Binding;


public class SplashScreen_2 extends Fragment {
    FragmentSplashScreen2Binding binding;
    Animation topanim,bottomanim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSplashScreen2Binding.inflate(inflater,container,false);
//        Animation
        topanim= AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        binding.splashScreen2Image.setAnimation(topanim);
        binding.splashScreen2Heading.setAnimation(bottomanim);
        binding.splashScreen2Text.setAnimation(bottomanim);
        return binding.getRoot();
    }
}