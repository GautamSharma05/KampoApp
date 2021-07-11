package com.example.kampo.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kampo.Activity.LoginActivity;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentSettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;


public class SettingsFragment extends Fragment {
FragmentSettingsBinding binding;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater,container,false);
        binding.changeEmailAdressText.setOnClickListener(v -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new ChangeEmailFragment()).addToBackStack(null).commit();
        });
        binding.changePasswordText.setOnClickListener(v ->{
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new ChangePasswordFragment()).addToBackStack(null).commit();

        });
        binding.companyEmail.setOnClickListener(v ->{
            sendEmail();
        });
        binding.emailIcon.setOnClickListener(v ->{
            sendEmail();
        });
        binding.phoneIcon.setOnClickListener(v ->{
            makePhoneCall();
        });
        binding.phoneNumber.setOnClickListener(v -> {
            makePhoneCall();
        });
        binding.deleteUserText.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                                documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                      if(task.isSuccessful()){
                                          Intent intent = new Intent(getActivity(), LoginActivity.class);
                                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                          startActivity(intent);
                                      }
                                    }
                                });
                            }
                        }
                    });
        });

        return binding.getRoot();
    }
    private void sendEmail() {
        String email = binding.companyEmail.getText().toString().trim();
        String [] recipents = email.split(",");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipents);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Help Me");
        intent.setType("text/rfc822");
        try{
            startActivity(Intent.createChooser(intent,"Choose Options For Send Email"));
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8854082108"));
        startActivity(intent);
    }
}