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


public class SplashScreen_3 extends Fragment {
    FragmentSplashScreen3Binding binding;
    Animation topanim,bottomanim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreen3Binding.inflate(inflater,container,false);
        binding.getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

//        Animation
        topanim= AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        binding.splashScreen3Image.setAnimation(topanim);
        binding.splashScreen3Heading.setAnimation(bottomanim);
        binding.splashScreen3Text.setAnimation(bottomanim);
        binding.getStartedButton.setAnimation(bottomanim);
        return binding.getRoot();
    }
}