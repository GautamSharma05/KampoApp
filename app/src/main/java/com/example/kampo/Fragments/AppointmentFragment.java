package com.example.kampo.Fragments;



import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kampo.Adapters.WorkersAdapter;
import com.example.kampo.Models.Workers;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentAppointmentBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;


public class AppointmentFragment extends Fragment {
    FragmentAppointmentBinding binding;
    WorkersAdapter workersAdapter;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentBinding.inflate(inflater,container,false);
        binding.dayDatePicker.setStartDate(12,07,2021);
        binding.dayDatePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable @org.jetbrains.annotations.Nullable Date date) {
                if(date != null){
                    Toast.makeText(getContext(), date.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
      binding.appointmentRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false));
      binding.specialistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        FirestoreRecyclerOptions<Workers> workers = new FirestoreRecyclerOptions.Builder<Workers>()
                .setQuery(fStore.collection("Workers"),Workers.class).build();
        workersAdapter = new WorkersAdapter(workers);
        binding.specialistRecyclerView.setAdapter(workersAdapter);

        binding.bookAppointment.setOnClickListener(v -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new AddressFragment()).addToBackStack(null).commit();
        });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        workersAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        workersAdapter.stopListening();
    }
}