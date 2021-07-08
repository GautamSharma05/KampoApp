package com.example.kampo.SplashScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.kampo.R;
import com.example.kampo.databinding.FragmentSplashScreen1Binding;

import org.jetbrains.annotations.NotNull;


public class SplashScreen_1 extends Fragment {
   FragmentSplashScreen1Binding binding;
    Animation topAnim,bottomAnim;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreen1Binding.inflate(inflater,container,false);

//        Animation
        topAnim= AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(getActivity(),R.anim.bottom_animation);
        binding.splashScreen1Image.setAnimation(topAnim);
        binding.splashScreen1Heading.setAnimation(bottomAnim);
        binding.splashScreen1Text.setAnimation(bottomAnim);
        return binding.getRoot();
    }
}