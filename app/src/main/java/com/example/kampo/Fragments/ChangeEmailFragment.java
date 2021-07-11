package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.kampo.R;
import com.example.kampo.databinding.FragmentChangeEmailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class ChangeEmailFragment extends Fragment {
    FragmentChangeEmailBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeEmailBinding.inflate(inflater, container, false);
         binding.updateEmailButton.setOnClickListener(v -> {
             updateEmail();
         });
        return binding.getRoot();
    }

    private void updateEmail() {
        String newEmail = binding.newEmailAddress.getText().toString();
        if(TextUtils.isEmpty(newEmail)){
            binding.newEmailAddress.setError("Email is required");
        }
        else if(!newEmail.trim().matches(emailPattern)){
            binding.newEmailAddress.setError("Email is Not Valid");
        }
        else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                                documentReference.update("Email",newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
                                           appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new SettingsFragment()).addToBackStack(null).commit();
                                       }
                                    }
                                });
                            }
                        }
                    });
        }
    }
}