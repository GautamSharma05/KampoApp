package com.example.kampo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kampo.Fragments.WorkersProfileFragment;
import com.example.kampo.Models.Workers;
import com.example.kampo.R;
import com.example.kampo.databinding.SampleWorkersBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class WorkersAdapter extends FirestoreRecyclerAdapter<Workers,WorkersAdapter.WorkerHolder>{


    public WorkersAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Workers> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull WorkersAdapter.WorkerHolder holder, int position, @NonNull @NotNull Workers model) {
        holder.binding.workerName.setText(model.getFullName());
        Glide.with(holder.binding.workerImage.getContext()).load(model.getProfilePicUri()).placeholder(R.drawable.avatar).into(holder.binding.workerImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new WorkersProfileFragment(model.getFullName(),model.getProfilePicUri(),model.getProfileThumbnail())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public WorkerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_workers,parent,false);
        return new WorkerHolder(view);
    }

    public static class WorkerHolder extends RecyclerView.ViewHolder {
        SampleWorkersBinding binding;
        public WorkerHolder(View view) {
            super(view);
            binding = SampleWorkersBinding.bind(view);
        }
    }
}
