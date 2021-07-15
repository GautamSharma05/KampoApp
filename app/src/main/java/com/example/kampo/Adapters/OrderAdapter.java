package com.example.kampo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampo.Models.Orders;

import com.example.kampo.R;
import com.example.kampo.databinding.SampleOrdersBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class OrderAdapter extends FirestoreRecyclerAdapter<Orders,OrderAdapter.OrderHolder> {

    public OrderAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Orders> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull OrderAdapter.OrderHolder holder, int position, @NonNull @NotNull Orders model) {
        holder.binding.orderBookingIdUpdateText.setText(model.getBookingId());
        holder.binding.orderCustomerUpdateName.setText(model.getName());
        holder.binding.orderCustomerUpdateMobileNumber.setText(model.getMobileNumber());
        holder.binding.orderSpecialistNameUpdate.setText(model.getWorkerName());
        holder.binding.orderSpecialistMobileNumberUpdate.setText(model.getWorkerMobileNumber());
        holder.binding.orderBookedSlotUpdate.setText(model.getSlot());
        holder.binding.orderBookingUpdatedDate.setText(model.getBookingDate());
    }

    @NonNull
    @NotNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_orders,parent,false);
        return new OrderHolder(view);
    }

    public static class OrderHolder extends RecyclerView.ViewHolder{
        SampleOrdersBinding binding;
        public OrderHolder(View view){
            super(view);
             binding = SampleOrdersBinding.bind(view);
        }
    }
}
