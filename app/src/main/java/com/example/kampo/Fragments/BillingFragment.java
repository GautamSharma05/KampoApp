package com.example.kampo.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kampo.databinding.FragmentBillingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;


public class BillingFragment extends Fragment {
    String bookingId;

    //Creating Constructor for Getting Booking Id from Previous Payment Fragment
    public BillingFragment(String bookingId) {
        this.bookingId = bookingId;
    }

    FragmentBillingBinding binding;

    //Creating Instances of Firebase Database
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBillingBinding.inflate(inflater,container,false);

        //Getting User Address From Cloud FireStore Database and Display into The App
        fStore.collection("Users").document(Objects.requireNonNull(mAuth.getUid())).addSnapshotListener((value, error) -> {
            if(value != null){
                binding.addressSelect.setText(value.getString("Address"));
            }
        });

        //Getting All the Booking Details From Cloud FireStore Database and Display into The App
        fStore.collection("Booking").document(bookingId).addSnapshotListener((value, error) -> {
            if(value != null){
                binding.bookingIdUpdateText.setText(value.getString("BookingId"));
                binding.customerUpdateName.setText(value.getString("Name"));
                binding.customerUpdateMobileNumber.setText(value.getString("Mobile Number"));
                binding.paymentMethodUpdate.setText(value.getString("Payment Method"));
                binding.specialistNameUpdate.setText(value.getString("Worker Name"));
                binding.specialistMobileNumberUpdate.setText(value.getString("Worker Mobile Number"));
                binding.bookedSlotUpdate.setText(value.getString("Slot"));
                binding.bookingUpdatedDate.setText(value.getString("Booking Date"));
            }
        });


        return binding.getRoot();
    }


}