package com.example.kampo.Fragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentWorkersProfileBinding;
import org.jetbrains.annotations.NotNull;



public class WorkersProfileFragment extends Fragment {
    String FullName;
    String ProfilePicUri;
    String ProfileThumbnail;
    String WorkerId;
    public WorkersProfileFragment(String FullName,String ProfilePicUri,String ProfileThumbnail,String WorkerId) {
        this.FullName = FullName;
        this.ProfilePicUri = ProfilePicUri;
        this.ProfileThumbnail = ProfileThumbnail;
        this.WorkerId = WorkerId;
    }

    FragmentWorkersProfileBinding binding;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkersProfileBinding.inflate(inflater, container, false);
        binding.profileWorkerName.setText(FullName);

        //Getting Image From Firebase Database Using Glide Library
        Glide.with(getContext()).load(ProfileThumbnail).placeholder(R.drawable.avatar).into(binding.profileImageThumbnail);
        Glide.with(getContext()).load(ProfilePicUri).placeholder(R.drawable.avatar).into(binding.workerProfileImage);

        //Book me Button
        binding.bookMeButton.setOnClickListener(v -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new SelectedSpecialistFragment(WorkerId,FullName,ProfilePicUri)).addToBackStack(null).commit();
        });
        return binding.getRoot();
    }
}