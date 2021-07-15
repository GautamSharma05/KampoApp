package com.example.kampo.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.kampo.Activity.MapActivity;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentPaymentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class PaymentFragment extends Fragment {
    String WorkerId,FullName,dateSelected,result;
    //Constructor For getting Values from previous Fragment
    public PaymentFragment(String workerId, String fullName, String dateSelected,String result) {
        this.WorkerId = workerId;
        this.FullName = fullName;
        this.dateSelected = dateSelected;
        this.result = result;
    }

    FragmentPaymentBinding binding;

    //Defining Variable
    String customerName,customerMobileNumber,customerAddress,workerMobileNumber,bookingId;

    //Creating Instances
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater,container,false);
        binding.addAddressText.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MapActivity.class);
            startActivity(intent);
        });

        //Getting User Details From Firebase Database
        fStore.collection("Users").document(Objects.requireNonNull(mAuth.getUid())).addSnapshotListener((value, error) -> {
                if(value != null){
                    customerName = value.getString("FullName");
                    customerMobileNumber = value.getString("PhoneNumber");
                    customerAddress = value.getString("Address");
                    binding.customerUpdateNamePrev.setText(customerName);
                    binding.customerUpdateMobileNumberPrev.setText(customerMobileNumber);
                    binding.addressSelect.setText(customerAddress);
                }
        });

        //Getting Worker Details From Worker Database
        fStore.collection("Workers").document(WorkerId).addSnapshotListener((value, error) -> {
                if(value != null){
                    workerMobileNumber = value.getString("WorkerNumber");
                    binding.specialistMobileNumberUpdatePrev.setText(workerMobileNumber);
                }
        });

        //Set text
       binding.specialistNameUpdatePrev.setText(FullName);
       binding.bookingUpdatedDatePrev.setText(dateSelected);
       binding.bookedSlotUpdatePrev.setText(result);


       //Booking Details entry in database
       binding.confirmAddress.setOnClickListener(v -> {
           DocumentReference documentReference = fStore.collection("Booking").document();
           bookingId = documentReference.getId();
           Map<String,Object> booking = new HashMap<>();
           booking.put("BookingId",bookingId);
           booking.put("Name",customerName);
           booking.put("Address",customerAddress);
           booking.put("MobileNumber",customerMobileNumber);
           booking.put("WorkerName",FullName);
           booking.put("WorkerMobileNumber",workerMobileNumber);
           booking.put("PaymentMethod","COD");
           booking.put("Slot",result);
           booking.put("UserId",mAuth.getUid());
           booking.put("BookingDate",dateSelected);
           documentReference.set(booking).addOnCompleteListener(task -> {
              if(task.isSuccessful()){
                  fStore.collection("Workers").document(WorkerId)
                          .collection("Appointment").document("15-7-2021").update(result,false);

                  AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                  appCompatActivity
                          .getSupportFragmentManager()
                          .beginTransaction()
                          .replace(R.id.frameContainer,new BillingFragment(bookingId))
                          .addToBackStack(null)
                          .commit();

              }
              else{
                  Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
              }
           });


       });


        return binding.getRoot();
    }
}