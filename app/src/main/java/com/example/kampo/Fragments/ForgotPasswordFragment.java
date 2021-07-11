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

import com.example.kampo.Activity.LoginActivity;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordFragment extends Fragment {
    FragmentForgotPasswordBinding binding;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false);

        binding.forgotPasswordResetLinkButton.setOnClickListener(v -> {
            resetLink();
        });
        return binding.getRoot();
    }

    private void resetLink() {
        String forgotEmailAddress = binding.forgotPasswordEmailAddress.getText().toString();
        if(TextUtils.isEmpty(forgotEmailAddress)){
            binding.forgotPasswordEmailAddress.setError("Email Address is Required");
        }
        else if(!forgotEmailAddress.trim().matches(emailPattern)){
            binding.forgotPasswordEmailAddress.setError("Email is not Valid");
        }
        else{
            mAuth.sendPasswordResetEmail(forgotEmailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Reset link send to your E-mail, Check your Inbox", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }
}