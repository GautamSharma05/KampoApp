package com.example.kampo.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.kampo.Activity.RegistrationActivity;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentSplashScreen3Binding;

import org.jetbrains.annotations.NotNull;


public class SplashScreen_3 extends Fragment {
    FragmentSplashScreen3Binding binding;
    Animation topAnim,bottomAnim;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreen3Binding.inflate(inflater,container,false);
        binding.getStartedButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegistrationActivity.class);
            startActivity(intent);
        });

//        Animation
        topAnim= AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        binding.splashScreen3Image.setAnimation(topAnim);
        binding.splashScreen3Heading.setAnimation(bottomAnim);
        binding.splashScreen3Text.setAnimation(bottomAnim);
        binding.getStartedButton.setAnimation(bottomAnim);
        return binding.getRoot();
    }
}