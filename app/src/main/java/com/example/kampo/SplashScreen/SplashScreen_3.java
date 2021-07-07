package com.example.kampo.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kampo.Activity.RegistrationActivity;
import com.example.kampo.databinding.FragmentSplashScreen3Binding;


public class SplashScreen_3 extends Fragment {
    FragmentSplashScreen3Binding binding;

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
        return binding.getRoot();
    }
}