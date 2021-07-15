package com.example.kampo.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.kampo.R;
import com.example.kampo.databinding.FragmentSelectedSpecialistBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class SelectedSpecialistFragment extends Fragment {
    String WorkerId,FullName,ProfilePicUri;

    //Constructor for getting Value of workerId,fullName and ProfilePicUri
    public SelectedSpecialistFragment(String workerId, String fullName, String profilePicUri) {
        this.WorkerId = workerId;
        this.FullName = fullName;
        this.ProfilePicUri = profilePicUri;
    }

    FragmentSelectedSpecialistBinding binding;
    String dateSelected;
    boolean slot_1,slot_2,slot_3,slot_4;
   // private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectedSpecialistBinding.inflate(inflater,container,false);
        binding.dayDatePicker.setStartDate(15,7,2021);

        binding.dayDatePicker.getSelectedDate(date -> {
            if(date != null){
                dateSelected = date.toString();
            }
        });


        //Getting Today Date using Date
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String year = String.valueOf(localDate.getYear());
        String month = String.valueOf(localDate.getMonthValue());
        String day = String.valueOf(localDate.getDayOfMonth());
        String todayDate = day + "-" + month + "-" + year;


        //Checking Slot Available or not From Database
        DocumentReference documentReference = fStore.collection("Workers").document(WorkerId).collection("Appointment").document(todayDate);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if(value != null){
                            slot_1 = value.getBoolean("10:00AM");
                            slot_2 = value.getBoolean("11:00AM");
                            slot_3 = value.getBoolean("12:00PM");
                            slot_4 = value.getBoolean("1:00PM");
                        }
                        else{
                            Toast.makeText(getContext(), "Out Dated", Toast.LENGTH_SHORT).show();
                        }
            }
        });


        binding.employeeName.setText(FullName);
        Glide.with(getContext()).load(ProfilePicUri).placeholder(R.drawable.sampleworker).into(binding.employeeSelectedImage);


        //Check All Condition After click on button and Send user to other fragment
        binding.bookFinalAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int morningSelectedId = binding.morningSlotGroup.getCheckedRadioButtonId();


                //Checking Condition date is selected or not
                if(dateSelected == null ){
                    //AlertDialog its show if date is not selected
                    AlertDialog.Builder builder
                            = new AlertDialog.Builder(getContext());
                    builder.setTitle("Select the Date");
                    builder.setMessage("Please Select the Date From Above Calendar");
                    AlertDialog dialog
                            = builder.create();
                    dialog.show();
                }
                //checking that  atLeast one slot is selected or not
                else if(binding.morningSlotGroup.getCheckedRadioButtonId() == -1){

                    Toast.makeText(getContext(), "Please Select Available Slot", Toast.LENGTH_SHORT).show();
                }


                //Checking Slot Availability from Firebase Database
                else{
                    String result= (binding.slot1.isChecked())?"10:00AM":(binding.slot2.isChecked())?"11:00AM":(binding.slot3.isChecked())?"12:00PM":(binding.slot4.isChecked())?"01:00PM":"";
                    if(result.equals("10:00AM")){
                        if(slot_1){
                            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new PaymentFragment(WorkerId,FullName,dateSelected,result)).addToBackStack(null).commit();
                        }
                        else{
                            Toast.makeText(getContext(), "Slot not Available Select Another", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(result.equals("11:00AM")){
                        if(slot_2){
                            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new PaymentFragment(WorkerId,FullName,dateSelected,result)).addToBackStack(null).commit();
                        }
                        else{
                            Toast.makeText(getContext(), "Slot not Available Select Another", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(result.equals("12:00PM")){
                        if(slot_3){
                            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new PaymentFragment(WorkerId,FullName,dateSelected,result)).addToBackStack(null).commit();
                        }
                        else{
                            Toast.makeText(getContext(), "Slot not Available Select Another", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(result.equals("01:00PM")){
                        if(slot_4){
                            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new PaymentFragment(WorkerId,FullName,dateSelected,result)).addToBackStack(null).commit();
                        }
                        else{
                            Toast.makeText(getContext(), "Slot not Available Select Another", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });
        return binding.getRoot();
    }

    //Getting Radio value and checked which radio button is Selected
    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.slot1:
                if(checked)
                break;
            case R.id.slot2:
                if(checked)
                break;
            case R.id.slot3:
                if(checked)
                break;
            case R.id.slot4:
                if(checked)
                break;

        }
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

}