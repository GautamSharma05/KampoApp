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
<<<<<<< Updated upstream

public class SplashScreen_1 extends Fragment {
     FragmentSplashScreen1Binding binding;
     Animation topanim,bottomanim;
=======


public class SplashScreen_1 extends Fragment {
    FragmentSplashScreen1Binding binding;
>>>>>>> Stashed changes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreen1Binding.inflate(inflater,container,false);
<<<<<<< Updated upstream
//        Animation
        topanim= AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomanim=AnimationUtils.loadAnimation(getActivity(),R.anim.bottom_animation);
        binding.splashScreen1Image.setAnimation(topanim);
        binding.splashScreen1Heading.setAnimation(bottomanim);
        binding.splashScreen1Text.setAnimation(bottomanim);
        return binding.getRoot();

=======
        return binding.getRoot();
>>>>>>> Stashed changes
    }
}