package com.example.kampo.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kampo.Activity.MainActivity;
import com.example.kampo.Activity.RegistrationActivity;
import com.example.kampo.Activity.loaderActivity;

import com.example.kampo.R;
import com.example.kampo.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(binding.userEmailAddressText.getText()).toString().trim();
                String password = Objects.requireNonNull(binding.loginPasswordText.getText()).toString().trim();
                logIn(email,password);

            }
        });
        binding.signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegistrationActivity.class);
            startActivity(intent);
        });
        binding.forgotPasswordText.setOnClickListener(v -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.loginContainer,new ForgotPasswordFragment()).addToBackStack(null).commit();
        });

        return binding.getRoot();
    }

    private void logIn(String email, String password) {
        if(TextUtils.isEmpty(email)){
            binding.userEmailAddressText.setError("Email is required");
        }
        else if(!email.trim().matches(emailPattern)){
            binding.userEmailAddressText.setError("Email is Not Valid");
        }
        else if(TextUtils.isEmpty(password)){
            binding.loginPasswordText.setError("Password is required");
        }
        else{

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUi(user);

                            } else {
                                Toast.makeText(getContext(), "May Be Your Email or Password Wrong!", Toast.LENGTH_SHORT).show();
                                updateUi(null);

                            }
                        }
                    });
        }
        }

    private void updateUi(FirebaseUser user) {
        if(user != null){
            Intent intent = new Intent(getContext(), loaderActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getContext(), "Try Again!", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
    }


}
