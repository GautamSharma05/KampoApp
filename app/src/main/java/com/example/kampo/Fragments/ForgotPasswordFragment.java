package com.example.kampo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.kampo.Activity.LoginActivity;
import com.example.kampo.databinding.FragmentForgotPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;


public class ForgotPasswordFragment extends Fragment {
    FragmentForgotPasswordBinding binding;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false);

        //Calling Reset Link Function when User click on button
        binding.forgotPasswordResetLinkButton.setOnClickListener(v -> resetLink());


        return binding.getRoot();
    }


    //Reset Link Function
    private void resetLink() {
        String forgotEmailAddress = binding.forgotPasswordEmailAddress.getText().toString();
        //Checking Email field not be empty
        if(TextUtils.isEmpty(forgotEmailAddress)){
            binding.forgotPasswordEmailAddress.setError("Email Address is Required");
        }
        else if(!forgotEmailAddress.trim().matches(emailPattern)){
            binding.forgotPasswordEmailAddress.setError("Email is not Valid");
        }
        else{
            mAuth.sendPasswordResetEmail(forgotEmailAddress)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Reset link send to your E-mail, Check your Inbox", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
        }
    }
}