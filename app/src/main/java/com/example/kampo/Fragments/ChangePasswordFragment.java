package com.example.kampo.Fragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentChangePasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.NotNull;


public class ChangePasswordFragment extends Fragment {
    FragmentChangePasswordBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater,container,false);

        //Calling Update Password function when user click on button
        binding.updatePasswordButton.setOnClickListener(v -> updatePassword());



        return binding.getRoot();
    }
    //Update Password Fragment
    private void updatePassword() {

        //Getting Values From user
        String currentPassword = binding.currentPassword.getText().toString();
        String newPassword = binding.newPassword.getText().toString().trim();
        String confirmPassword = binding.confirmPassword.getText().toString().trim();

        //Checking Condition Password Field not be empty
        if(TextUtils.isEmpty(currentPassword)){
            binding.currentPassword.setError("Current Password is Required");
        }
        else if(TextUtils.isEmpty(newPassword)){
            binding.newPassword.setError("New Password is Required");
        }
        else if(TextUtils.isEmpty(confirmPassword)){
            binding.confirmPassword.setError("Confirm Password is Required");
        }
        else if(newPassword.length() < 6){
            binding.newPassword.setError("Password Must be equal to 6 character or greater than 6");
        }
        else if(!confirmPassword.equals(newPassword)){
            binding.confirmPassword.setError("Password and Confirm Password must be same");
        }
        else {

            //Getting Current password
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null; //Checking User not be null


            //Updating a user Password
            user.updatePassword(newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
                            assert appCompatActivity != null;
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new SettingsFragment()).addToBackStack(null).commit();
                        }
                    });


        }
    }
}