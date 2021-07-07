package com.example.kampo.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.kampo.Adapters.WorkersAdapter;
import com.example.kampo.Models.Workers;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentHomeBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    WorkersAdapter workersAdapter;
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider1,"", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slider3,"", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slider4,"", ScaleTypes.CENTER_CROP));
        binding.imageSlider.setImageList(slideModels);
        binding.workersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        FirestoreRecyclerOptions<Workers> workers = new FirestoreRecyclerOptions.Builder<Workers>()
                .setQuery(fStore.collection("Workers").limit(2),Workers.class).build();
            workersAdapter = new WorkersAdapter(workers);
            binding.workersRecyclerView.setAdapter(workersAdapter);
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