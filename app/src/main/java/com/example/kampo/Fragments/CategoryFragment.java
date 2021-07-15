package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.kampo.R;
import com.example.kampo.databinding.FragmentCategoryBinding;

import org.jetbrains.annotations.NotNull;


public class CategoryFragment extends Fragment {
    FragmentCategoryBinding binding;
    Animation leftAnim, rightAnim,topAnim,bottomAnim;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCategoryBinding.inflate(inflater,container,false);


        //All Animation Work Are Below in CategoryFragment
        leftAnim= AnimationUtils.loadAnimation(getContext(),R.anim.left_animation);
        rightAnim= AnimationUtils.loadAnimation(getContext(),R.anim.right_animation);
        topAnim= AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        binding.hairColor.setAnimation(topAnim);
        binding.hairCut.setAnimation(topAnim);
        binding.hairStyling.setAnimation(leftAnim);
        binding.massage.setAnimation(leftAnim);
        binding.hairTreatment.setAnimation(rightAnim);
        binding.categoryShaving.setAnimation(rightAnim);
        binding.facials.setAnimation(bottomAnim);
        binding.trimming.setAnimation(bottomAnim);

        //Services Booking Screen
        binding.hairColor.setOnClickListener(v -> getAppointmentScreen());
        binding.hairCut.setOnClickListener(v -> getAppointmentScreen());
        binding.hairStyling.setOnClickListener(v -> getAppointmentScreen());
        binding.hairTreatment.setOnClickListener(v -> getAppointmentScreen());
        binding.trimming.setOnClickListener(v -> getAppointmentScreen());
        binding.massage.setOnClickListener(v -> getAppointmentScreen());
        binding.facials.setOnClickListener(v -> getAppointmentScreen());
        binding.categoryShaving.setOnClickListener(v -> getAppointmentScreen());


        return binding.getRoot();

    }

    private void getAppointmentScreen() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
        assert appCompatActivity != null;
        appCompatActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer,new AppointmentFragment())
                .addToBackStack(null)
                .commit();
    }
}