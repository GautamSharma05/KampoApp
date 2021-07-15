package com.example.kampo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.kampo.Activity.LoginActivity;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    //Creating Firebase Instances
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    //Getting Current User Id in Variable
    String userID = mAuth.getUid();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);

        //Getting User Values From Firebase Database
        fStore.collection("Users").document(userID).addSnapshotListener((value, error) -> {
           if(value != null){
                binding.userFullName.setText(value.getString("FullName"));
                binding.userPhoneNumber.setText(value.getString("PhoneNumber"));
                binding.profileUserEmail.setText(value.getString("Email"));
                binding.userGender.setText(value.getString("Gender"));
               Glide.with(getContext()).load(value.getString("UserProfilePic")).placeholder(R.drawable.avatar).into(binding.profileImage);
           }
        });

        //Send User to edit profile Screen When user Click on edit profile button
        binding.editProfileButton.setOnClickListener(v -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new EditProfileFragment()).addToBackStack(null).commit();
        });

        //Send User to Setting Screen When user Click on edit profile button
        binding.settingsText.setOnClickListener(v -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new SettingsFragment()).addToBackStack(null).commit();
        });

        //logOut user
        binding.logOutText.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}