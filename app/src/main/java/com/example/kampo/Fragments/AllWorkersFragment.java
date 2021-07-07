package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kampo.R;
import com.example.kampo.databinding.FragmentAllWorkersBinding;


public class AllWorkersFragment extends Fragment {
    FragmentAllWorkersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllWorkersBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}