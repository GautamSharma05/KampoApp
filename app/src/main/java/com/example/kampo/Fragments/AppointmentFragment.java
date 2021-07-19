package com.example.kampo.Fragments;



import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.kampo.Adapters.WorkersAdapter;
import com.example.kampo.Models.Workers;
import com.example.kampo.databinding.FragmentAppointmentBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;




public class AppointmentFragment extends Fragment {
    FragmentAppointmentBinding binding; //Binding the FragmentAppointment
    WorkersAdapter workersAdapter;
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String dateSelected;
    boolean selectedSpecialist = false;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentBinding.inflate(inflater,container,false);

        //Date Picker Start Date and Getting Selected Date in dateSelected Variable
        binding.dayDatePicker.setStartDate(19, 7,2021);
        binding.dayDatePicker.getSelectedDate(date -> {
            if(date != null){
                dateSelected = date.toString();
                Toast.makeText(getContext(), dateSelected, Toast.LENGTH_SHORT).show();
            }
        });


        //Getting All Employee From Database and Display in RecyclerView
      binding.specialistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        FirestoreRecyclerOptions<Workers> workers = new FirestoreRecyclerOptions.Builder<Workers>()
                .setQuery(fStore.collection("Workers"),Workers.class).build();
        workersAdapter = new WorkersAdapter(workers);
        binding.specialistRecyclerView.setAdapter(workersAdapter);












        //Attach setOnClickListener and Check All the condition below before send user to another Fragment
        binding.bookAppointment.setOnClickListener(v -> {
            String services = binding.customerWantServices.getText().toString();
            if(dateSelected == null ){

                //Creating Alert if user is not select date
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                builder.setTitle("Select the Date");
                builder.setMessage("Please Select the Date From Above Calendar");
                AlertDialog dialog
                        = builder.create();
                dialog.show();
            }
            else if(!selectedSpecialist){

                //Creating Alert if user is not select Specialist from Given list
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getContext());
                builder.setTitle("Select the Specialist ");
                builder.setMessage("Please Select the Specialist By Clicking On Specialist Profile Picture");
                AlertDialog dialog
                        = builder.create();
                dialog.show();
            }
            else if(binding.morningSlotGroup.getCheckedRadioButtonId() == -1){

                //Checking if User select at least one radio button or not
                Toast.makeText(getContext(), "Please Select Available Slot", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(services)){
                binding.customerWantServices.setError("At least One Service is Required");
            }
            else{
                Toast.makeText(getContext(), "I don't Know", Toast.LENGTH_SHORT).show();
            }
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