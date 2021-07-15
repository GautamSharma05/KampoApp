package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    WorkersAdapter workersAdapter;

    //Creating Firebase instances
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        //Attach image from Cloud storage in Slider
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/kampo-82b0e.appspot.com/o/SliderImages%2Fslider1.jpg?alt=media&token=d2ba2466-f86d-43fa-9090-e117113aba33","", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/kampo-82b0e.appspot.com/o/SliderImages%2Fslider3.jpg?alt=media&token=90b946dc-8717-4a2a-be92-50651a9780cf","", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/kampo-82b0e.appspot.com/o/SliderImages%2Fslider4.jpg?alt=media&token=a2a039b7-67de-457c-923b-f70689942f7e","", ScaleTypes.CENTER_CROP));
        binding.imageSlider.setImageList(slideModels);




        //Services Click And Send User To appointment Screen
        binding.hairCutImage.setOnClickListener(v -> getAppointmentScreen());
        binding.hairCutText.setOnClickListener(v -> getAppointmentScreen());
        binding.hairDryerImage.setOnClickListener(v -> getAppointmentScreen());
        binding.hairDryer.setOnClickListener(v -> getAppointmentScreen());
        binding.shampooImage.setOnClickListener(v -> getAppointmentScreen());
        binding.shampoo.setOnClickListener(v -> getAppointmentScreen());
        binding.stylishImage.setOnClickListener(v -> getAppointmentScreen());
        binding.Stylish.setOnClickListener(v -> getAppointmentScreen());
        binding.hairSpaImage.setOnClickListener(v -> getAppointmentScreen());
        binding.hairSpa.setOnClickListener(v -> getAppointmentScreen());
        binding.shampooImage.setOnClickListener(v -> getAppointmentScreen());
        binding.shaving.setOnClickListener(v -> getAppointmentScreen());
        binding.coloringImage.setOnClickListener(v -> getAppointmentScreen());
        binding.coloring.setOnClickListener(v -> getAppointmentScreen());
        binding.moreImage.setOnClickListener(v ->{
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer,new CategoryFragment())
                    .addToBackStack(null)
                    .commit();
        });
        binding.more.setOnClickListener(v ->{
            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            appCompatActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer,new CategoryFragment())
                    .addToBackStack(null)
                    .commit();
        });




        //Get All the Employees From Database
        binding.workersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        FirestoreRecyclerOptions<Workers> workers = new FirestoreRecyclerOptions.Builder<Workers>()
                .setQuery(fStore.collection("Workers").limit(5),Workers.class).build();
            workersAdapter = new WorkersAdapter(workers);
            binding.workersRecyclerView.setAdapter(workersAdapter);

            //When User Click on Sell All Button Then All the Employees will be display on screen
            binding.sellAllText.setOnClickListener(v ->{
                AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new AllWorkersFragment()).addToBackStack(null).commit();
            });



            return binding.getRoot();
    }

    private void getAppointmentScreen() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getContext();
        assert appCompatActivity != null;
        appCompatActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer,new AppointmentFragment())
                .addToBackStack(null)
                .commit();
    }

    //FirebaseRecyclerView Start listening from Database
    @Override
    public void onStart() {
        super.onStart();
        workersAdapter.startListening();
    }

    //FirebaseRecyclerView Stop listening from Database
    @Override
    public void onStop() {
        super.onStop();
        workersAdapter.stopListening();
    }
}