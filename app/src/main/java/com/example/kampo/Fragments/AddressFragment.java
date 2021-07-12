package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kampo.Models.Users;

import com.example.kampo.databinding.FragmentAddressBinding;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class AddressFragment extends Fragment {
    FragmentAddressBinding binding;

    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddressBinding.inflate(inflater,container,false);
        binding.addAddressText.setOnClickListener(v -> {

        });
        fStore.collection("Users").document(Objects.requireNonNull(mAuth.getUid())).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(value != null){
                    binding.addressSelect.setText(value.getString("Address"));
                }
            }
        });
        fStore.collection("Booking").document("b9oY6rmjCyR4TjahqNQv").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(value != null){
                    binding.bookingIdUpdateText.setText(value.getString("Booking Id"));
                    binding.customerUpdateName.setText(value.getString("Name"));
                    binding.customerUpdateMobileNumber.setText(value.getString("Mobile Number"));
                    binding.paymentMethodUpdate.setText(value.getString("Payment Method"));
                    binding.specialistNameUpdate.setText(value.getString("Worker Name"));
                    binding.specialistMobileNumberUpdate.setText(value.getString("Worker Mobile Number"));
                    binding.bookedSlotUpdate.setText(value.getString("Slot"));
                }
            }
        });


        return binding.getRoot();
    }


}