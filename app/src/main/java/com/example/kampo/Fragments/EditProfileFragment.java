package com.example.kampo.Fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    ArrayAdapter<String> arrayAdapter; //Array Adapter
    Uri selectedImage;  //Image Url
    String downloadUrl; //Download Image Url
    ProgressDialog progressBar; //ProgressBar
    String [] gender = {"Male","Female","Others"};
    String genderText;

    //Create Firebase Database Instance
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage =FirebaseStorage.getInstance();


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);

        //Setup progressBar
        progressBar = new ProgressDialog(getContext());
        progressBar.setMessage("Wait till Image Updated...");
        progressBar.setCancelable(false);

        //Getting User Registered Email From Database
        fStore.collection("Users").document(Objects.requireNonNull(mAuth.getUid())).addSnapshotListener((value, error) -> {
           if(value != null){
               binding.editProfileUserEmail.setText(value.getString("Email"));
           }
        });


        //ActivityResultLauncher help us to open Gallery For picking Image from Gallery
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
                    binding.changeProfileImage.setImageURI(result);
                    selectedImage = result;
                    progressBar.show();

                    //Store Image in Cloud Storage Database Firebase
                    StorageReference reference = storage.getReference().child("Profiles").child(mAuth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                progressBar.dismiss();
                                downloadUrl = uri.toString(); //Download Url
                            }) ;
                        }
                    });
                });

        //Set User new Image at the place of default image
        binding.changeProfileImage.setOnClickListener(v -> mGetContent.launch("image/*"));

        //Options for male,female,other
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, gender);
        binding.spinner.setAdapter(arrayAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              genderText = binding.spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Calling Update Profile Function when User click on Button
        binding.updateProfileButton.setOnClickListener(v -> updateProfile());

        return binding.getRoot();
    }



    //Update Profile Function
    private void updateProfile() {
        String userFullName = binding.userFullNameText.getText().toString().trim();
        String phoneNumber = binding.editUserTextPhone.getText().toString().trim();

        //Checking All the Condition
        if (TextUtils.isEmpty(userFullName)) {
            binding.userFullNameText.setError("Full Name is Required");
        }
        else if (TextUtils.isEmpty(phoneNumber)) {
            binding.editUserTextPhone.setError("Phone Number Not Be Empty");
        }
        else {
            DocumentReference documentReference = fStore.collection("Users").document(Objects.requireNonNull(mAuth.getUid()));
            Map<String, Object> users = new HashMap<>();
            users.put("FullName", userFullName);

            users.put("PhoneNumber", phoneNumber);
            users.put("Gender", genderText);
            users.put("UserId", mAuth.getUid());
            users.put("UserProfilePic",downloadUrl);

            documentReference.update(users).addOnSuccessListener(unused -> {
                AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
                assert appCompatActivity != null;
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new ProfileFragment()).addToBackStack(null).commit();
            }).addOnFailureListener(e -> Toast.makeText(getContext(), "We will resolve your error soon", Toast.LENGTH_SHORT).show());

        }
    }
}
