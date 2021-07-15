package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kampo.Adapters.OrderAdapter;
import com.example.kampo.Models.Orders;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentAllOrderBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;


public class AllOrderFragment extends Fragment {
    FragmentAllOrderBinding binding;
    OrderAdapter orderAdapter;
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllOrderBinding.inflate(inflater,container,false);
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid().trim();

        Query query = fStore.collection("Booking").whereEqualTo("UserId",currentUser);
        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        FirestoreRecyclerOptions<Orders> orders = new FirestoreRecyclerOptions.Builder<Orders>().
                setQuery(query,Orders.class).build();
        orderAdapter = new OrderAdapter(orders);
        binding.ordersRecyclerView.setAdapter(orderAdapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }
}