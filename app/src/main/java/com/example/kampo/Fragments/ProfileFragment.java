package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userID = mAuth.getUid();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        fStore.collection("Users").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
               if(value != null){
                    binding.userFullName.setText(value.getString("FullName"));
                    binding.userPhoneNumber.setText(value.getString("PhoneNumber"));
                    binding.profileUserEmail.setText(value.getString("Email"));
                    binding.userGender.setText(value.getString("Gender"));
                   Glide.with(getContext()).load(value.getString("UserProfilePic")).placeholder(R.drawable.avatar).into(binding.profileImage);
               }
            }
        });
        binding.editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new EditProfileFragment()).addToBackStack(null).commit();
            }
        });

        return binding.getRoot();
    }
}