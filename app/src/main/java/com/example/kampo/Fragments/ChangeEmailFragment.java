package com.example.kampo.Fragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentChangeEmailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;



public class ChangeEmailFragment extends Fragment {
    FragmentChangeEmailBinding binding;

    //Email Patter For Checking The Email is Valid
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeEmailBinding.inflate(inflater, container, false);

        //Calling Update email function when user click on button
        binding.updateEmailButton.setOnClickListener(v -> updateEmail());

        return binding.getRoot();
    }

    private void updateEmail() {
        //Getting email text in a variable newEmail
        String newEmail = binding.newEmailAddress.getText().toString();

        //Check All The condition for email
        if(TextUtils.isEmpty(newEmail)){
            binding.newEmailAddress.setError("Email is required");
        }
        else if(!newEmail.trim().matches(emailPattern)){
            binding.newEmailAddress.setError("Email is Not Valid");
        }
        else {
            //Getting Current User From Firebase
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null; // Checking User not be null

            //Updating Email Address
            user.updateEmail(newEmail)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                            documentReference.update("Email",newEmail).addOnCompleteListener(task1 -> {
                               if(task1.isSuccessful()){
                                   AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
                                   assert appCompatActivity != null;
                                   appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new SettingsFragment()).addToBackStack(null).commit();
                               }
                            });
                        }
                    });
        }
    }
}