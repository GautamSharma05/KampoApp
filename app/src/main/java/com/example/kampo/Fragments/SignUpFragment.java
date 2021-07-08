package com.example.kampo.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kampo.Activity.MainActivity;

import com.example.kampo.Activity.loaderActivity;
import com.example.kampo.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore= FirebaseFirestore.getInstance();
    String userFullName,email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        progressBar = new ProgressDialog(getContext());
        progressBar.setMessage("Sign Up...");
        progressBar.setCancelable(false);
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        return binding.getRoot();
    }

    private void signUp() {
        userFullName = binding.fullNameText.getText().toString().trim();
        email = binding.emailAdressText.getText().toString().trim();
        String password = binding.passwordText.getText().toString();
        if (TextUtils.isEmpty(userFullName)) {
            binding.fullNameText.setError("Full Name is Required");
        }
        else if(TextUtils.isEmpty(email)){
            binding.emailAdressText.setError("Email is required");
        }
        else if(!email.trim().matches(emailPattern)){
            binding.emailAdressText.setError("Email is Not Valid");
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordText.setError("Password is required");
        }
        else if(password.length() < 6){
            binding.passwordText.setError("Password Must be equal to 6 character or greater than 6");
        }
        else if(!binding.checkBox.isChecked()){
            binding.checkBoxError.setVisibility(View.VISIBLE);
        }
        else {
//            progressBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();

                        DocumentReference documentReference = fStore.collection("Users").document(Objects.requireNonNull(mAuth.getUid()));
                        Map<String, Object> users = new HashMap<>();
                        users.put("FullName", userFullName);
                        users.put("Email", email);
                        users.put("UserId", mAuth.getUid());
                        users.put("UserProfilePic", "Default");
                        documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                updateUI(user);
//                                progressBar.dismiss();
                            }

                        });
                    }
                    else {
                        Toast.makeText(getContext(), "May be Email is Already in used", Toast.LENGTH_SHORT).show();
                        updateUI(null);
//                        progressBar.dismiss();
                    }
                }
            });
        }
    }
    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent intent = new Intent(getContext(), loaderActivity.class);
            intent.putExtra("Name",userFullName);
            intent.putExtra("Email",email);
            startActivity(intent);

        }
        else{
            Toast.makeText(getContext(), "Try Again Some Time!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}