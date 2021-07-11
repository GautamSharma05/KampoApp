package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentWorkersProfileBinding;


public class WorkersProfileFragment extends Fragment {
    String FullName;
    String ProfilePicUri;
    String ProfileThumbnail;
    public WorkersProfileFragment(String FullName,String ProfilePicUri,String ProfileThumbnail) {
        this.FullName = FullName;
        this.ProfilePicUri = ProfilePicUri;
        this.ProfileThumbnail = ProfileThumbnail;
    }

    FragmentWorkersProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkersProfileBinding.inflate(inflater, container, false);
        binding.profileWorkerName.setText(FullName);
        Glide.with(getContext()).load(ProfileThumbnail).placeholder(R.drawable.avatar).into(binding.profileImageThumbnail);
        Glide.with(getContext()).load(ProfilePicUri).placeholder(R.drawable.avatar).into(binding.workerProfileImage);
        return binding.getRoot();
    }
}