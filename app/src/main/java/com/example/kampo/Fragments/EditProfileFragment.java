package com.example.kampo.Fragments;

import android.app.ProgressDialog;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    ArrayAdapter<String> arrayAdapter;
    Uri selectedImage;
    String downloadUrl;
    ProgressDialog progressBar;

    String [] gender = {"Male","Female","Others"};
    String genderText;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage =FirebaseStorage.getInstance();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);
        progressBar = new ProgressDialog(getContext());
        progressBar.setMessage("Wait till Image Updated...");
        progressBar.setCancelable(false);
        fStore.collection("Users").document(mAuth.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
               if(value != null){
                   binding.editProfileUserEmail.setText(value.getString("Email"));
               }
            }
        });

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>(){
                    @Override
                    public void onActivityResult(Uri result) {
                        binding.changeProfileImage.setImageURI(result);
                        selectedImage = result;
                        progressBar.show();
                        StorageReference reference = storage.getReference().child("Profiles").child(mAuth.getUid());
                        reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            progressBar.dismiss();
                                            downloadUrl = uri.toString();

                                        }
                                    }) ;
                                }
                            }
                        });
                    }
                });
        binding.changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,gender);
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

        binding.updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();


            }
        });

        return binding.getRoot();
    }

    private void updateProfile() {
        String userFullName = binding.userFullNameText.getText().toString().trim();
        String phoneNumber = binding.editUserTextPhone.getText().toString().trim();
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

            documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new ProfileFragment()).addToBackStack(null).commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(getContext(), "We will resolve your error soon", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
