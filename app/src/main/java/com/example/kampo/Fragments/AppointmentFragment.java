package com.example.kampo.Fragments;



import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kampo.databinding.FragmentAppointmentBinding;
import org.jetbrains.annotations.NotNull;




public class AppointmentFragment extends Fragment {
    FragmentAppointmentBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }



}