package com.example.kampo.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.kampo.Activity.LoginActivity;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentSettingsBinding;
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
        //Sending user to Change Email Address Screen
        binding.changeEmailAdressText.setOnClickListener(v -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new ChangeEmailFragment()).addToBackStack(null).commit();
        });

        //Sending user to Change Password Screen
        binding.changePasswordText.setOnClickListener(v ->{
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new ChangePasswordFragment()).addToBackStack(null).commit();

        });

        binding.companyEmail.setOnClickListener(v -> sendEmail());  //Contact us
        binding.emailIcon.setOnClickListener(v -> sendEmail());     //Contact us
        binding.phoneIcon.setOnClickListener(v -> makePhoneCall()); //Contact us
        binding.phoneNumber.setOnClickListener(v -> makePhoneCall());   //Contact us

        //Delete user From Database
        binding.deleteUserText.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null;
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                            documentReference.delete().addOnCompleteListener(task1 -> {
                              if(task1.isSuccessful()){
                                  Intent intent = new Intent(getActivity(), LoginActivity.class);
                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                  startActivity(intent);
                              }
                            });
                        }
                    });
        });

        return binding.getRoot();
    }


    //Sending User to Email Screen
    private void sendEmail() {
        String email = binding.companyEmail.getText().toString().trim();
        String [] recipients = email.split(",");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Help Me");
        intent.setType("text/rfc822");
        try{
            startActivity(Intent.createChooser(intent,"Choose Options For Send Email"));
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //Sending User to Phone Call Screen
    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8854082108"));
        startActivity(intent);
    }
}