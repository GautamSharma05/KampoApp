package com.example.kampo.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kampo.Adapters.WorkersAdapter;
import com.example.kampo.Models.Workers;
import com.example.kampo.databinding.FragmentAllWorkersBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;


public class AllWorkersFragment extends Fragment {
    FragmentAllWorkersBinding binding;
    WorkersAdapter workersAdapter;

    //Creating Firebase Firestore instance
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllWorkersBinding.inflate(inflater,container,false);




        //Getting all the Employees From Database in RecyclerView using FireStoreRecyclerOptions
        binding.allSpecialistRecylcerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        FirestoreRecyclerOptions<Workers> workers = new FirestoreRecyclerOptions.Builder<Workers>()
                .setQuery(fStore.collection("Workers"),Workers.class).build();
        workersAdapter = new WorkersAdapter(workers);
        binding.allSpecialistRecylcerView.setAdapter(workersAdapter);



        return binding.getRoot();
    }

    //RecyclerView Start Listening from Cloud Firestore
    @Override
    public void onStart() {
        super.onStart();
        workersAdapter.startListening();
    }
   //RecyclerView Stop Listening From Cloud Firestore
    @Override
    public void onStop() {
        super.onStop();
        workersAdapter.stopListening();
    }
}
